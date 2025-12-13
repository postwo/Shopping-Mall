package com.example.shopping.filter;

import com.example.shopping.service.TokenBlacklistService;
import com.example.shopping.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;

    /**
     * HTTP 요청이 들어올 때마다 한 번씩 실행되는 필터 메서드
     * JWT 토큰을 검증하고, 유효 시 SecurityContext에 인증 정보 저장
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,  // 현재 HTTP 요청 객체
            @NonNull HttpServletResponse response, // 현재 HTTP 응답 객체
            @NonNull FilterChain filterChain // 다음 필터로 요청을 전달하기 위한 체인
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); // Authorization 헤더 추출

        // Authorization 헤더가 없거나 Bearer 스킴이 아니면 다음 필터로 바로 이동
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7); // "Bearer " 이후의 토큰 문자열만 추출

            // 블랙리스트에 등록된 토큰인지 확인
            if (tokenBlacklistService.isBlacklisted(jwt)) {
                log.warn("블랙리스트에 등록된 토큰입니다."); // 보안 로그 기록
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 응답 설정
                response.setContentType("application/json; charset=UTF-8"); // JSON 응답 설정
                response.getWriter().write("{\"error\":\"무효화된 토큰입니다. 다시 로그인해주세요.\"}"); // 에러 메시지 전송
                return; // 요청 중단
            }

            final String username = jwtUtil.extractUsername(jwt); // JWT에서 사용자명 추출

            // SecurityContext에 인증이 없는 경우만 처리 (중복 인증 방지)
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // DB에서 사용자 정보 로드

                // 토큰 검증 후 유효하면 인증 객체 생성
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, // 사용자 정보
                            null, // 자격 증명 (비밀번호는 null)
                            userDetails.getAuthorities() // 권한 정보
                    );
                    // 요청 관련 세부 정보를 인증 객체에 추가
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 인증 컨텍스트에 사용자 정보 저장 (Spring Security가 인증된 사용자로 인식)
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    log.warn("유효하지 않은 JWT 토큰: {}", username);
                }
            }
        } catch (JwtException e) {
            // JWT 관련 예외 처리 (만료, 변조 등)
            log.error("JWT 처리 중 오류 발생: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 반환
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\":\"토큰이 만료되었습니다. 다시 로그인해주세요.\"}"); // 에러 응답
            return; // 요청 종료
        } catch (Exception e) {
            // 기타 예외 처리 (예기치 못한 오류)
            log.error("인증 필터에서 예상치 못한 오류: {}", e.getMessage());
        }

        // 인증 처리 후 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}

