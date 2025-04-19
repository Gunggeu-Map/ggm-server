package com.gunggeumap.ggm.auth.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    // save
    public void save(String key, String value, long expiredMs) {
        redisTemplate.opsForValue().set(key, value, expiredMs, TimeUnit.MILLISECONDS);
    }
    // get
    public Optional<String> findByKey(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    // exists
    public boolean exists(String key) {
        return findByKey(key).isPresent();
    }

    // delete
    public void deleteByKey(String key) {
        redisTemplate.delete(key);
    }
}
