package com.example.employeeManagementSystem.employee.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Query(value = "SELECT * FROM Employee where emp_name like  %?1%",nativeQuery = true)
    List<EmployeeEntity> findByName(String empName);
}

