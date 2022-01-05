package com.example.employeeManagementSystem.expense.web;

import com.example.employeeManagementSystem.expense.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/new")
    public String expense(@RequestBody ExpenseRequestDto expenseRequestDto){
        return expenseService.newExpense(expenseRequestDto);
    }

    @GetMapping(value = "/employee")
    public List<ExpenseResponseDto> getExpenseByMonth(Integer empId, Month month, Integer year){
        return expenseService.getExpenseByMonth(empId,month,year);
    }

    @PutMapping(value = "/update")
    public String updateExpense(@RequestBody ExpenseUpdateRequestDto expenseUpdateRequestDto){
        return expenseService.updateExpense(expenseUpdateRequestDto);
    }

    @GetMapping(value = "/{id}")
    public ExpenseResponseDto getExpenseById(@PathVariable Integer id){
        return expenseService.getExpenseByExpenseId(id);
    }

//    @GetMapping(value="employee/{id}")
//    public List<ExpenseResponseDto> getExpense(@PathVariable Integer id){
//        return expenseService.getExpense(id);
//    }

}
