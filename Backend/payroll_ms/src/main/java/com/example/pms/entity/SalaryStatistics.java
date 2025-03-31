package com.example.pms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryStatistics {
	    private Double averageSalary;
	    private Double maxSalary;
	    private Double minSalary;
	    private Long totalEmployees;
}
