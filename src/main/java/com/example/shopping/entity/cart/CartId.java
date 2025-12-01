package com.example.shopping.entity.cart;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable //이 클래스가 엔티티의 일부로 포함될 수 있음을 나타낸다
@Getter
@Setter
@EqualsAndHashCode //필수: 복합키는 동등성(equals) 비교가 가능
@NoArgsConstructor
@AllArgsConstructor
public class CartId implements Serializable {
    @Column(name = "goods_num", length = 10)
    private String goodsNum;

    @Column(name = "member_Num", length = 30)
    private String memberNum;
}