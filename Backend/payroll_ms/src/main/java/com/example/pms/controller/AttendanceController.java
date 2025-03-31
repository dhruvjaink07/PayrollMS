package com.example.pms.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.pms.entity.Attendance;
import com.example.pms.service.AttendanceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Mark attendance for multiple employees at once
    @PostMapping
    public List<Attendance> markAttendance(@RequestBody List<Attendance> attendances) {
        return attendanceService.markAttendance(attendances);
    }

    // Get attendance for a specific employee by their ID
    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getAttendanceByEmployeeId(@PathVariable Integer employeeId) {
        return attendanceService.getAttendanceByEmployeeId(employeeId);
    }

    // Get attendance records for a specific date
    @GetMapping("/date/{date}")
    public List<Attendance> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return attendanceService.getAttendanceForDate(date);
    }
}
