package com.example.shopping.entity.goods;

import com.example.shopping.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "goods")
@Getter
@Setter
@ToString(exclude = {"emp", "updateEmp"})
@NoArgsConstructor
@AllArgsConstructor
public class Good { // 상품
    @Id
    @Column(name = "goods_num", length = 10)
    private String goodsNum;

    @Column(name = "goods_name", length = 100, nullable = false)
    private String goodsName;

    @Column(name = "goods_price", nullable = false)
    private Integer goodsPrice;

    @Column(name = "goods_content", length = 2000, nullable = false)
    private String goodsContent;

    @Column(name = "goods_main_store", length = 500, nullable = false)
    private String goodsMainStore;

    @Column(name = "goods_main_store_img", length = 500, nullable = false)
    private String goodsMainStoreImg;

    @Column(name = "goods_images", length = 500)
    private String goodsImages;

    @Column(name = "goods_images_img", length = 500)
    private String goodsImagesImg;

    @Column(name = "delivery_cost")
    private Integer deliveryCost = 0;

    @Column(name = "visit_count")
    private Integer visitCount = 0;

    @Column(name = "goods_regist", nullable = false)
    private LocalDate goodsRegist;

    @Column(name = "goods_update_date")
    private LocalDate goodsUpdateDate;

    // 외래 키 매핑 (등록 직원)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_num", referencedColumnName = "emp_num") // on delete set null
    private Employee emp;

    // 외래 키 매핑 (수정 직원)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_emp_num", referencedColumnName = "emp_num") // on delete set null
    private Employee updateEmp;
}
