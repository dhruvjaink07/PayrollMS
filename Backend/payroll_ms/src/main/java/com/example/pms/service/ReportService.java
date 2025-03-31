package com.example.pms.service;

import com.example.pms.entity.Company;
import com.example.pms.entity.Employee;
import com.example.pms.entity.Salary;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private CompanyService companyService;

    public byte[] generateSalaryReport(Integer employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<Salary> salaries = salaryService.getSalaryByEmployeeId(employeeId);
        Company company = companyService.getCompanyById(1);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Add company header
            Table companyTable = new Table(UnitValue.createPercentArray(new float[]{1, 3}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setBackgroundColor(new DeviceRgb(230, 240, 255))
                    .setMarginBottom(20);

            companyTable.addCell(new Cell().add(new Paragraph("Company:"))
                    .setBorder(Border.NO_BORDER)
                    .setBold());
            companyTable.addCell(new Cell().add(new Paragraph(company.getName()))
                    .setBorder(Border.NO_BORDER));
            companyTable.addCell(new Cell().add(new Paragraph("Address:"))
                    .setBorder(Border.NO_BORDER)
                    .setBold());
            companyTable.addCell(new Cell().add(new Paragraph(company.getAddress()))
                    .setBorder(Border.NO_BORDER));
            companyTable.addCell(new Cell().add(new Paragraph("Contact:"))
                    .setBorder(Border.NO_BORDER)
                    .setBold());
            companyTable.addCell(new Cell().add(new Paragraph(company.getContact()))
                    .setBorder(Border.NO_BORDER));
            companyTable.addCell(new Cell().add(new Paragraph("Email:"))
                    .setBorder(Border.NO_BORDER)
                    .setBold());
            companyTable.addCell(new Cell().add(new Paragraph(company.getEmail()))
                    .setBorder(Border.NO_BORDER));

            document.add(companyTable);

            // Title
            Paragraph title = new Paragraph("Salary Receipt")
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE)
                    .setMarginBottom(20);
            document.add(title);

            // Employee details
            Table employeeTable = new Table(UnitValue.createPercentArray(new float[]{1, 3}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setBackgroundColor(new DeviceRgb(240, 255, 240))
                    .setMarginBottom(20);

            employeeTable.addCell(new Cell().add(new Paragraph("Name:"))
                    .setBold()
                    .setBorder(Border.NO_BORDER));
            employeeTable.addCell(new Cell().add(new Paragraph(employee.getFirstName() + " " + employee.getLastName()))
                    .setBorder(Border.NO_BORDER));
            employeeTable.addCell(new Cell().add(new Paragraph("Department:"))
                    .setBold()
                    .setBorder(Border.NO_BORDER));
            employeeTable.addCell(new Cell().add(new Paragraph(employee.getDepartment()))
                    .setBorder(Border.NO_BORDER));
            employeeTable.addCell(new Cell().add(new Paragraph("Designation:"))
                    .setBold()
                    .setBorder(Border.NO_BORDER));
            employeeTable.addCell(new Cell().add(new Paragraph(employee.getDesignation()))
                    .setBorder(Border.NO_BORDER));

            document.add(employeeTable);

            // Salary Details Table
            Table salaryTable = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2, 2}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10);

            // Table Header
            salaryTable.addCell(new Cell().add(new Paragraph("Payment Date"))
                    .setBackgroundColor(new DeviceRgb(200, 230, 255))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            salaryTable.addCell(new Cell().add(new Paragraph("Basic Salary"))
                    .setBackgroundColor(new DeviceRgb(200, 230, 255))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            salaryTable.addCell(new Cell().add(new Paragraph("Bonus"))
                    .setBackgroundColor(new DeviceRgb(200, 230, 255))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            salaryTable.addCell(new Cell().add(new Paragraph("Deductions"))
                    .setBackgroundColor(new DeviceRgb(200, 230, 255))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            salaryTable.addCell(new Cell().add(new Paragraph("Total Salary"))
                    .setBackgroundColor(new DeviceRgb(200, 230, 255))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());

            // Add Salary Details
            for (Salary salary : salaries) {
                salaryTable.addCell(new Cell().add(new Paragraph(salary.getPaymentDate().toString()))
                        .setTextAlignment(TextAlignment.CENTER));
                salaryTable.addCell(new Cell().add(new Paragraph(salary.getBasicSalary().toString()))
                        .setTextAlignment(TextAlignment.RIGHT));
                salaryTable.addCell(new Cell().add(new Paragraph(salary.getBonus().toString()))
                        .setTextAlignment(TextAlignment.RIGHT));
                salaryTable.addCell(new Cell().add(new Paragraph(salary.getDeductions().toString()))
                        .setTextAlignment(TextAlignment.RIGHT));
                salaryTable.addCell(new Cell().add(new Paragraph(salary.getTotalSalary().toString()))
                        .setTextAlignment(TextAlignment.RIGHT));
            }

            document.add(salaryTable);

            // Footer with Instructions
            Paragraph footer = new Paragraph("Instructions: Please ensure all details are verified. Contact HR for any discrepancies.\n")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMarginTop(30);
            document.add(footer);

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
