package com.example.employeManagementSystem.salary.web;

import com.example.employeManagementSystem.common.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class SalaryRequestDto {
    private Integer empId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private Double amount;
    private String remark;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
