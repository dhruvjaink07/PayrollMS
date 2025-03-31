package com.example.pms.utility;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryResponse {
    private Integer employeeId;
    private BigDecimal monthlySalary;
    private String monthYear;
}