package com.example.employeeManagementSystem.employee.data;

import com.example.employeeManagementSystem.salary.data.SalaryEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Integer empId;
    private String empName;
    private String role;
    private Double wagesPerHour;

    @OneToMany(mappedBy = "employeeEntity", cascade = CascadeType.ALL)
    private List<SalaryEntity> salaryEntityList = new ArrayList<>();



}
