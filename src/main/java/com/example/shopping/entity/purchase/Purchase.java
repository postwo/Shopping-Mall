package com.example.shopping.entity.purchase;

import com.example.shopping.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@ToString(exclude = {"member"})
@NoArgsConstructor
@AllArgsConstructor
public class Purchase { // 구매
    @Id
    @Column(name = "purchase_num", length = 30)
    private String purchaseNum;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "purchase_price", nullable = false)
    private Integer purchasePrice;

    @Column(name = "delivery_addr", length = 200)
    private String deliveryAddr;

    @Column(name = "delivery_addr_detail", length = 30)
    private String deliveryAddrDetail;

    @Column(name = "delivery_post", length = 6)
    private String deliveryPost;

    @Column(name = "delivery_phone", length = 13)
    private String deliveryPhone;

    @Column(name = "message", length = 200)
    private String message;

    @Column(name = "purchase_status", length = 20)
    private String purchaseStatus;

    @Column(name = "delivery_Name", length = 200)
    private String deliveryName;

    // 외래 키 매핑 (회원 번호)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Num", referencedColumnName = "member_Num") // on delete set null
    private Member member;
}
