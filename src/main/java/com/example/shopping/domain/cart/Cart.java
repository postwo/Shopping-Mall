package com.example.shopping.domain.cart;

import com.example.shopping.domain.goods.Goods;
import com.example.shopping.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
@IdClass(CartId.class)
public class Cart {

    @Id
    @Column(name = "goods_num", length = 10)
    private String goodsNum;

    @Id
    @Column(name = "member_num", length = 30)
    private String memberNum;

    // FK -> goods(goods_num) ON DELETE CASCADE
    @ManyToOne
    @JoinColumn(name = "goods_num", referencedColumnName = "goods_num")
    private Goods goods;

    // FK -> members(member_num) ON DELETE CASCADE
    @ManyToOne
    @JoinColumn(name = "member_num", referencedColumnName = "member_num")
    private Member member;

    @Setter
    @Column(name = "cart_date", nullable = false)
    private LocalDate cartDate;

    @Setter
    @Column(name = "cart_qty", nullable = false)
    private int cartQty;

    // ========== Custom Setters (FK 동기화) ==========

    public void setGoodsNum(String goodsNum) { this.goodsNum = goodsNum; }

    public void setMemberNum(String memberNum) { this.memberNum = memberNum; }

    public void setGoods(Goods goods) {
        this.goods = goods;
        this.goodsNum = goods.getGoodsNum();
    }

    public void setMember(Member member) {
        this.member = member;
        this.memberNum = member.getMemberNum();
    }
}
