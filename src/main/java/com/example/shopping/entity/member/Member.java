package com.example.shopping.entity.member;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "member_Num", length = 30)
    private String memberNum;

    @Column(name = "member_Id", length = 15, nullable = false, unique = true)
    private String memberId;

    @Column(name = "member_Pw", length = 200, nullable = false)
    private String memberPw;

    @Column(name = "member_Name", length = 15, nullable = false)
    private String memberName;

    @Column(name = "member_addr", length = 200, nullable = false)
    private String memberAddr;

    @Column(name = "member_addr_detail", length = 30)
    private String memberAddrDetail;

    @Column(name = "member_post", length = 6, nullable = false)
    private String memberPost;

    @Column(name = "member_phone1", length = 13, nullable = false)
    private String memberPhone1;

    @Column(name = "member_phone2", length = 13)
    private String memberPhone2;

    @Column(name = "member_regist", nullable = false)
    private LocalDate memberRegist;

    @Column(name = "member_birth", nullable = false)
    private LocalDate memberBirth;

    @Column(name = "gender", length = 1, nullable = false)
    private Character gender;

    @Column(name = "member_email", length = 100, nullable = false, unique = true)
    private String memberEmail;

    @Column(name = "member_email_conf", length = 1)
    private Character memberEmailConf;

    @Column(name = "point")
    private Integer point;
}
