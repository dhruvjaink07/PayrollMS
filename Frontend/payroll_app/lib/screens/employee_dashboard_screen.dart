import 'package:flutter/material.dart';
import 'package:payroll_app/model/Employee.dart';
import 'package:payroll_app/model/Salary.dart';
import 'package:payroll_app/provider/employee_provider.dart';
import 'package:payroll_app/provider/salary_provider.dart';
import 'package:payroll_app/screens/add_employee.dart';
import 'package:payroll_app/screens/attendance_screen.dart';
import 'package:payroll_app/screens/pie_chart.dart'; // Ensure this is the correct pie chart import
import 'package:payroll_app/screens/update_employee_screen.dart';
import 'package:provider/provider.dart';

class EmployeeDashBoard extends StatefulWidget {
  const EmployeeDashBoard({super.key});

  @override
  _EmployeeDashBoardState createState() => _EmployeeDashBoardState();
}

class _EmployeeDashBoardState extends State<EmployeeDashBoard> {
  @override
  void initState() {
    super.initState();
    // Fetch salary data when the screen is loaded
    Future.microtask(() {
      Provider.of<SalaryProvider>(context, listen: false).getSalaries();
    });
  }

  void navigateToAddEmployeeScreen() {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => AddEmployeeScreen(),
      ),
    );
  }

  void navigateToUpdateEmployeeScreen(Employee employee) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => UpdateEmployeeScreen(employee: employee),
      ),
    );
  }

  void navigateToAttendanceScreen(List<Employee> employees) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => AttendanceScreen(employees: employees),
      ),
    );
  }

  void deleteEmployee(int employeeId) {
    final provider = Provider.of<EmployeeProvider>(context, listen: false);
    provider.deleteEmployee(employeeId);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Employee Dashboard'),
        actions: [
          IconButton(
              onPressed: () {
                navigateToAttendanceScreen(
                    Provider.of<EmployeeProvider>(context, listen: false)
                        .employees);
              },
              icon: const Icon(Icons.check_box)),
          IconButton(
              onPressed: navigateToAddEmployeeScreen,
              icon: const Icon(Icons.person_add))
        ],
      ),
      body: Consumer<EmployeeProvider>(
        builder: (context, provider, child) {
          if (provider.isLoading) {
            return const Center(child: CircularProgressIndicator());
          }
          if (provider.employees.isEmpty) {
            return const Center(child: Text('No employees found.'));
          }
          return LayoutBuilder(
            builder: (context, constraints) {
              if (constraints.maxWidth > 600) {
                return _buildWideContainers(provider.employees);
              } else {
                return _buildNarrowContainers(provider.employees);
              }
            },
          );
        },
      ),
    );
  }

  Widget _buildWideContainers(List<Employee> employees) {
    return GridView.builder(
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        childAspectRatio: 2.3,
        crossAxisSpacing: 20,
        mainAxisSpacing: 20,
      ),
      itemCount: employees.length,
      itemBuilder: (context, index) {
        return _buildEmployeeCard(employees[index]);
      },
    );
  }

  Widget _buildNarrowContainers(List<Employee> employees) {
    return ListView.builder(
      itemCount: employees.length,
      itemBuilder: (context, index) {
        return _buildEmployeeCard(employees[index]);
      },
    );
  }

  Widget _buildEmployeeCard(Employee employee) {
    // Fetch the salary data for the employee
    final salary = Provider.of<SalaryProvider>(context).salaryData.firstWhere(
        (salary) => salary.employeeId == employee.employeeId,
        orElse: () => Salary(
            salaryId: 0,
            employeeId: employee.employeeId!,
            basicSalary: 0.0,
            bonus: 0.0,
            deductions: 0.0,
            totalSalary: 0.0,
            paymentDate: DateTime.now()));

    final dataMap = {
      "Basic Salary": salary.basicSalary,
      "Bonus": salary.bonus,
      "Deductions": salary.deductions,
    };

    final colorList = [
      Colors.blueAccent,
      Colors.greenAccent,
      Colors.redAccent,
    ];

    return Card(
      elevation: 5,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(12),
      ),
      margin: const EdgeInsets.all(10),
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 12, horizontal: 16),
        child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        CircleAvatar(
                          radius: 24,
                          backgroundColor: Colors.blue,
                          child: Text(
                            '${employee.firstName[0]}${employee.lastName[0]}',
                            style: const TextStyle(
                                color: Colors.white,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                        const SizedBox(width: 10),
                        Text(
                          '${employee.firstName} ${employee.lastName}',
                          style: const TextStyle(
                              fontSize: 16, fontWeight: FontWeight.bold),
                          overflow: TextOverflow.ellipsis,
                        ),
                      ],
                    ),
                    const SizedBox(height: 8),
                    Text(
                      'Designation: ${employee.designation}',
                      style: const TextStyle(color: Colors.grey),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      'Department: ${employee.department}',
                      style: const TextStyle(color: Colors.grey),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      'Salary: \$${employee.salary}',
                      style: const TextStyle(
                          color: Colors.green, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 4),
                    Text(
                      'Date of Joining: ${employee.dateOfJoining}',
                      style: const TextStyle(color: Colors.grey),
                    ),
                    const SizedBox(height: 10),
                  ],
                ),
                SizedBox(
                  height: 200, // You can adjust the height as needed
                  child: ReusablePieChart(
                    dataMap: dataMap,
                    colorList: colorList,
                    centerText: "Salary Breakdown",
                    chartRadius: 100,
                    legendLabels: {
                      "Basic Salary": "Basic Salary legend",
                      "Bonus": "Bonus legend",
                      "Deductions": "Deductions legend",
                    },
                  ),
                ),
              ],
            ),
            const SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                IconButton(
                  onPressed: () {
                    Provider.of<EmployeeProvider>(context, listen: false)
                        .downloadReceipt(employee.employeeId!);
                  },
                  icon: const Icon(Icons.receipt),
                ),
                IconButton(
                  icon: const Icon(Icons.edit, color: Colors.blue),
                  onPressed: () => navigateToUpdateEmployeeScreen(employee),
                ),
                IconButton(
                  icon: const Icon(Icons.delete, color: Colors.red),
                  onPressed: () {
                    showDialog(
                      context: context,
                      builder: (BuildContext context) {
                        return AlertDialog(
                          title: const Text('Confirm Delete'),
                          content: const Text(
                              'Are you sure you want to delete this employee?'),
                          actions: [
                            TextButton(
                              onPressed: () {
                                Navigator.pop(context); // Close dialog
                              },
                              child: const Text('Cancel'),
                            ),
                            TextButton(
                              onPressed: () {
                                deleteEmployee(employee.employeeId!);
                                Navigator.pop(context); // Close dialog
                              },
                              child: const Text('Delete'),
                            ),
                          ],
                        );
                      },
                    );
                  },
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
