package com.example.shopping.entity.review;

import com.example.shopping.entity.purchaseList.PurchaseList;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "review")
@Getter
@Setter
@ToString(exclude = {"purchaseList"})
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    // review_num을 단독 PK로 사용하고 (purchase_num, goods_num)은 유니크 제약조건으로 관리하는 방식이 더 일반적이지만,
    // 원본 DDL에서 (purchase_num, goods_num)을 PK로 사용하므로 복합키로 처리합니다.
    @EmbeddedId
    private ReviewId id;

    // serial 컬럼은 PK의 일부가 아닌 경우에 사용이 용이합니다. 여기서는 PK와 별도로 추가된 컬럼으로 처리합니다.
    @Column(name = "review_num")
    private Long reviewNum;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Column(name = "score")
    private Integer score;

    @Column(name = "review_content", length = 2000)
    private String reviewContent;

    @Column(name = "member_Id", length = 15)
    private String memberId;

    // 외래 키 매핑 (PurchaseList의 복합키를 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId // id의 필드명이 PurchaseListId의 필드명과 일치해야 합니다.
    @JoinColumns({
            @JoinColumn(name = "purchase_num", referencedColumnName = "purchase_num"),
            @JoinColumn(name = "goods_num", referencedColumnName = "goods_num")
    }) // on delete cascade
    private PurchaseList purchaseList;
}