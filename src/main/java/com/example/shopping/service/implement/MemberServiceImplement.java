package com.example.shopping.service.implement;

import com.example.shopping.common.error.MemberErrorCode;
import com.example.shopping.common.exception.ApiException;
import com.example.shopping.dto.member.MemberRequest;
import com.example.shopping.entity.member.Member;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImplement implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Member registerMember(MemberRequest request) {
        String id = request.getMemberId();

        memberRepository.findByMemberId(id).ifPresent(member -> {
            throw new ApiException(MemberErrorCode.MEMBER_ALREADY_EXISTS);
        });

        String pw = request.getMemberPw();
        request.setMemberPw(passwordEncoder.encode(pw));
        Member createMember = new Member(request);

        return memberRepository.save(createMember);
    }
}
