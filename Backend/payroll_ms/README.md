# Payroll Management System

This is a Payroll Management System built with Spring Boot. It provides RESTful APIs to manage employees, salaries, attendance, and generate reports.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Getting Started](#getting-started)

## Installation

1. Clone the repository:

    ```sh
    git clone https://github.com/your-repo/payroll_ms.git
    cd payroll_ms
    ```

2. Configure the database in [application.properties](http://_vscodecontentref_/1):

    ```properties
    spring.datasource.url=jdbc:mysql://your-database-url:port/database_name
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```

3. Build the project:

    ```sh
    ./mvnw clean install
    ```

4. Run the application:

    ```sh
    ./mvnw spring-boot:run
    ```

## Usage

### Employee

- `POST /employees` - Add a new employee
- `GET /employees/{id}` - Get an employee by ID
- `GET /employees` - Get all employees
- `PUT /employees/{id}` - Update an employee
- `DELETE /employees/{id}` - Delete an employee

### Salary

- `POST /api/salaries` - Add a new salary
- `GET /api/salaries/employee/{employeeId}` - Get salaries by employee ID
- `GET /api/salaries` - Get all salaries
- `GET /api/salaries/statistics` - Get salary statistics
- `GET /api/salaries/salary/monthly` - Get monthly salary for an employee

### Attendance

- `POST /attendance` - Mark attendance for multiple employees
- `GET /attendance/employee/{employeeId}` - Get attendance by employee ID
- `GET /attendance/date/{date}` - Get attendance by date

### Reports

- `GET /reports/salary/{employeeId}` - Get salary report for an employee

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/example/pms/
│   │       ├── controller/
│   │       │   └── EmployeeController.java
│   │       ├── entity/
│   │       │   └── Employee.java
│   │       ├── repository/
│   │       │   └── EmployeeRepository.java
│   │       ├── service/
│   │       │   └── EmployeeService.java
│   │       ├── utility/
│   │       │   └── DateUtils.java
│   │       └── PayrollMsApplication.java
│   └── resources/
│       ├── static/
│       ├── templates/
│       └── application.properties
└── test/
    └── java/
        └── com/example/pms/
            └── PayrollMsApplicationTests.java
```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## License

This project is licensed under the Apache License 2.0. See the LICENSE file for details.

## Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.1/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.1/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.1/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.1/reference/using/devtools.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.1/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides