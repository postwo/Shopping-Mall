package com.example.shopping.entity.token;

import com.example.shopping.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id // 기본 키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 전략 사용
    private Long id; // 리프레시 토큰 고유 식별자 (PK)

    @Column(nullable = false, unique = true) // null 불가 및 중복 방지
    private String token; // 실제 리프레시 토큰 문자열

    @OneToOne // 사용자와 1:1 관계 (하나의 사용자당 하나의 리프레시 토큰)
    @JoinColumn(name = "member_num", referencedColumnName = "member_num")
    private Member member; // 토큰이 속한 사용자 정보

    @Column(name = "expires_at", nullable = false) // 만료일 저장 (null 불가)
    private LocalDateTime expiresAt; // 리프레시 토큰 만료 시각

    @Column(name = "created_at") // 생성일 컬럼 지정
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시각 기본값 현재 시간으로 설정

    /**
     * 리프레시 토큰의 만료 여부를 확인하는 메서드
     * @return true: 만료됨, false: 유효함
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt); // 현재 시간이 만료 시간 이후인지 비교
    }
}
