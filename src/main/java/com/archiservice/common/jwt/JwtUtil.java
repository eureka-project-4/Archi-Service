package com.archiservice.common.jwt;

import com.archiservice.common.security.CustomUser;
import io.jsonwebtoken.*;
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

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String token) {
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
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("JWT token is malformed: {}", e.getMessage());
            throw e;
        } catch (SecurityException e) {
            log.error("JWT signature does not match: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid: {}", e.getMessage());
            throw e;
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(CustomUser customUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", customUser.getId());
        claims.put("tagCode", customUser.getTagCode());
        claims.put("ageCode", customUser.getAgeCode());
        return createToken(claims, customUser.getUsername(), accessTokenExpiration);
    }

    public String generateRefreshToken(CustomUser customUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", customUser.getId());
        claims.put("tagCode", customUser.getTagCode());
        claims.put("ageCode", customUser.getAgeCode());
        return createToken(claims, customUser.getUsername(), refreshTokenExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractEmail(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    public Boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
    
    public String generateCustomToken(Map<String, Object> claims, String subject) {
    	return createToken(claims, subject, accessTokenExpiration);
    }


    public Long extractUserId(String token) {
        final Claims claims = extractAllClaims(token);
        Object userId = claims.get("userId");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        } else if (userId instanceof Long) {
            return (Long) userId;
        } else if (userId instanceof String) {
            return Long.parseLong((String) userId);
        } else {
            throw new IllegalArgumentException("Invalid userId type in token: " + userId);
        }
    }

    public Long extractTagCode(String token) {
        final Claims claims = extractAllClaims(token);
        Object tagCode = claims.get("tagCode");
        if (tagCode instanceof Integer) {
            return ((Integer) tagCode).longValue();
        } else if (tagCode instanceof Long) {
            return (Long) tagCode;
        } else if (tagCode instanceof String) {
            return Long.parseLong((String) tagCode);
        } else {
            throw new IllegalArgumentException("Invalid tagCode type in token: " + tagCode);
        }
    }

    public String extractAgeCode(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("ageCode", String.class);
    }




}