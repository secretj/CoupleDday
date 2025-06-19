package com.coupledday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/oauth2/google")
    public String googleLogin() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/oauth2/kakao")
    public String kakaoLogin() {
        return "redirect:/oauth2/authorization/kakao";
    }

    @GetMapping("/oauth2/naver")
    public String naverLogin() {
        return "redirect:/oauth2/authorization/naver";
    }
}
