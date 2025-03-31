package com.example.pms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salary")  // Table name in the database
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generated primary key
    @Column(name = "salary_id")  // Column name in the database
    private Integer salaryId;

    @Column(name = "employee_id")  // Column name in the database
    private Integer employeeId;

    @Column(name = "basic_salary")  // Column name in the database
    private BigDecimal basicSalary;

    @Column(name = "bonus")  // Column name in the database
    private BigDecimal bonus;

    @Column(name = "deductions")  // Column name in the database
    private BigDecimal deductions;

    @Column(name = "total_salary")  // Column name in the database
    private BigDecimal totalSalary;

    @Column(name = "payment_date")  // Column name in the database
    private LocalDate paymentDate;
}
