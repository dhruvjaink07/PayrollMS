package com.example.pms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pms.entity.Attendance;
import com.example.pms.entity.Salary;
import com.example.pms.entity.SalaryStatistics;
import com.example.pms.repository.AttendanceRepository;
import com.example.pms.repository.EmployeeRepository;
import com.example.pms.repository.SalaryRepository;

import jakarta.transaction.Transactional;

@Service
public class SalaryService {
    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired 
    private EmployeeRepository employeeRepository;
    
    @Transactional
    public Salary addSalary(Salary salary) {
        BigDecimal totalSalary = salary.getBasicSalary().add(salary.getBonus()).subtract(salary.getDeductions());
        salary.setTotalSalary(totalSalary);
        return salaryRepository.save(salary);
    }

    public List<Salary> getSalaryByEmployeeId(Integer employeeId) {
        return salaryRepository.findSalaryByEmployeeId(employeeId);
    }
    
    public List<Salary> getSalaryOfAllEmployees(){
    	return salaryRepository.findAll();
    }

    // Method to calculate salary statistics for all employees
    public SalaryStatistics calculateSalaryStatistics() {
        List<Salary> salaries = salaryRepository.findAll();  // Fetch all salaries

        if (salaries.isEmpty()) {
            return new SalaryStatistics(0.0, 0.0, 0.0, 0L);  // If no data is available
        }

        OptionalDouble avgSalary = salaries.stream().mapToDouble(s -> s.getTotalSalary().doubleValue()).average();
        Double maxSalary = salaries.stream().mapToDouble(s -> s.getTotalSalary().doubleValue()).max().orElse(0.0);
        Double minSalary = salaries.stream().mapToDouble(s -> s.getTotalSalary().doubleValue()).min().orElse(0.0);
        Long totalEmployees = (long) salaries.size();

        return new SalaryStatistics(
                avgSalary.orElse(0.0),  // Default to 0.0 if no salaries exist
                maxSalary,
                minSalary,
                totalEmployees
        );
    }
    
    // Method to delete salary by employee ID
    public void deleteSalaryByEmployeeId(Integer employeeId) {
        salaryRepository.deleteByEmployeeId(employeeId);
    }

    public BigDecimal calculateTax(BigDecimal salary) {
        BigDecimal tax = BigDecimal.ZERO;

        if (salary.compareTo(BigDecimal.valueOf(50000)) > 0) {
            tax = salary.multiply(BigDecimal.valueOf(0.1));  // 10% tax on salaries over 50000
        }

        return tax;
    }
    
    // Calculate Monthly Salary
    public BigDecimal calculateMonthlySalary(Integer employeeId, int month, int year) {
        List<Attendance> attendances = attendanceRepository.findByEmployeeIdAndMonth(employeeId, month, year);
        // Fetch the employee's base salary
        BigDecimal baseSalary = employeeRepository.findById(employeeId).orElseThrow(
            () -> new IllegalArgumentException("Employee not found with ID: " + employeeId)
        ).getSalary();
        
        // Calculating Daily Salary
        BigDecimal dailySalary = baseSalary.divide(BigDecimal.valueOf(30),RoundingMode.HALF_UP);
        
        // Calculate total Deductions
        BigDecimal totalDeductions = BigDecimal.valueOf(attendances.stream()
                .filter(a -> "Absent".equalsIgnoreCase(a.getStatus()))
                .count()).multiply(dailySalary);
        
        // Calculate Final Salary
        return baseSalary.subtract(totalDeductions);
    }
}
