package com.example.shopping.entity.goodsinquire;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInquireId implements Serializable {
    // inquire_num은 @GeneratedValue이므로 ID 클래스에서는 제외하고 엔티티에서 처리합니다.

    @Column(name = "goods_num", length = 10)
    private String goodsNum;

    @Column(name = "member_Num", length = 30)
    private String memberNum;
}
