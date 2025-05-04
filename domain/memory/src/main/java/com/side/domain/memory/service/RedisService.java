package com.side.domain.memory.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.side.domain.memory.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisRepository redisRepository;

    public Long increment(String key) {
        return redisRepository.increment(key);
    }

    public void expire(String key, long timeOut, TimeUnit timeUnit) {
        redisRepository.expire(key, timeOut, timeUnit);
    }

    public Long decrement(String key) {
        return redisRepository.decrement(key);
    }

    public boolean insertIfAbsent(String key, Object value) {
        return redisRepository.insertIfAbsent(key, value);
    }

    public void create(String key, Object value) {
        try {
            log.debug("RedisService.create called - key: {}", key);
            redisRepository.create(key, value);
            log.debug("RedisService.create completed for key: {}", key);
        } catch (Exception e) {
            log.error("Error in RedisService.create: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void create(String key, Object value, long timeout, TimeUnit unit) {
        try {
            log.debug("RedisService.create with timeout called - key: {}", key);
            redisRepository.create(key, value, timeout, unit);
            log.debug("RedisService.create with timeout completed for key: {}", key);
        } catch (Exception e) {
            log.error("Error in RedisService.create with timeout: {}", e.getMessage(), e);
            throw e;
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            log.debug("RedisService.get called - key: {}, class: {}", key, clazz.getSimpleName());
            T result = redisRepository.get(key, clazz);
            log.debug("RedisService.get completed for key: {}", key);
            return result;
        } catch (Exception e) {
            log.error("Error in RedisService.get: {}", e.getMessage(), e);
            throw e;
        }
    }

    public <T> T get(String key, TypeReference<T> typeReference) {
        try {
            log.debug("RedisService.get with TypeReference called - key: {}", key);
            T result = redisRepository.get(key, typeReference);
            log.debug("RedisService.get with TypeReference completed for key: {}", key);
            return result;
        } catch (Exception e) {
            log.error("Error in RedisService.get with TypeReference: {}", e.getMessage(), e);
            throw e;
        }
    }

    public <T> Optional<T> find(String key, Class<T> clazz) {
        try {
            log.debug("RedisService.find called - key: {}, class: {}", key, clazz.getSimpleName());
            Optional<T> result = redisRepository.find(key, clazz);
            log.debug("RedisService.find completed for key: {}", key);
            return result;
        } catch (Exception e) {
            log.error("Error in RedisService.find: {}", e.getMessage(), e);
            throw e;
        }
    }

    public <T> Optional<T> find(String key, TypeReference<T> typeReference) {
        try {
            log.debug("RedisService.find with TypeReference called - key: {}", key);
            Optional<T> result = redisRepository.find(key, typeReference);
            log.debug("RedisService.find with TypeReference completed for key: {}", key);
            return result;
        } catch (Exception e) {
            log.error("Error in RedisService.find with TypeReference: {}", e.getMessage(), e);
            throw e;
        }
    }

    public boolean delete(String key) {
        try {
            log.debug("RedisService.delete called - key: {}", key);
            boolean result = redisRepository.delete(key);
            log.debug("RedisService.delete completed for key: {}, result: {}", key, result);
            return result;
        } catch (Exception e) {
            log.error("Error in RedisService.delete: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * Redis 연결을 테스트하는 메서드
     * @return 연결 성공 여부
     */
    public boolean testConnection() {
        try {
            log.info("Testing Redis connection...");
            // 간단한 값을 저장하고 바로 삭제하여 연결 테스트
            String testKey = "connection_test_" + System.currentTimeMillis();
            this.create(testKey, "OK");
            boolean deleted = this.delete(testKey);
            log.info("Redis connection test result: {}", deleted ? "Success" : "Failed");
            return deleted;
        } catch (Exception e) {
            log.error("Redis connection test failed: {}", e.getMessage(), e);
            return false;
        }
    }
}