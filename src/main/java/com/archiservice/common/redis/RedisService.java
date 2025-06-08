package com.archiservice.common.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    // Refresh Token 저장
    public void saveRefreshToken(String email, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + email;
        Duration expiration = Duration.ofMillis(refreshTokenExpiration);

        stringRedisTemplate.opsForValue().set(key, refreshToken, expiration);
        log.info("Refresh token saved for user: {}", email);
    }

    // Refresh Token 조회
    public String getRefreshToken(String email) {
        String key = REFRESH_TOKEN_PREFIX + email;
        String token = stringRedisTemplate.opsForValue().get(key);

        if (token != null) {
            log.info("Refresh token found for user: {}", email);
        } else {
            log.info("No refresh token found for user: {}", email);
        }

        return token;
    }

    // Refresh Token 삭제 (로그아웃시)
    public void deleteRefreshToken(String email) {
        String key = REFRESH_TOKEN_PREFIX + email;
        Boolean deleted = stringRedisTemplate.delete(key);

        if (Boolean.TRUE.equals(deleted)) {
            log.info("Refresh token deleted for user: {}", email);
        } else {
            log.warn("No refresh token to delete for user: {}", email);
        }
    }

    // Refresh Token 존재 여부 확인
    public boolean hasRefreshToken(String email) {
        String key = REFRESH_TOKEN_PREFIX + email;
        Boolean exists = stringRedisTemplate.hasKey(key);
        return Boolean.TRUE.equals(exists);
    }

    // Refresh Token 유효성 검증 (Redis에 저장된 토큰과 비교)
    public boolean validateRefreshToken(String email, String refreshToken) {
        String storedToken = getRefreshToken(email);
        boolean isValid = storedToken != null && storedToken.equals(refreshToken);

        if (isValid) {
            log.info("Refresh token validation successful for user: {}", email);
        } else {
            log.warn("Refresh token validation failed for user: {}", email);
        }

        return isValid;
    }

    // 모든 Refresh Token 삭제 (관리자용)
    public void deleteAllRefreshTokens() {
        String pattern = REFRESH_TOKEN_PREFIX + "*";
        var keys = stringRedisTemplate.keys(pattern);

        if (keys != null && !keys.isEmpty()) {
            stringRedisTemplate.delete(keys);
            log.info("All refresh tokens deleted. Count: {}", keys.size());
        }
    }
}
