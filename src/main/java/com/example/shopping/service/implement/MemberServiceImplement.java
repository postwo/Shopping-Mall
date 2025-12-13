package com.example.shopping.service.implement;

import com.example.shopping.common.error.MemberErrorCode;
import com.example.shopping.common.error.TokenErrorCode;
import com.example.shopping.common.exception.ApiException;
import com.example.shopping.dto.jwt.TokenRefreshResponse;
import com.example.shopping.dto.member.LoginRequest;
import com.example.shopping.dto.member.LoginResponse;
import com.example.shopping.dto.member.RegisterRequest;
import com.example.shopping.dto.member.RegisterResponse;
import com.example.shopping.entity.member.Member;
import com.example.shopping.entity.token.RefreshToken;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.RefreshTokenRepository;
import com.example.shopping.service.MemberService;
import com.example.shopping.service.TokenBlacklistService;
import com.example.shopping.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImplement implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    @Transactional
    public RegisterResponse registerMember(RegisterRequest request) {
        String id = request.getMemberId();

        memberRepository.findByMemberId(id).ifPresent(member -> {
            throw new ApiException(MemberErrorCode.MEMBER_ALREADY_EXISTS);
        });

        String pw = request.getMemberPw();
        request.setMemberPw(passwordEncoder.encode(pw));
        Member createMember = new Member(request);

        Member savedMember = memberRepository.save(createMember);
        return RegisterResponse.createMember(savedMember);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMemberId(), request.getMemberPw())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByMemberId(userDetails.getUsername())
                .orElseThrow(() -> new ApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        // "조회 후 없으면 삽입, 있으면 업데이트" (Upsert) 로직
        Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByMember(member);
        if (existingTokenOpt.isPresent()) {
            // 기존 토큰이 있으면, 새 토큰과 만료 시간으로 업데이트
            RefreshToken existingToken = existingTokenOpt.get();
            existingToken.setToken(newRefreshToken);
            existingToken.setExpiresAt(LocalDateTime.now().plusSeconds(jwtUtil.getRefreshTokenExpiration() / 1000));
            refreshTokenRepository.save(existingToken);
        } else {
            // 기존 토큰이 없으면, 새로 생성하여 저장
            RefreshToken refreshTokenEntity = RefreshToken.builder()
                    .token(newRefreshToken)
                    .member(member)
                    .expiresAt(LocalDateTime.now().plusSeconds(jwtUtil.getRefreshTokenExpiration() / 1000))
                    .build();
            refreshTokenRepository.save(refreshTokenEntity);
        }

        Cookie refreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (jwtUtil.getRefreshTokenExpiration() / 1000));
        response.addCookie(refreshTokenCookie);

        log.info("사용자 로그인 성공: {}", request.getMemberId());

        String username = member.getMemberId();
        String email = member.getMemberEmail();

        return LoginResponse.loginMember(accessToken,username,email);
    }

    /**
     * 리프레시 토큰을 이용해 새로운 액세스 토큰 발급
     * @param refreshToken 리프레시 토큰
     * @return 새로운 액세스 토큰이 포함된 응답
     */
    @Override
    public TokenRefreshResponse refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new ApiException(TokenErrorCode.REFRESH_TOKEN_NOT_FOUND, "리프레시 토큰이 없습니다.");
        }

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new ApiException(TokenErrorCode.INVALID_TOKEN, "유효하지 않은 리프레시 토큰입니다."));

        if (refreshTokenEntity.isExpired()) {
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, "리프레시 토큰이 만료되었습니다. 다시 로그인해주세요.");
        }

        Member member = refreshTokenEntity.getMember();
        String newAccessToken = jwtUtil.generateAccessToken(new org.springframework.security.core.userdetails.User(member.getMemberId(), member.getMemberPw(), new java.util.ArrayList<>()));

        log.info("액세스 토큰 재발급 성공: {}", member.getMemberId());

        return TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .username(member.getMemberId())
                .build();
    }

    /**
     * 사용자 로그아웃 처리
     * @param accessToken 액세스 토큰
     * @param refreshToken 리프레시 토큰
     * @param response HttpServletResponse (쿠키 삭제용)
     * @return 로그아웃 결과 메시지
     */
    @Override
    public Map<String, String> logout(String accessToken, String refreshToken, HttpServletResponse response) {
        // Access Token 블랙리스트 처리
        if (accessToken != null && !accessToken.isEmpty()) {
            long remainingTime = jwtUtil.getRemainingExpirationTime(accessToken);
            if (remainingTime > 0) {
                tokenBlacklistService.addToBlacklist(accessToken, remainingTime);
                log.info("Access Token이 블랙리스트에 추가되었습니다. 남은 시간: {}ms", remainingTime);
            }
        }

        // 리프레시 토큰 삭제 (DB에서 제거)
        if (refreshToken != null && !refreshToken.isEmpty()) {
            refreshTokenRepository.deleteByToken(refreshToken);
        }

        // 클라이언트 쿠키에서 리프레시 토큰 제거
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);

        log.info("로그아웃 완료");

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "로그아웃되었습니다.");
        return responseMap;
    }

    /**
     * 현재 인증된 사용자 정보 조회
     * @param request HttpServletRequest
     * @return 사용자 정보 맵
     */
    @Override
    public Map<String, String> getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ApiException(TokenErrorCode.AUTHORIZATION_HEADER_NOT_FOUND, "인증 헤더가 없거나 형식이 올바르지 않습니다.");
        }
        
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        
        log.info("현재 사용자 조회: {}", username);
        
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("message", "인증된 사용자입니다.");
        return userInfo;
    }

}
