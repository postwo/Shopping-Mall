package com.example.shopping.entity.cart;

import com.example.shopping.entity.goods.Good;
import com.example.shopping.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString(exclude = {"good", "member"})
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @EmbeddedId
    private CartId id;

    @Column(name = "cart_date", nullable = false)
    private LocalDate cartDate;

    @Column(name = "cart_qty", nullable = false)
    private Integer cartQty;

    // 외래 키 매핑 (상품 번호)
    @MapsId("goodsNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_num", referencedColumnName = "goods_num") // on delete cascade
    private Good good;

    // 외래 키 매핑 (회원 번호)
    @MapsId("memberNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Num", referencedColumnName = "member_Num") // on delete cascade
    private Member member;
}