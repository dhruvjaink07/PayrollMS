CREATE DATABASE payroll_management;

USE payroll_management;

-- Employee Table
CREATE TABLE employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    designation VARCHAR(50),
    salary DECIMAL(10, 2),
    date_of_joining DATE,
    department VARCHAR(50)
);

-- Salary Table
CREATE TABLE salary (
    salary_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    basic_salary DECIMAL(10, 2),
    bonus DECIMAL(10, 2),
    deductions DECIMAL(10, 2),
    total_salary DECIMAL(10, 2),
    payment_date DATE,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

drop table salary;

-- Inserting Employee Records
INSERT INTO employee (first_name, last_name, designation, department, salary, date_of_joining) 
VALUES 
('John', 'Doe', 'Software Engineer', 'IT', 50000.00, '2021-06-15'),
('Jane', 'Smith', 'HR Manager', 'HR', 60000.00, '2019-04-01'),
('Robert', 'Johnson', 'Accountant', 'Finance', 55000.00, '2020-08-10'),
('Emily', 'Davis', 'Project Manager', 'IT', 70000.00, '2018-03-23'),
('Michael', 'Miller', 'System Analyst', 'IT', 65000.00, '2021-02-10'),
('Olivia', 'Wilson', 'Marketing Head', 'Marketing', 75000.00, '2017-11-30');

-- Inserting Salary Records
INSERT INTO salary (employee_id, basic_salary, bonus, deductions, total_salary, payment_date)
VALUES 
(1, 40000.00, 5000.00, 2000.00, 43000.00, '2023-01-31'),
(2, 50000.00, 7000.00, 3000.00, 54000.00, '2023-01-31'),
(3, 45000.00, 4000.00, 1500.00, 47000.00, '2023-01-31'),
(4, 60000.00, 8000.00, 3500.00, 63000.00, '2023-01-31'),
(5, 55000.00, 6000.00, 2500.00, 58000.00, '2023-01-31'),
(6, 65000.00, 9000.00, 4000.00, 69000.00, '2023-01-31');

CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    date DATE NOT NULL,
    check_in_time TIME,
    check_out_time TIME,
    total_hours_worked DECIMAL(5, 2),
    status ENUM('Present', 'Absent', 'On Leave', 'Half-Day') DEFAULT 'Present',
    remarks VARCHAR(255),
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

INSERT INTO attendance (employee_id, date, check_in_time, check_out_time, total_hours_worked, status, remarks)
VALUES 
(1, '2025-01-15', '09:00:00', '17:00:00', 8.0, 'Present', 'Worked full shift'),
(1, '2025-01-16', '09:15:00', '13:00:00', 3.75, 'Half-Day', 'Left early for personal reasons'),
(1, '2025-01-17', NULL, NULL, 0.0, 'Absent', 'No attendance recorded'),
(2, '2025-01-15', '09:00:00', '18:00:00', 9.0, 'Present', 'Worked overtime'),
(2, '2025-01-16', '10:00:00', '17:00:00', 7.0, 'Present', 'Late check-in'),
(3, '2025-01-15', NULL, NULL, 0.0, 'On Leave', 'Approved leave'),
(3, '2025-01-16', '09:30:00', '16:30:00', 7.0, 'Present', 'Worked full day'),
(4, '2025-01-15', '08:45:00', '17:30:00', 8.75, 'Present', 'Regular workday'),
(4, '2025-01-16', NULL, NULL, 0.0, 'Absent', 'No attendance recorded'),
(5, '2025-01-15', '09:10:00', '17:10:00', 8.0, 'Present', 'Slightly late check-in'),
(5, '2025-01-16', '09:05:00', '17:00:00', 7.92, 'Present', 'Regular workday'),
(5, '2025-01-17', NULL, NULL, 0.0, 'On Leave', 'Approved leave');

CREATE TABLE Company (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    contact VARCHAR(50),
    email VARCHAR(100)
);
INSERT INTO Company (ID, Name, Address, Contact, Email)
VALUES (1, 'Tech Solutions Inc.', '1234 Main St, Tech City', '9876543210', 'contact@techsolutions.com');

Select * from attendance;
