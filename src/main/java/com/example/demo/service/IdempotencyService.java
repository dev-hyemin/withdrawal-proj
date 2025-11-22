package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private static final String PREFIX = "idem:";
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 멱등성 등록 시도
     *
     * @return true면 최초 요청, false면 중복 요청
     */
    public boolean tryRegister(String transactionId, long ttl) {
        String fullKey = PREFIX + transactionId;

        Boolean success = redisTemplate.opsForValue().setIfAbsent(
                fullKey,
                "",
                Duration.ofSeconds(ttl)
        );

        return Boolean.TRUE.equals(success);
    }

    /**
     * 응답 저장
     */
    public void saveResponse(String transactionId, Object response, long ttl) {
        String fullKey = PREFIX + transactionId;

        redisTemplate.opsForValue().set(
                fullKey,
                response.toString(),
                Duration.ofSeconds(ttl)
        );
    }

    /**
     * 중복 요청일 때 저장된 응답 꺼내기
     */
    public String getSavedResponse(String transactionId) {
        return (String) redisTemplate.opsForValue().get(PREFIX + transactionId);
    }
}

