package com.example.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.pms.entity.Salary;

import jakarta.transaction.Transactional;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
	@Query(value = "SELECT * FROM salary WHERE employee_id = :employeeId", nativeQuery = true)
	List<Salary> findSalaryByEmployeeId(@Param("employeeId") Integer employeeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Salary s WHERE s.employeeId = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") Integer employeeId);

}
