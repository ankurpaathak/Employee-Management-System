package com.example.employeeManagementSystem.employee.web;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EmployeeRequestDto {

    @NotNull
    @Size(min = 3, max = 30, message = "Name should be greater than 3 or less than 30")
    private String empName;
    private String role;
    private Double wagesPerHour;


}
