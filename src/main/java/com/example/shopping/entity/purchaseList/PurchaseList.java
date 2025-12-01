package com.example.shopping.entity.purchaseList;

import com.example.shopping.entity.goods.Good;
import com.example.shopping.entity.purchase.Purchase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_list")
@Getter
@Setter
@ToString(exclude = {"purchase", "good"})
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseList {
    @EmbeddedId
    private PurchaseListId id;

    @Column(name = "purchase_qty", nullable = false)
    private Integer purchaseQty;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    // 외래 키 매핑 (구매 번호)
    @MapsId("purchaseNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_num", referencedColumnName = "purchase_num") // on delete cascade
    private Purchase purchase;

    // 외래 키 매핑 (상품 번호)
    @MapsId("goodsNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_num", referencedColumnName = "goods_num")
    private Good good;
}
