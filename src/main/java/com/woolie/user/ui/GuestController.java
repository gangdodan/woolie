package com.woolie.user.ui;

import com.woolie.user.application.GuestTokenGenerateService;
import com.woolie.user.application.TokenGenerateService;
import com.woolie.user.domain.User;
import com.woolie.user.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.woolie.security.constants.SecurityConstants.ACCESS_TOKEN;
import static com.woolie.security.constants.SecurityConstants.REFRESH_HEADER;

@RestController
@RequestMapping("/auth/token")
@RequiredArgsConstructor
public class GuestController {
    private final GuestTokenGenerateService guestTokenGenerateService;
    private final TokenGenerateService tokenGenerateService;

    @GetMapping("/guest")
    public ResponseEntity<JwtTokenDto> getGuestUserToken() {
        User guestUser = guestTokenGenerateService.generate();
        JwtTokenDto jwtToken = tokenGenerateService.getJwtToken(guestUser.getId());

        return ResponseEntity.status(201)
                .header(ACCESS_TOKEN, jwtToken.getAccessToken())
                .header(REFRESH_HEADER, jwtToken.getRefreshToken())
                .build();
    }
}
