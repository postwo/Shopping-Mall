package com.example.shopping.controller;

import com.example.shopping.common.api.Api;
import com.example.shopping.dto.jwt.TokenRefreshResponse;
import com.example.shopping.dto.member.LoginRequest;
import com.example.shopping.dto.member.LoginResponse;
import com.example.shopping.dto.member.RegisterRequest;
import com.example.shopping.dto.member.RegisterResponse;
import com.example.shopping.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public Api<RegisterResponse> registerMember(@RequestBody RegisterRequest request){
        RegisterResponse register = memberService.registerMember(request);
        return Api.OK(register);
    }

    @PostMapping("/login")
    public Api<LoginResponse> loginMember(@RequestBody LoginRequest request, HttpServletResponse response){
        LoginResponse login = memberService.login(request,response);
        return Api.OK(login);
    }

    @PostMapping("/refresh")
    public Api<TokenRefreshResponse> refreshToken(HttpServletRequest request) {
        String refreshToken = getRefreshTokenFromCookies(request);
        TokenRefreshResponse tokenRefreshResponse = memberService.refreshToken(refreshToken);
        return Api.OK(tokenRefreshResponse);
    }

    @PostMapping("/logout")
    public Api<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = extractAccessToken(request);
        String refreshToken = getRefreshTokenFromCookies(request);
        Map<String, String> result = memberService.logout(accessToken, refreshToken, response);
        return Api.OK(result);
    }

    @GetMapping("/me")
    public Api<Map<String, String>> getCurrentUser(HttpServletRequest request) {
        Map<String, String> userInfo = memberService.getCurrentUser(request);
        return Api.OK(userInfo);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
