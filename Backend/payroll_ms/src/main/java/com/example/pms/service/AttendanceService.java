package com.example.pms.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pms.entity.Attendance;
import com.example.pms.repository.AttendanceRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    private static final DateTimeFormatter TIME_FORMATTER_WITH_SECONDS = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER_WITHOUT_SECONDS = DateTimeFormatter.ofPattern("HH:mm");

    public List<Attendance> markAttendance(List<Attendance> attendances) {
        for (Attendance attendance : attendances) {
            if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
                try {
                    // Check if the time contains seconds, otherwise append ":00"
                    String checkInTimeStr = attendance.getCheckInTime().toString();
                    String checkOutTimeStr = attendance.getCheckOutTime().toString();
                    
                    LocalTime checkInTime = parseTime(checkInTimeStr);
                    LocalTime checkOutTime = parseTime(checkOutTimeStr);

                    // Calculate total hours worked
                    Duration workedHours = Duration.between(checkInTime, checkOutTime);
                    attendance.setTotalHoursWorked(workedHours.toHours() + (workedHours.toMinutesPart() / 60.0));
                } catch (Exception e) {
                    // Handle invalid time format or parsing errors
                    attendance.setTotalHoursWorked(0.0);
                    System.err.println("Error parsing time for attendance: " + e.getMessage());
                }
            } else {
                attendance.setTotalHoursWorked(0.0);
            }
        }
        return attendanceRepository.saveAll(attendances);
    }

    // Helper method to parse time with or without seconds
    private LocalTime parseTime(String timeStr) {
        if (timeStr.length() == 5) { // If time doesn't have seconds (HH:mm)
            timeStr = timeStr + ":00"; // Append ":00" to make it HH:mm:ss
        }
        return LocalTime.parse(timeStr, TIME_FORMATTER_WITH_SECONDS);
    }

    public List<Attendance> getAttendanceByEmployeeId(Integer id) {
        return attendanceRepository.findByEmployeeId(id);
    }

    public List<Attendance> getAttendanceForDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }
}
