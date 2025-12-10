package com.example.shopping.util;

import com.example.shopping.common.error.TokenErrorCode;
import com.example.shopping.common.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration-time}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-expiration-time}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            log.error("JWT 파싱 오류: {}", e.getMessage());
            throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // 추가 Claim 없음
        return createToken(claims, userDetails.getUsername(), refreshTokenExpiration); // 토큰 생성
    }

    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .claims(claims) // 추가 데이터 설정
                .subject(subject) // 사용자명 설정
                .issuedAt(new Date(System.currentTimeMillis())) // 발행 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 만료 시간 설정
                .signWith(getSigningKey()) // 서명키로 서명
                .compact(); // JWT 문자열로 직렬화
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token); // 토큰에서 사용자명 추출
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // 사용자명 일치 + 만료 안됨
        } catch (JwtException e) {
            log.error("JWT 검증 실패: {}", e.getMessage());
            return false; // 유효하지 않으면 false 반환
        }
    }

    public Boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token); // 만료 여부만 확인
        } catch (JwtException e) {
            log.error("JWT 유효성 검사 실패: {}", e.getMessage());
            return false;
        }
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public long getRemainingExpirationTime(String token) {
        try {
            Date expiration = extractExpiration(token); // 토큰 만료일 추출
            long now = System.currentTimeMillis(); // 현재 시간
            long expirationTime = expiration.getTime(); // 만료 시간
            return Math.max(0, expirationTime - now); // 남은 시간 계산 (음수 방지)
        } catch (Exception e) {
            log.error("토큰 유효시간 계산 오류: {}", e.getMessage());
            return 0; // 오류 시 0 반환
        }
    }
}
