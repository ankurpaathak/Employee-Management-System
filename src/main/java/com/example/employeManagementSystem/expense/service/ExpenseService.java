package com.example.employeManagementSystem.expense.service;

import com.example.employeManagementSystem.common.util.Utility;
import com.example.employeManagementSystem.employee.service.EmployeeService;
import com.example.employeManagementSystem.expense.data.ExpenseEntity;
import com.example.employeManagementSystem.expense.data.ExpenseRepository;
import com.example.employeManagementSystem.expense.web.ExpenseRequestDto;
import com.example.employeManagementSystem.expense.web.ExpenseResponseDto;
import com.example.employeManagementSystem.expense.web.ExpenseUpdateRequestDto;
import com.example.employeManagementSystem.salary.data.SalaryEntity;
import com.example.employeManagementSystem.salary.service.SalaryService;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {

    private final EmployeeService employeeService;
    private final ExpenseRepository expenseRepository;
    private final SalaryService salaryService;


    public ExpenseService(EmployeeService employeeService, ExpenseRepository expenseRepository, SalaryService salaryService) {
        this.employeeService = employeeService;
        this.expenseRepository = expenseRepository;
        this.salaryService = salaryService;
    }

    public String newExpense(ExpenseRequestDto expenseRequestDto){
        Date today = Calendar.getInstance().getTime();
        if(!expenseRequestDto.getDate().before(today)){
            return "Invalid Date";
        }
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setSalaryEntity(salaryService.getSalaryByEmployeeMonthYearElseCreate(expenseRequestDto.getEmpId(),
                Utility.getMonth(expenseRequestDto.getDate()),Utility.getYear(expenseRequestDto.getDate())));
        expenseEntity.setDate(expenseRequestDto.getDate());
        expenseEntity.setDescription(expenseRequestDto.getDescription());
        expenseEntity.setAmount(expenseRequestDto.getAmount());
        expenseRepository.save(expenseEntity);
        return "Expense added Successfully";
    }

    public String updateExpense(ExpenseUpdateRequestDto expenseUpdateRequestDto){
        Date today = Calendar.getInstance().getTime();
        if(!expenseUpdateRequestDto.getDate().before(today)){
            return "Invalid Date";
        }
        ExpenseEntity expenseEntity = getExpenseById(expenseUpdateRequestDto.getExpenseId());
        expenseEntity.setSalaryEntity(salaryService.getSalaryByEmployeeMonthYearElseCreate(expenseUpdateRequestDto.getEmpId(),
                Utility.getMonth(expenseUpdateRequestDto.getDate()), Utility.getYear(expenseUpdateRequestDto.getDate())));
        expenseEntity.setDate(expenseUpdateRequestDto.getDate());
        expenseEntity.setDescription(expenseUpdateRequestDto.getDescription());
        expenseEntity.setAmount(expenseUpdateRequestDto.getAmount());
        expenseRepository.save(expenseEntity);
        return "Expense Updated";
    }

    public ExpenseEntity getExpenseById(Integer id){
        return expenseRepository.findById(id).get();
    }

    public List<ExpenseResponseDto> getExpenseByMonth(Integer empId, Month month, Integer year){
        SalaryEntity salaryEntity = salaryService.getSalaryByEmployeeMonthYear(empId,month,year);
        List<ExpenseResponseDto> expenseResponseDtoList = new ArrayList<>();
        if(salaryEntity == null){
            return expenseResponseDtoList;
        }
        List<ExpenseEntity> expenseEntityList = salaryEntity.getExpenseEntityList();
        expenseEntityList.forEach(expenseEntity -> {
            expenseResponseDtoList.add(convertEntityToDto(expenseEntity));
        });
        return expenseResponseDtoList;
    }

    private ExpenseResponseDto convertEntityToDto(ExpenseEntity expenseEntity){
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setExpenseId(expenseEntity.getExpenseId());
        expenseResponseDto.setDate(expenseEntity.getDate());
        expenseResponseDto.setDescription(expenseEntity.getDescription());
        expenseResponseDto.setAmount(expenseEntity.getAmount());
        return expenseResponseDto;
    }

    public Integer getTotalExpenseBySalaryId(SalaryEntity salaryEntity){
        Integer totalExpense = expenseRepository.getTotalExpenseBySalaryId(salaryEntity);
        return totalExpense == null ? 0 : totalExpense;
    }

//    public List<ExpenseResponseDto> getExpense(Integer empId) {
//        SalaryEntity salaryEntity = salaryService.getEmployeeById(empId);
//        List<ExpenseEntity> expenseEntityList = salaryEntity.getExpenseEntityList();
//        List<ExpenseResponseDto> expenseResponseDtoList = new ArrayList<>();
//        expenseEntityList.forEach(expenseEntity -> {
//            expenseResponseDtoList.add(convertEntityToDto(expenseEntity));
//        });
//        return expenseResponseDtoList;
//    }
}
