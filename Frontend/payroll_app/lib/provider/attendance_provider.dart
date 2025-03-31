import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:payroll_app/model/Attendance.dart'; // For formatting time

class AttendanceProvider extends ChangeNotifier {
  final Dio _dio = Dio(BaseOptions(
    baseUrl: 'http://localhost:8080',
    connectTimeout: const Duration(milliseconds: 5000),
    receiveTimeout: const Duration(milliseconds: 5000),
  ));

  List<Attendance> attendanceData = [];

  AttendanceProvider() {
    getAttendanceData();
  }

  Future<void> getAttendanceData() async {
    try {
      final Response response = await _dio.get('/attendance');
      attendanceData = (response.data as List)
          .map((json) => Attendance.fromJson(json))
          .toList();
    } catch (e) {
      debugPrint('Error fetching attendance data: $e');
      // Handle error appropriately (log or update a UI-related state)
    }
    notifyListeners();
  }

  Future<void> addAttendanceData(List<Attendance> attendances) async {
    try {
      // Update UI optimistically
      final tempData = List<Attendance>.from(attendanceData)
        ..addAll(attendances);
      attendanceData = tempData;
      notifyListeners();

      // Convert times to the required format before sending to the server
      final formattedAttendances = attendances.map((attendance) {
        return attendance.copyWith(
          checkInTime: formatTime(attendance.checkInTime),
          checkOutTime: formatTime(attendance.checkOutTime),
        );
      }).toList();

      // Post to server
      final Response response = await _dio.post(
        '/attendance',
        data: attendances,
        options: Options(
          headers: {'Content-Type': 'application/json'},
        ),
      );

      if (response.statusCode == 200) {
        attendanceData = (response.data as List)
            .map((json) => Attendance.fromJson(json))
            .toList();
      }
    } catch (e) {
      debugPrint('Error recording attendance: $e');
      // Handle error without context, perhaps updating a UI-related state
    }
    notifyListeners();
  }

  String formatTime(String time) {
    try {
      // Parse the time into DateTime and reformat to HH:mm:ss
      final parsedTime = DateFormat("HH:mm").parse(time);
      return DateFormat("HH:mm:ss").format(parsedTime);
    } catch (e) {
      debugPrint("Error formatting time: $e");
      return "00:00:00"; // Default fallback if there's an error
    }
  }
}
