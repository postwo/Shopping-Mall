package com.example.shopping.dto.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class MemberRequest {
    String memberId;
    @Setter
    String memberPw;
    String memberName;
    String memberAddr;
    String memberAddrDetail;
    String memberPost;
    String gender;
    String memberPhone;
    String memberEmail;
    LocalDate memberBirth;
}
