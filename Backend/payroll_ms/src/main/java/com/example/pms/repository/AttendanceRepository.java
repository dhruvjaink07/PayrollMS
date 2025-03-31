package com.example.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pms.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeId(Integer employeeId);
    List<Attendance> findByDate(LocalDate date);
    @Query("SELECT a FROM Attendance a WHERE a.employeeId = :employeeId AND MONTH(a.date) = :month AND YEAR(a.date) = :year")
    List<Attendance> findByEmployeeIdAndMonth(@Param("employeeId") Integer employeeId, 
                                              @Param("month") int month, 
                                              @Param("year") int year);
}
