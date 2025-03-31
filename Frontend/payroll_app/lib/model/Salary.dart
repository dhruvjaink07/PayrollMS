class Salary {
  final int? salaryId; // Nullable
  final int? employeeId; // Nullable, handle null cases
  final double basicSalary;
  final double bonus;
  final double deductions;
  final double totalSalary;
  final DateTime paymentDate;

  Salary({
    this.salaryId,
    this.employeeId,
    required this.basicSalary,
    required this.bonus,
    required this.deductions,
    required this.totalSalary,
    required this.paymentDate,
  });

  factory Salary.fromJson(Map<String, dynamic> json) {
    return Salary(
      salaryId: json['salaryId'] as int?, // Nullable
      employeeId: json['employeeId'] as int?, // Nullable
      basicSalary: (json['basicSalary'] ?? 0.0).toDouble(), // Provide default
      bonus: (json['bonus'] ?? 0.0).toDouble(), // Provide default
      deductions: (json['deductions'] ?? 0.0).toDouble(), // Provide default
      totalSalary: (json['totalSalary'] ?? 0.0).toDouble(), // Provide default
      paymentDate: json['paymentDate'] != null
          ? DateTime.parse(json['paymentDate'] as String)
          : DateTime.now(), // Fallback to current date
    );
  }

  // Method to convert Salary object to JSON
  Map<String, dynamic> toJson() {
    return {
      'salaryId': salaryId,
      'employeeId': employeeId,
      'basicSalary': basicSalary,
      'bonus': bonus,
      'deductions': deductions,
      'totalSalary': totalSalary,
      'paymentDate': paymentDate.toIso8601String(), // ISO 8601 formatted string
    };
  }
}
