package com.example.shopping.repository;

import com.example.shopping.entity.member.Member;
import com.example.shopping.entity.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // 토큰 문자열로 RefreshToken 엔티티 조회
    Optional<RefreshToken> findByToken(String token);

    // 특정 사용자와 연결된 RefreshToken 조회
    Optional<RefreshToken> findByMember(Member member);

    // 특정 사용자와 연결된 RefreshToken 삭제
    void deleteByMember(Member member);

    // 토큰 문자열로 RefreshToken 삭제
    void deleteByToken(String token);
}
