package com.example.employeeManagementSystem.salary.web;

import com.example.employeeManagementSystem.common.enums.PaymentStatus;
import lombok.Data;

import java.time.Month;

@Data
public class SalaryResponseDto {
    private Integer salaryId;
    private Month month;
    private Double wagesPerHour;
    private Integer totalWorkingHour;
    private Double grossSalary;
    private Integer totalExpense;
    private Double netSalary;
    private String remark;
    private PaymentStatus status = PaymentStatus.INITIATED;








}
