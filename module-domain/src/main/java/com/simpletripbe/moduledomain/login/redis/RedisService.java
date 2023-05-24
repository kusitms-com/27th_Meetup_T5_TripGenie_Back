package com.simpletripbe.moduledomain.login.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * key: email,
 * value: RTK
 */
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    /**
     * redis에 값 저장하는 메서드
     */
    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration); // 30일 뒤 메모리에서 삭제
    }

    /**
     * redis에 저장된 값 불러오는 메서드
     */
    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    /**
     * redis에 저장된 값 삭제하는 메서드
     */
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
