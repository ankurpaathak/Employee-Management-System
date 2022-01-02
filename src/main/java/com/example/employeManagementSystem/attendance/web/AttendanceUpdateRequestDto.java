package com.example.employeManagementSystem.attendance.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AttendanceUpdateRequestDto {
    private Integer empId;
    private Integer attendanceId;
    private Integer workingHour;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
}
