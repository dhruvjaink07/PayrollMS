package com.example.pms.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pms.entity.Attendance;
import com.example.pms.entity.Employee;
import com.example.pms.entity.Salary;
import com.example.pms.repository.AttendanceRepository;
import com.example.pms.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private SalaryService salaryService;
    
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Transactional
    public Employee save(Employee employee) {
        // Ensure the employee ID is null for new employees
        employee.setEmployeeId(null);

        // Save the employee to the database
        Employee savedEmployee = employeeRepository.save(employee);

        // Create and save a default salary for the new employee
        Salary defaultSalary = new Salary();
        defaultSalary.setEmployeeId(savedEmployee.getEmployeeId());  // Associate the salary with the employee
        defaultSalary.setBasicSalary(BigDecimal.valueOf(30000));     // Set a default basic salary
        defaultSalary.setBonus(BigDecimal.valueOf(3000));                     // Default bonus
        defaultSalary.setDeductions(BigDecimal.valueOf(2000));              // Default deductions
        defaultSalary.setPaymentDate(LocalDate.now());
        salaryService.addSalary(defaultSalary);

        return savedEmployee;
    }

    public Employee getEmployee(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setDesignation(updatedEmployee.getDesignation());
            employee.setSalary(updatedEmployee.getSalary());
            employee.setDateOfJoining(updatedEmployee.getDateOfJoining());
            return employeeRepository.save(employee);
        }
        return null;
    }

    @Transactional
    public boolean deleteEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(id);
        if (!attendances.isEmpty()) {
            attendanceRepository.deleteAll(attendances);
        }
        if (employee.isPresent()) {
            // Delete associated salary records first
            salaryService.deleteSalaryByEmployeeId(id);

            // Then delete the employee record
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
