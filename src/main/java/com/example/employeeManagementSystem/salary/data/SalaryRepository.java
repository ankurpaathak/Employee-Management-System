package com.example.employeeManagementSystem.salary.data;

import com.example.employeeManagementSystem.employee.data.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, Integer> {

   @Query("select e from salary e where e.employeeEntity = ?1 and e.month = ?2 and e.year = ?3")
   SalaryEntity getSalaryEntityByEmployeeMonthAndYear(EmployeeEntity employee, Month month, Integer year);


}
