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
@Table(name = "employee")  // Specifies the table name in the database
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generated primary key
    @Column(name = "employee_id")  // Column name in the database
    private Integer employeeId;

    @Column(name = "first_name")  // Column name in the database
    private String firstName;

    @Column(name = "last_name")  // Column name in the database
    private String lastName;

    @Column(name = "designation")  // Column name in the database
    private String designation;

    @Column(name = "department")  // Column name in the database
    private String department;

    @Column(name = "salary")  // Column name in the database
    private BigDecimal salary;

    @Column(name = "date_of_joining")  // Column name in the database
    private LocalDate dateOfJoining;
}
