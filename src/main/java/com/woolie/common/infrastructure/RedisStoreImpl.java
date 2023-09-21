package com.woolie.common.infrastructure;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.woolie.common.redis.RedisStore;
import com.woolie.user.exception.UnableToProcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.woolie.common.exception.enums.ErrorCode.UNABLE_TO_PROCESS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisStoreImpl<T> implements RedisStore<T> {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void saveData(String key, T value, Long expire) {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue()
                    .set(key, json, expire, MILLISECONDS);
        } catch (Exception e) {
            log.error("",e);
            throw new UnableToProcessException(UNABLE_TO_PROCESS);
        }
    }

    @Override
    public Optional<T> getData(String key, Class<T> type) {
        String json = redisTemplate.opsForValue().get(key);

        if (json == null) return Optional.empty();

        return getDataOf(json, type);
    }

    @Override
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Optional<T> getDataAndDelete(String key, Class<T> type) {
        String json = redisTemplate.opsForValue().getAndDelete(key);

        if (json == null) return Optional.empty();

        return getDataOf(json, type);
    }

    private Optional<T> getDataOf(String json, Class<T> type) {
        try {
            T value = objectMapper.readValue(json, type);
            return Optional.of(value);
        } catch (Exception e) {
            log.error("",e);
            throw new UnableToProcessException(UNABLE_TO_PROCESS);
        }
    }
}
