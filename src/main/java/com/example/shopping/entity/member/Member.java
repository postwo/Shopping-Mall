package com.example.shopping.entity.member;

import com.example.shopping.dto.member.RegisterRequest;
import com.example.shopping.entity.member.role.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // 2. Auditing 리스너 추가
public class Member implements UserDetails {
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

    @Column(length = 30)
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

    public Member(RegisterRequest dto) {
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
        this.role = MemberRole.USER;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 사용자 권한 반환
        return List.of(new SimpleGrantedAuthority("ROLE_" + MemberRole.USER)); // ROLE_ 접두사를 붙여 권한 부여
    }

    @Override
    public String getPassword() {
        return this.getMemberPw();
    }

    @Override
    public String getUsername() {
        return this.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}