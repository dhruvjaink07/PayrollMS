import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:payroll_app/model/Salary.dart';

class SalaryProvider extends ChangeNotifier {
  final Dio _dio = Dio(BaseOptions(
    baseUrl: 'http://localhost:8080',
    connectTimeout: const Duration(milliseconds: 5000),
    receiveTimeout: const Duration(milliseconds: 5000),
  ));

  List<Salary> salaryData = [];

  Future<List<Salary>> getSalaries() async {
    try {
      final Response response = await _dio.get('/api/salaries');
      if (response.statusCode == 200) {
        salaryData = (response.data as List)
            .map((json) => Salary.fromJson(json))
            .toList();
      }
    } catch (e) {
      debugPrint('Error fetching salary data: $e');
    }

    return salaryData;
  }

  Future<void> addSalary(Salary salary) async {
    try {
      final Response response = await _dio.post(
        '/api/salaries',
        data: salary.toJson(),
        options: Options(
          headers: {'Content-Type': 'application/json'},
        ),
      );

      if (response.statusCode == 200) {
        salaryData.add(Salary.fromJson(response.data));
      }
    } catch (e) {
      debugPrint('Error adding salary: $e');
    }
    notifyListeners();
  }
}
