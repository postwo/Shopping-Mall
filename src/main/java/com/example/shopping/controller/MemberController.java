package com.example.shopping.controller;

import com.example.shopping.common.api.Api;
import com.example.shopping.dto.member.LoginRequest;
import com.example.shopping.dto.member.RegisterRequest;
import com.example.shopping.entity.member.Member;
import com.example.shopping.service.implement.MemberServiceImplement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImplement memberServiceImplement;

    @PostMapping("/register")
    public Api<Member> registerMember(@RequestBody RegisterRequest request){
        Member member = memberServiceImplement.registerMember(request);
        return Api.OK(member);
    }

    @PostMapping("/login")
    public Api<Member> loginMember(@RequestBody LoginRequest request, HttpServletResponse response){
        Member member = memberServiceImplement.login(request,response);
        return Api.OK(member);
    }
}
