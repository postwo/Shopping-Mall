package com.example.shopping.service;

import com.example.shopping.dto.member.MemberRequest;
import com.example.shopping.entity.member.Member;

public interface MemberService {

    Member registerMember(MemberRequest dto);

}
