package com.example.employeManagementSystem.attendance.data;

import com.example.employeManagementSystem.salary.data.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository <AttendanceEntity, Integer> {

    public List<AttendanceEntity> findAllByDateBetween(Date start, Date end);

    @Query(value = "select SUM(e.workingHour) from attendance e where e.salaryEntity = ?1")
    public Integer getTotalWorkingHourBySalaryId(SalaryEntity salaryEntity);

}
