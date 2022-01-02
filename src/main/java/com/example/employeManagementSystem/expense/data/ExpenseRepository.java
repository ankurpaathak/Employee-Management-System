package com.example.employeManagementSystem.expense.data;

import com.example.employeManagementSystem.salary.data.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {

    @Query(value = "select SUM(e.amount) from expense e where e.salaryEntity = ?1")
    public Integer getTotalExpenseBySalaryId(SalaryEntity salaryEntity);
}
