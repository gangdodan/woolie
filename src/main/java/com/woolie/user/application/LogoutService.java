package com.woolie.user.application;

import com.woolie.common.redis.RedisStore;
import com.woolie.user.dto.RedisUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final RedisStore<RedisUserDto> redisStore;

    public void logout(String refreshToken) {
        redisStore.deleteData(refreshToken);
    }
}
