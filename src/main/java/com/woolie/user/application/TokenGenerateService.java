package com.woolie.user.application;

import com.woolie.common.exception.enums.ErrorCode;
import com.woolie.common.redis.RedisStore;
import com.woolie.security.domain.JwtTokenUtil;
import com.woolie.user.domain.Role;
import com.woolie.user.domain.User;
import com.woolie.user.dto.JwtTokenDto;
import com.woolie.user.dto.RedisUserDto;
import com.woolie.user.exception.UserNotFoundException;
import com.woolie.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.woolie.security.constants.SecurityConstants.REFRESH_TOKEN_EXPIRE_COUNT;


@Service
@RequiredArgsConstructor
public class TokenGenerateService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final RedisStore<RedisUserDto> userRedisStore;

    public JwtTokenDto getJwtToken(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.DATA_NOT_FOUND));

        JwtTokenDto jwtTokenDto = generateTokenSet(user.getEmail(), user.getId(), user.getRole(), user.getUsername());
        saveRefreshToken(jwtTokenDto,user);

        return jwtTokenDto;
    }

    private JwtTokenDto generateTokenSet(String email, Long id, Role roles, String nickname) {
        String accessToken = jwtTokenUtil.createAccessToken(email, id, roles.getRoles(), nickname);
        String refreshToken = jwtTokenUtil.createRefreshToken(email, id, roles.getRoles(), nickname);

        return new JwtTokenDto(accessToken, refreshToken);
    }

    private void saveRefreshToken(JwtTokenDto jwtTokenDto, User user) {
        userRedisStore.saveData(jwtTokenDto.getRefreshToken(), RedisUserDto.of(user), REFRESH_TOKEN_EXPIRE_COUNT);
    }
}
