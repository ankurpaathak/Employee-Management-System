package com.example.employeeManagementSystem.expense.web;

import lombok.Data;

import java.util.Date;

@Data
public class ExpenseResponseDto {
    private Integer expenseId;
    private Date date;
    private String description;
    private Double amount;
}
