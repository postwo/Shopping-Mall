package com.example.shopping.entity.review;

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
public class ReviewId implements Serializable {
    @Column(name = "purchase_num", length = 30)
    private String purchaseNum;

    @Column(name = "goods_num", length = 10)
    private String goodsNum;
}
