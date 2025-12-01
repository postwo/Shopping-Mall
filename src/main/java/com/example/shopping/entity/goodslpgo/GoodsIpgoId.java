package com.example.shopping.entity.goodslpgo;

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
public class GoodsIpgoId implements Serializable { // 상품 입고 , 복합키
    @Column(name = "ipgo_num", length = 30)
    private String ipgoNum;

    @Column(name = "goods_num", length = 10)
    private String goodsNum;
}
