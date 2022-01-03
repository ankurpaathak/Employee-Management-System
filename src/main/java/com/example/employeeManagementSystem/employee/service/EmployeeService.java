package com.example.employeeManagementSystem.employee.service;

import com.example.employeeManagementSystem.common.util.Utility;
import com.example.employeeManagementSystem.employee.web.EmployeeRequestDto;
import com.example.employeeManagementSystem.employee.web.EmployeeResponseDto;
import com.example.employeeManagementSystem.employee.data.EmployeeRepository;
import com.example.employeeManagementSystem.employee.data.EmployeeEntity;
import com.example.employeeManagementSystem.employee.web.EmployeeUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public String createEmployee(EmployeeRequestDto employeeRequestDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        if (Utility.checkCreate(employeeRequestDto.getEmpName(), employeeRequestDto.getRole(),
                employeeRequestDto.getWagesPerHour())) {
            employeeEntity.setEmpName(employeeRequestDto.getEmpName());
            employeeEntity.setRole(employeeRequestDto.getRole());
            employeeEntity.setWagesPerHour(employeeRequestDto.getWagesPerHour());
            employeeRepository.save(employeeEntity);
            return "Employee Created Successfully";
        }
        return "Please enter mandatory field";
    }

    public List<EmployeeResponseDto> getEmpByName(String empName) {
        List<EmployeeEntity> employeeEntitiesList = employeeRepository.findByName(empName);
        List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();
        employeeEntitiesList.forEach(employeeEntity -> {
            employeeResponseDtoList.add(convertEntityToDto(employeeEntity));
        });
        return employeeResponseDtoList;
    }

    public EmployeeEntity getEmployeeById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    public EmployeeResponseDto getEmployee(Integer id) {
        return convertEntityToDto(getEmployeeById(id));
    }

    private EmployeeResponseDto convertEntityToDto(EmployeeEntity employeeEntity) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setEmpId(employeeEntity.getEmpId());
        employeeResponseDto.setEmpName(employeeEntity.getEmpName());
        employeeResponseDto.setRole(employeeEntity.getRole());
        employeeResponseDto.setWagesPerHour(employeeEntity.getWagesPerHour());
        return employeeResponseDto;

    }

    public List<EmployeeResponseDto> getAllEmployee() {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();
        employeeEntityList.forEach(employeeEntity -> {
            employeeResponseDtoList.add(convertEntityToDto(employeeEntity));
        });
        return employeeResponseDtoList;
    }

    public String updateEmployee(EmployeeUpdateRequestDto employeeUpdateRequestDto) {
        EmployeeEntity employeeEntity = getEmployeeById(employeeUpdateRequestDto.getEmpId());
        employeeEntity.setEmpName(Utility.checkUpdate(employeeEntity.getEmpName(), employeeUpdateRequestDto.getEmpName()));
        employeeEntity.setRole(Utility.checkUpdate(employeeEntity.getRole(), employeeUpdateRequestDto.getRole()));
        employeeEntity.setWagesPerHour(Utility.checkUpdate(employeeEntity.getWagesPerHour(), employeeUpdateRequestDto.getWagesPerHour()));
        employeeRepository.save(employeeEntity);
        return "Successfully Updated";
    }

    public String deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
        return "Successfully deleted";
    }
}
