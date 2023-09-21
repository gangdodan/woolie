package com.woolie.security.oauth;


import com.woolie.user.domain.RandomPasswordGenerator;
import com.woolie.user.domain.User;
import com.woolie.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.woolie.user.domain.enums.RoleName.ROLE_USER;


@Component
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RandomPasswordGenerator randomPasswordGeneratorImpl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService defaultOAuth2Service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2Service.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuth2UserDto oauth2User = OAuth2UserDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        saveIfNotSavedUser(oauth2User);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(ROLE_USER.toString())),
                oAuth2User.getAttributes(), userNameAttributeName);
    }

    private void saveIfNotSavedUser(OAuth2UserDto oauth2User) {
        if (userRepository.findUserByEmail(oauth2User.getEmail()).isEmpty()) {
            saveOAuth2User(oauth2User);
        }
    }

    private void saveOAuth2User(OAuth2UserDto oauth2User) {
        String email = oauth2User.getEmail();
        String name = oauth2User.getName();
        String password = passwordEncoder.encode(randomPasswordGeneratorImpl.generateTemporaryPassword());
        userRepository.save(User.builder()
                        .username(name)
                        .email(email)
                        .password(password)
                .build());
    }
}
