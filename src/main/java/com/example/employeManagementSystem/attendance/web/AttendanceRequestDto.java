package com.example.employeManagementSystem.attendance.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AttendanceRequestDto {
    private Integer empId;
    private Integer workingHour;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
}
