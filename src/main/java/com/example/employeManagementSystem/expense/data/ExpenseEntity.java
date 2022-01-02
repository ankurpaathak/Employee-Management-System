package com.example.employeManagementSystem.expense.data;

import com.example.employeManagementSystem.salary.data.SalaryEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "expense")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expenseId;
    private String description;
    private Date date;
    private Double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_id")
    private SalaryEntity salaryEntity;


}
