package com.example.shopping.entity.retired;

import com.example.shopping.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "retired")
@Getter
@Setter
@ToString(exclude = {"employee"})
@NoArgsConstructor
@AllArgsConstructor
public class Retired { //퇴사자
    // PK이자 FK
    @Id
    @Column(name = "emp_num", length = 30)
    private String empNum;

    @Column(name = "retired_date", nullable = false)
    private LocalDate retiredDate;

    @Column(name = "job_title", length = 30)
    private String jobTitle;

    @Column(name = "reason", length = 200, nullable = false)
    private String reason;

    // 1:1 관계 (FK)
    @OneToOne
    @MapsId // Retired의 PK를 Employee의 FK로 사용
    @JoinColumn(name = "emp_num", referencedColumnName = "emp_num")
    private Employee employee;
}