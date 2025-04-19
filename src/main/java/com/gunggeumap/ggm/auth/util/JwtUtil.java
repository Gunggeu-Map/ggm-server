package com.gunggeumap.ggm.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    /**
     * encoded HS256
     *
     * @param accessSecretKey  32Byte random code
     * @param refreshSecretKey 32Byte random code
     */
    public JwtUtil(final @Value("${spring.jwt.access.secret-key}") String accessSecretKey,
                   final @Value("${spring.jwt.refresh.secret-key}") String refreshSecretKey) {
        this.accessSecretKey = new SecretKeySpec(
                accessSecretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        this.refreshSecretKey = new SecretKeySpec(
                refreshSecretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /**
     * AccessToken 생성
     *
     * @param userId 서버 내 user id
     * @return AccessToken
     */
    public String generateAccessToken(String userId, Long validityMs) {
        return buildToken(userId, validityMs, accessSecretKey);
    }

    /**
     * RefreshToken 생성
     *
     * @param userId 서버 내 userId
     * @return RefreshToken
     */
    public String generateRefreshToken(String userId, Long validityMs) {
        return buildToken(userId, validityMs, refreshSecretKey);
    }

    /**
     * {access, refresh} Token 생성
     *
     * @param userId  서버 내 userId
     * @param secretKey  {access, refresh} secretKey
     * @param validityMs {access, refresh} validityTime
     * @return {access, refresh} Token
     */
    private String buildToken(String userId, Long validityMs, SecretKey secretKey) {
        Date now = new Date(System.currentTimeMillis());
        Date expiredMs = new Date(System.currentTimeMillis() + validityMs);
        return Jwts.builder()
                .subject(userId)
                .issuedAt(now)
                .expiration(expiredMs)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 토큰 내 userId 추출
     *
     * @param accessToken 사용자의 access token
     * @return tokenCode
     */
    public Long getUserIDFromAccessToken(String accessToken) {
        return Long.parseLong(extractAccessToken(accessToken).getSubject());
    }

    /**
     * 토큰 내 userId 추출
     *
     * @param refreshToken 사용자의 refresh token
     * @return tokenCode
     */
    public Long getUserIDFromRefreshToken(String refreshToken) {
        return Long.parseLong(extractRefreshToken(refreshToken).getSubject());
    }

    /**
     * Token parsing
     *
     * @param token     {access, refresh} token
     * @param secretKey {access, refresh} secretKey
     * @return jwt token 정보
     */
    private Claims parseToken(String token, SecretKey secretKey) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.debug("토큰 만료");
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("토큰 형식 문제 발생");
            throw e;
        }
    }

    /**
     * access token parsing
     *
     * @param accessToken 사용자의 access token
     * @return jwt token 정보
     */
    private Claims extractAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecretKey);
    }

    /**
     * refresh token parsing
     *
     * @param refreshToken 사용자의 refresh token
     * @return jwt token 정보
     */
    private Claims extractRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecretKey);
    }
}
