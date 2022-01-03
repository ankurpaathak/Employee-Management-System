package com.example.employeeManagementSystem.salary.web;

import com.example.employeeManagementSystem.salary.service.SalaryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.Month;

@RestController
@RequestMapping(path = "/salary")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping(value = "/employee/{id}")
    public SalaryResponseDto getPayment(@PathVariable Integer id, @NotNull Month month, @NotNull Integer year){
        return salaryService.getPayment(id,month,year);
    }

}
