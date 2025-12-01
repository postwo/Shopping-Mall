package com.example.shopping.entity.wish;

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
public class WishId implements Serializable {
    @Column(name = "goods_num", length = 10)
    private String goodsNum;

    @Column(name = "member_Num", length = 30)
    private String memberNum;
}
