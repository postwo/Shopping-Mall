package com.example.shopping.domain.member;

import com.example.shopping.domain.cart.Cart;
import com.example.shopping.domain.goods.GoodsInquire;
import com.example.shopping.domain.purchase.Purchase;
import com.example.shopping.domain.wish.Wish;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member {

    @Id
    @Column(name = "member_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNum;

    @Column(name = "member_email", length = 50, nullable = false)
    private String memberEmail;

    @Column(name = "member_pw", length = 200, nullable = false)
    private String memberPw;

    @Column(name = "member_name", length = 15, nullable = false)
    private String memberName;

    @Column(name = "member_addr", length = 200, nullable = false)
    private String memberAddr;

    @Column(name = "member_addr_detail", length = 30)
    private String memberAddrDetail;

    @Column(name = "member_post", length = 6, nullable = false)
    private String memberPost;

    @Column(name = "member_phone1", length = 13, nullable = false)
    private String memberPhone;

    @Column(name = "member_regist", nullable = false)
    private LocalDate memberRegist;

    @Column(name = "member_birth", nullable = false)
    private LocalDate memberBirth;

    @Column(name = "member_email_conf")
    private String memberEmailConf;

    @Column(name = "point")
    private Integer point;

    // ========== 연관관계 ==========

    // 관심상품 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishList = new ArrayList<>();

    // 장바구니 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartList = new ArrayList<>();

    // 구매 내역 (1:N)
    @OneToMany(mappedBy = "member")
    private List<Purchase> purchaseList = new ArrayList<>();

    // 상품 문의 (1:N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsInquire> goodsInquireList = new ArrayList<>();
}

