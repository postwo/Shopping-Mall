package com.example.shopping.entity.payment;

import com.example.shopping.entity.purchase.Purchase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment")
@Getter
@Setter
@ToString(exclude = {"purchase"})
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    // PK이자 FK
    @Id
    @Column(name = "purchase_Num", length = 30)
    private String purchaseNum;

    @Column(name = "confirmNumber", length = 50)
    private String confirmNumber;

    @Column(name = "cardNum", length = 50) // 카드번호
    private String cardNum;

    @Column(name = "tid", length = 50) // 거래번호
    private String tid;

    @Column(name = "totalPrice", length = 50) // 결제 금액
    private String totalPrice;

    @Column(name = "resultMessage", length = 50) // 결과 메세지
    private String resultMessage;

    @Column(name = "payMethod", length = 50) // 결제 방법
    private String payMethod;

    @Column(name = "applDate", length = 50) // 승인날짜
    private String applDate;

    @Column(name = "applTime", length = 50) // 승인시간
    private String applTime;

    @Column(name = "purchaseName", length = 50)
    private String purchaseName;

    // 1:1 관계 (FK)
    @OneToOne
    @MapsId // Payment의 PK를 Purchase의 FK로 사용
    @JoinColumn(name = "purchase_Num", referencedColumnName = "purchase_num") // on delete cascade
    private Purchase purchase;
}
