package com.example.employeManagementSystem.expense.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ExpenseUpdateRequestDto {
    private Integer empId;
    private Integer expenseId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private String description;
    private Double amount;
}
