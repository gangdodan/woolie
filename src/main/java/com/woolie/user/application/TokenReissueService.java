package com.woolie.user.application;


import com.woolie.common.redis.RedisStore;
import com.woolie.security.domain.JwtTokenUtil;
import com.woolie.user.dto.JwtTokenDto;
import com.woolie.user.dto.RedisUserDto;
import com.woolie.user.exception.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.woolie.common.exception.enums.ErrorCode.DATA_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class TokenReissueService {
    private final RedisStore<RedisUserDto> redisStore;
    private final JwtTokenUtil jwtTokenUtil;
    private final TokenGenerateService tokenGenerateService;

    public JwtTokenDto reIssueToken(String refreshToken) {
        validateToken(refreshToken);
        RedisUserDto user = findValueOrThrowException(refreshToken);

        return tokenGenerateService.getJwtToken(user.getId());
    }

    private void validateToken(String refreshToken) {
        jwtTokenUtil.parseRefreshToken(refreshToken);
    }

    private RedisUserDto findValueOrThrowException(String refreshToken) {
        return redisStore.getDataAndDelete(refreshToken, RedisUserDto.class).orElseThrow(() -> new TokenNotFoundException(DATA_NOT_FOUND));
    }
}
