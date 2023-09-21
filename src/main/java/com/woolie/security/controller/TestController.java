package com.woolie.security.controller;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.woolie.common.resolver.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/all")
    public String test1() {
        return "all";
    }

    @GetMapping("/user")
    public String test2() {
        return "all";
    }

    @GetMapping("/manager")
    public String test3() {
        return "all";
    }

    @GetMapping("/admin")
    public String admin() {
        return "all";
    }

    @PostMapping("/resolver")
    public String resolver(@Token UserInfo userInfo) {
        return userInfo.getEmailAddress();
    }
}
