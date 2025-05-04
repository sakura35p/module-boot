package com.side.domain.memory.repository;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface RedisRepository {

    Long increment(String key);

    void expire(String key, long timeOut, TimeUnit timeUnit);

    Long decrement(String key);

    boolean insertIfAbsent(String key, Object value);

    void create(String key, Object value);

    void create(String key, Object value, long timeout, TimeUnit unit);

    <T> T get(String key, Class<T> clazz);

    <T> T get(String key, TypeReference<T> typeReference);

    <T> Optional<T> find(String key, Class<T> clazz);

    <T> Optional<T> find(String key, TypeReference<T> typeReference);

    boolean delete(String key);
}
