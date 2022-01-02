package com.example.employeManagementSystem.salary.data;

import com.example.employeManagementSystem.attendance.data.AttendanceEntity;
import com.example.employeManagementSystem.common.enums.PaymentStatus;
import com.example.employeManagementSystem.employee.data.EmployeeEntity;
import com.example.employeManagementSystem.expense.data.ExpenseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "salary")
public class SalaryEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private EmployeeEntity employeeEntity;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    private Integer salaryId;
    @Enumerated(EnumType.STRING)
    private Month month;
    private Integer year;
    private Double wagesPerHour;
    private String remark;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.INITIATED;

    @OneToMany(mappedBy = "salaryEntity", cascade = CascadeType.ALL)
    private List<ExpenseEntity> expenseEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "salaryEntity", cascade = CascadeType.ALL)
    private List<AttendanceEntity> attendanceEntityList = new ArrayList<>();

}
