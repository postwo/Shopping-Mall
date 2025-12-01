package com.example.shopping.entity.employee;

import com.example.shopping.entity.retired.Retired;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString(exclude = {"retired"})
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @Column(name = "emp_num", length = 30)
    private String empNum;

    @Column(name = "EMP_ID", length = 10, nullable = false, unique = true)
    private String empId;

    @Column(name = "EMP_PW", length = 200, nullable = false)
    private String empPw;

    @Column(name = "EMP_NAME", length = 50, nullable = false)
    private String empName;

    @Column(name = "EMP_ADDR", length = 200, nullable = false)
    private String empAddr;

    @Column(name = "EMP_ADDR_detail", length = 30)
    private String empAddrDetail;

    @Column(name = "EMP_post", length = 6, nullable = false)
    private String empPost;

    @Column(name = "EMP_PHONE", length = 13, nullable = false)
    private String empPhone;

    @Column(name = "emp_jumin", length = 14, nullable = false, unique = true)
    private String empJumin;

    @Column(name = "EMP_EMAIL", length = 200, nullable = false, unique = true)
    private String empEmail;

    @Column(name = "emp_enter_date", nullable = false)
    private LocalDate empEnterDate;

    // 1:1 관계 (Retired 테이블의 PK가 FK이므로 OneToOne)
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Retired retired;
}