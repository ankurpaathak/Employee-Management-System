package com.example.employeManagementSystem.attendance.data;

import com.example.employeManagementSystem.salary.data.SalaryEntity;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "attendance")
public class AttendanceEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;
    private Date date;
    private Integer workingHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_id")
    private SalaryEntity salaryEntity;

}
