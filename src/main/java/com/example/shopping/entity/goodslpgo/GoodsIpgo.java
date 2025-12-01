package com.example.shopping.entity.goodslpgo;

import com.example.shopping.entity.employee.Employee;
import com.example.shopping.entity.goods.Good;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "goodsIpgo")
@Getter
@Setter
@ToString(exclude = {"good", "employee"})
@NoArgsConstructor
@AllArgsConstructor
public class GoodsIpgo { // 상품입고
    @EmbeddedId
    private GoodsIpgoId id;

    @Column(name = "ipgo_qty", nullable = false)
    private Integer ipgoQty;

    @Column(name = "made_date", nullable = false)
    private LocalDate madeDate;

    @Column(name = "ipgo_price", nullable = false)
    private Integer ipgoPrice;

    @Column(name = "ipgo_date", nullable = false)
    private LocalDate ipgoDate;

    // 외래 키 매핑 (상품 번호)
    @MapsId("goodsNum") // 복합키 필드 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_num", referencedColumnName = "goods_num")
    private Good good;

    // 외래 키 매핑 (직원 번호)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_num", referencedColumnName = "emp_num") // on delete set null
    private Employee employee;
}