package com.example.shopping.service;

import com.example.shopping.common.error.TokenErrorCode;
import com.example.shopping.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate; // Redis 접근 객체

    private static final String BLACKLIST_PREFIX = "blacklist:token:"; // Redis 키 접두사 정의

    /**
     * JWT 토큰을 블랙리스트에 추가
     * @param token JWT 토큰 문자열
     * @param expirationTimeMillis 토큰의 남은 유효 시간 (밀리초)
     */
    public void addToBlacklist(String token, long expirationTimeMillis) {
        try {
            String key = BLACKLIST_PREFIX + token;
            // 토큰의 남은 유효시간 동안만 Redis에 저장 (자동 만료 설정)
            redisTemplate.opsForValue().set(key, "blacklisted", expirationTimeMillis, TimeUnit.MILLISECONDS);
            log.info("토큰이 블랙리스트에 추가되었습니다. 유효시간: {}ms", expirationTimeMillis);
        } catch (Exception e) {
            log.error("블랙리스트 추가 중 오류 발생: {}", e.getMessage());
            throw new ApiException(TokenErrorCode.TOKEN_ADD_FAIL, e);
        }
    }

    /**
     * 특정 토큰이 블랙리스트에 포함되어 있는지 확인
     * @param token JWT 토큰 문자열
     * @return 블랙리스트에 존재하면 true, 존재하지 않으면 false
     */
    public boolean isBlacklisted(String token) {
        try {
            String key = BLACKLIST_PREFIX + token;
            Boolean hasKey = redisTemplate.hasKey(key);
            return Boolean.TRUE.equals(hasKey);
        } catch (Exception e) {
            log.error("블랙리스트 확인 중 오류 발생: {}", e.getMessage());
            // Redis 장애 시 false 반환하여 시스템 중단 방지 (안전한 fallback 처리)
            return false;
        }
    }

    /**
     * 특정 토큰을 블랙리스트에서 제거 (관리자 전용 기능 등에 사용)
     * @param token JWT 토큰 문자열
     */
    public void removeFromBlacklist(String token) {
        try {
            String key = BLACKLIST_PREFIX + token;
            redisTemplate.delete(key);
            log.info("토큰이 블랙리스트에서 제거되었습니다.");
        } catch (Exception e) {
            log.error("블랙리스트 제거 중 오류 발생: {}", e.getMessage());
            throw new ApiException(TokenErrorCode.TOKEN_REMOVE_FAIL, e);
        }
    }
}
