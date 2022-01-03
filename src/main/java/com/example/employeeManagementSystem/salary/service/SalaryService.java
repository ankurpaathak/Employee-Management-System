package com.example.employeeManagementSystem.salary.service;

import com.example.employeeManagementSystem.attendance.service.AttendanceService;
import com.example.employeeManagementSystem.employee.data.EmployeeEntity;
import com.example.employeeManagementSystem.employee.service.EmployeeService;
import com.example.employeeManagementSystem.expense.service.ExpenseService;
import com.example.employeeManagementSystem.salary.data.SalaryEntity;
import com.example.employeeManagementSystem.salary.data.SalaryRepository;
import com.example.employeeManagementSystem.salary.web.SalaryResponseDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Month;

@Service
public class SalaryService {

    private final EmployeeService employeeService;
    private final SalaryRepository salaryRepository;
    private final AttendanceService attendanceService;
    private final ExpenseService expenseService;

    public SalaryService(EmployeeService employeeService, SalaryRepository salaryRepository, @Lazy AttendanceService attendanceService, @Lazy ExpenseService expenseService) {
        this.employeeService = employeeService;
        this.salaryRepository = salaryRepository;
        this.attendanceService = attendanceService;
        this.expenseService = expenseService;
    }

    public SalaryResponseDto getPayment(Integer empId, Month month, Integer year){
        EmployeeEntity employeeEntity = employeeService.getEmployeeById(empId);
        SalaryEntity salaryEntity = salaryRepository.getSalaryEntityByEmployeeMonthAndYear(employeeEntity,month,year);
        SalaryResponseDto salaryResponseDto = convertEntityToDto(salaryEntity);
        salaryResponseDto.setTotalWorkingHour(attendanceService.getTotalWorkingHourBySalaryId(salaryEntity));
        salaryResponseDto.setTotalExpense(expenseService.getTotalExpenseBySalaryId(salaryEntity));
        salaryResponseDto.setGrossSalary(salaryResponseDto.getTotalWorkingHour()*salaryEntity.getWagesPerHour());
        salaryResponseDto.setNetSalary(salaryResponseDto.getGrossSalary()-salaryResponseDto.getTotalExpense());
        return salaryResponseDto;
    }

    private SalaryResponseDto convertEntityToDto(SalaryEntity salaryEntity){
        SalaryResponseDto salaryResponseDto = new SalaryResponseDto();
        salaryResponseDto.setSalaryId(salaryEntity.getSalaryId());
        salaryResponseDto.setMonth(salaryEntity.getMonth());
        salaryResponseDto.setRemark(salaryEntity.getRemark());
        salaryResponseDto.setWagesPerHour(salaryEntity.getWagesPerHour());
        return salaryResponseDto;
    }

    public SalaryEntity getSalaryById(Integer id){
        return salaryRepository.findById(id).get();
    }

    private SalaryEntity createSalary(EmployeeEntity employee, Month month, Integer year){
        SalaryEntity salaryEntity = new SalaryEntity();
        salaryEntity.setEmployeeEntity(employee);
        salaryEntity.setMonth(month);
        salaryEntity.setYear(year);
        salaryEntity.setWagesPerHour(employee.getWagesPerHour());
        return salaryRepository.save(salaryEntity);
    }

    public SalaryEntity getSalaryByEmployeeMonthYearElseCreate(Integer empId, Month month, Integer year){
        EmployeeEntity employeeEntity = employeeService.getEmployeeById(empId);
        SalaryEntity salaryEntity = salaryRepository.getSalaryEntityByEmployeeMonthAndYear(employeeEntity, month, year);
        if(salaryEntity == null){
            return createSalary(employeeEntity, month, year);
        }
        else{
            return salaryEntity;
        }
    }

    public SalaryEntity getSalaryByEmployeeMonthYear(Integer empId, Month month, Integer year){
        EmployeeEntity employeeEntity = employeeService.getEmployeeById(empId);
        SalaryEntity salaryEntity = salaryRepository.getSalaryEntityByEmployeeMonthAndYear(employeeEntity, month, year);
        return salaryEntity;
    }

    public SalaryEntity getEmployeeById(Integer empId){
        return salaryRepository.findById(empId).get();
    }

}
