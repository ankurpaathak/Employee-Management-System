package com.example.employeeManagementSystem.employee.web;

import com.example.employeeManagementSystem.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(value = "/create")
    public String createNewEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        return employeeService.createEmployee(employeeRequestDto);
    }

    @GetMapping(value = "/{id}")
    public EmployeeResponseDto getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping(value = "/name")
    public List<EmployeeResponseDto> getEmployeeByName(String empName){
        return employeeService.getEmpByName(empName);
    }

    @GetMapping(value = "/getAll")
    public List<EmployeeResponseDto> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @PutMapping(value = "/update")
    public String employeeUpdate(@Valid @RequestBody EmployeeUpdateRequestDto employeeUpdateRequestDto){
        return employeeService.updateEmployee(employeeUpdateRequestDto);
    }

    @DeleteMapping(value = "/delete")
    public String deleteEmployee(Integer id){
        return employeeService.deleteEmployeeById(id);
    }

}
