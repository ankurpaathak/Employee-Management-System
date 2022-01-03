package com.example.employeeManagementSystem.expense.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ExpenseRequestDto {
    private Integer empId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private String description;
    private Double amount;
}
