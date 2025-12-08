package com.example.shopping.entity.member;

import com.example.shopping.dto.member.MemberRequest;
import com.example.shopping.entity.member.role.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener; // 추가
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // 2. Auditing 리스너 추가
public class Member {
    @Id
    @Column(name = "member_num") // 3. 컬럼명 스네이크 케이스로 변경
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNum;

    @Column(name = "member_id", length = 15, nullable = false, unique = true)
    private String memberId;

    @Column(name = "member_pw", length = 200, nullable = false)
    private String memberPw;

    @Column(name = "member_name", length = 15, nullable = false)
    private String memberName;

    @Column(name = "member_addr", length = 200, nullable = false)
    private String memberAddr;

    @Column(name = "member_addr_detail", length = 30)
    private String memberAddrDetail;

    @Column(name = "member_post", length = 6, nullable = false)
    private String memberPost;

    @Column(name = "member_phone1", length = 13, nullable = false)
    private String memberPhone;

    @CreatedDate
    @Column(name = "member_regist", nullable = false, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate memberRegist;

    @Column(name = "member_birth", nullable = false)
    private LocalDate memberBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "member_email", length = 100, nullable = false, unique = true)
    private String memberEmail;

    @Column(name = "member_email_conf")
    private String memberEmailConf;

    @Column(name = "point")
    private Integer point;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberNum, member.memberNum);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberNum);
    }

    public Member(MemberRequest dto) {
        this.memberId = dto.getMemberId();
        this.memberPw = dto.getMemberPw();
        this.memberName = dto.getMemberName();
        this.memberAddr = dto.getMemberAddr();
        this.memberAddrDetail = dto.getMemberAddrDetail();
        this.memberPost = dto.getMemberPost();
        this.memberPhone = dto.getMemberPhone();
        this.memberBirth = dto.getMemberBirth();
        this.gender = dto.getGender();
        this.memberEmail = dto.getMemberEmail();
        this.memberEmailConf = dto.getMemberEmail();
        this.role = MemberRole.ROLE_USER;
    }
}