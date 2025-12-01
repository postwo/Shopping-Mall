package com.example.shopping.entity.goodsinquire;

import com.example.shopping.entity.employee.Employee;
import com.example.shopping.entity.goods.Good;
import com.example.shopping.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "goods_inquire")
@Getter
@Setter
@ToString(exclude = {"good", "member", "employee"})
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInquire {
    // inquire_num을 PK의 일부이면서 자동 생성되는 값으로 처리
    @Id
    @Column(name = "inquire_num")
    private Long inquireNum;

    // 복합 키의 나머지 필드
    @Embedded
    private GoodsInquireId id;

    @Column(name = "inquire_subject", length = 100, nullable = false)
    private String inquireSubject;

    @Column(name = "inquire_content", length = 2000, nullable = false)
    private String inquireContent;

    @Column(name = "inquire_kind", length = 20, nullable = false)
    private String inquireKind;

    @Column(name = "inquire_date", nullable = false)
    private LocalDate inquireDate;

    @Column(name = "inquire_answer", length = 2000)
    private String inquireAnswer;

    @Column(name = "inquire_answer_date")
    private LocalDate inquireAnswerDate;

    // 외래 키 매핑 (상품 번호)
    @MapsId("goodsNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_num", referencedColumnName = "goods_num", insertable = false, updatable = false) // on delete cascade
    private Good good;

    // 외래 키 매핑 (회원 번호)
    @MapsId("memberNum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Num", referencedColumnName = "member_Num", insertable = false, updatable = false) // on delete cascade
    private Member member;

    // 외래 키 매핑 (직원 번호)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_num", referencedColumnName = "emp_num") // on delete set null
    private Employee employee;
}
