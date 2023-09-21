package com.woolie.user.application;

import com.woolie.common.RandomKeyGenerator;
import com.woolie.user.domain.User;
import com.woolie.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestTokenGenerateService {
    private final UserRepository userRepository;
    private final RandomKeyGenerator randomKeyGenerator;
    private final PasswordEncoder passwordEncoder;

    public User generate() {
        String randomKey = randomKeyGenerator.generateRandomKey(6);
        String nickname = "Guest" + randomKey;
        String password = passwordEncoder.encode(randomKey);

        User user = User.builder()
                .username(nickname)
                .email(nickname + "@guest.com")
                .password(password)
                .build();

        return userRepository.save(user);
    }
}
