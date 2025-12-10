package com.example.shopping.dto.member;

import com.example.shopping.entity.member.Member;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
public class MemberResponse  {
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

    public static MemberResponse createMember(Member member){
        return MemberResponse.builder()
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
