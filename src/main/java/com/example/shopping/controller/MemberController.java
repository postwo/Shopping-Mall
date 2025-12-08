package com.example.shopping.controller;

import com.example.shopping.common.api.Api;
import com.example.shopping.dto.member.MemberRequest;
import com.example.shopping.entity.member.Member;
import com.example.shopping.service.implement.MemberServiceImplement;
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
    public Api<Member> registerMember(@RequestBody MemberRequest request){
        Member member = memberServiceImplement.registerMember(request);
        return Api.OK(member);
    }
}
