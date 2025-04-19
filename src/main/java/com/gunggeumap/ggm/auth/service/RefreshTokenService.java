package com.gunggeumap.ggm.auth.service;

import com.gunggeumap.ggm.auth.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisUtil redisUtil;

    private final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    public void saveRefreshToken(Long userId, String refreshToken, long expiredMs) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        redisUtil.save(key, refreshToken, expiredMs);
    }

    public boolean validateRefreshToken(Long userId, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        if (!redisUtil.exists(key)) {
            return false;
        }
        String storedRefreshToken = redisUtil.findByKey(key).get();
        return storedRefreshToken.equals(refreshToken);
    }
}
