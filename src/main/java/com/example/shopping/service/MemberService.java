package com.example.shopping.service;

import com.example.shopping.dto.jwt.TokenRefreshResponse;
import com.example.shopping.dto.member.LoginRequest;
import com.example.shopping.dto.member.LoginResponse;
import com.example.shopping.dto.member.RegisterRequest;
import com.example.shopping.dto.member.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface MemberService {

    RegisterResponse registerMember(RegisterRequest dto);
    LoginResponse login(LoginRequest request, HttpServletResponse response);
    TokenRefreshResponse refreshToken(String refreshToken);
    Map<String, String> logout(String accessToken, String refreshToken, HttpServletResponse response);
    Map<String, String> getCurrentUser(HttpServletRequest request);

}
