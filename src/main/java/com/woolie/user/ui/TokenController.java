package com.woolie.user.ui;

import com.woolie.common.resolver.Token;
import com.woolie.common.resolver.UserInfo;
import com.woolie.user.application.LogoutService;
import com.woolie.user.application.TokenReissueService;
import com.woolie.user.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.woolie.security.constants.SecurityConstants.ACCESS_TOKEN;
import static com.woolie.security.constants.SecurityConstants.REFRESH_HEADER;


@RestController
@RequestMapping("/oauth/token")
@RequiredArgsConstructor
public class TokenController {
    private final LogoutService logoutService;
    private final TokenReissueService tokenReissueService;

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToken(@RequestHeader(REFRESH_HEADER) String refreshToken) {
        logoutService.logout(refreshToken);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JwtTokenDto> reissueToken(@RequestHeader(REFRESH_HEADER) String refreshToken) {
        JwtTokenDto jwtTokenDto = tokenReissueService.reIssueToken(refreshToken);

        return ResponseEntity
                .status(200)
                .header(ACCESS_TOKEN, jwtTokenDto.getAccessToken())
                .header(REFRESH_HEADER, jwtTokenDto.getRefreshToken())
                .build();
    }
    @PostMapping("/test")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void test(@Token UserInfo request){
        System.out.println(request.getId());
    }
}
