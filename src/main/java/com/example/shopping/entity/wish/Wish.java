package com.example.shopping.entity.wish;

import com.example.shopping.entity.goods.Good;
import com.example.shopping.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "wish")
@Getter
@Setter
@ToString(exclude = {"good", "member"})
@NoArgsConstructor
@AllArgsConstructor
public class Wish {
    // @EmbeddedId: WishId를 이 엔티티의 복합키로 지정
    @EmbeddedId
    private WishId id;

    @Column(name = "wish_date", nullable = false)
    private LocalDate wishDate;

    // 외래 키 매핑 (상품 번호)
    // FK 매핑: 복합키의 필드와 실제 FK 연관 관계를 연결
    // @MapsId("goodsNum"): WishId 객체의 goodsNum 필드가 이 연관 관계를 담당한다고 JPA에 알린다
    @MapsId("goodsNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_num", referencedColumnName = "goods_num") // on delete cascade
    private Good good;

    // 외래 키 매핑 (회원 번호)
    @MapsId("memberNum")
    @ManyToOne(fetch = FetchType.LAZY)
    //referencedColumnName : 외래 키가 참조하는 대상 테이블의 컬럼 이름을 명시적으로 지정
    // insertable = false = INSERT 쿼리 생성 시, 해당 컬럼을 제외 = 엔티티를 저장할 때 이 외래 키 컬럼의 값은 데이터베이스에 넣지 않습니다.
    // updatable = false = UPDATE 쿼리 생성 시, 해당 컬럼을 제외 = 엔티티를 수정할 때 이 외래 키 컬럼의 값은 변경하지 않습니다.
    // 두 옵션은 현재 엔티티를 영속화(저장)하거나 변경할 때, 해당 @JoinColumn으로 매핑된 외래 키 값을 JPA가 데이터베이스에 쓰기(Write) 작업 할지 여부를 결정
    @JoinColumn(name = "member_Num", referencedColumnName = "member_Num") // on delete cascade
    private Member member;
}
