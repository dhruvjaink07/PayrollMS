package com.example.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pms.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

}
