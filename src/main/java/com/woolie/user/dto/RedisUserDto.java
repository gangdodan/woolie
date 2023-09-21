package com.woolie.user.dto;


import com.woolie.user.domain.Role;
import com.woolie.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RedisUserDto {
    private final Long id;
    private final String nickname;
    private final String email;
    private final Role role;

    public static RedisUserDto of(User user) {
        if (user == null) {
            return null;
        }

        RedisUserDto.RedisUserDtoBuilder redisUserDto = RedisUserDto.builder();

        redisUserDto.id(user.getId());
        redisUserDto.nickname(user.getUsername());
        redisUserDto.email(user.getEmail());
        redisUserDto.role(user.getRole());

        return redisUserDto.build();
    }
}
