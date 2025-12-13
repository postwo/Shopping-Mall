package com.example.shopping.dto.member;

import com.example.shopping.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class RegisterResponse {
    Long memberNum;
    String memberId;
    String memberPw;
    String memberName;
    String memberAddr;
    String memberAddrDetail;
    String memberPost;
    LocalDate memberRegistDate;
    String gender;
    String memberPhone;
    String memberEmail;
    LocalDate memberBirth;
    Integer point;

    public static RegisterResponse createMember(Member member){
        return RegisterResponse.builder()
                .memberNum(member.getMemberNum())
                .memberId(member.getMemberId())
                .memberPw(member.getMemberPw())
                .memberName(member.getMemberName())
                .memberAddr(member.getMemberAddr())
                .memberAddrDetail(member.getMemberAddrDetail())
                .memberPost(member.getMemberPost())
                .memberRegistDate(member.getMemberRegist())
                .gender(member.getGender())
                .memberPhone(member.getMemberPhone())
                .memberEmail(member.getMemberEmail())
                .memberBirth(member.getMemberBirth())
                .point(member.getPoint())
                .build();
    }
}
