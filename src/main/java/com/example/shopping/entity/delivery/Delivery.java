package com.example.shopping.entity.delivery;

import com.example.shopping.entity.purchase.Purchase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@ToString(exclude = {"purchase"})
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    // PK이자 FK
    @Id
    @Column(name = "purchase_num", length = 30)
    private String purchaseNum;

    @Column(name = "delivery_num", length = 20, unique = true)
    private String deliveryNum;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "DELIVERY_STATE", length = 20)
    private String deliveryState;

    // 1:1 관계 (FK)
    @OneToOne
    @MapsId // Delivery의 PK를 Purchase의 FK로 사용
    @JoinColumn(name = "purchase_num", referencedColumnName = "purchase_num") // on delete cascade
    private Purchase purchase;
}
