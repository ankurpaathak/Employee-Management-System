package com.example.employeManagementSystem.attendance.web;

import com.example.employeManagementSystem.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.Month;
import java.util.List;


@RestController
@RequestMapping(path = "/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping(value = "/mark")
    public String attendance(@RequestBody AttendanceRequestDto attendanceRequestDto){
        return attendanceService.markAttendance(attendanceRequestDto);
    }

//    @GetMapping(value = "/employee/{id}")
//    public List<AttendanceResponseDto> getAttendance(@PathVariable Integer id){
//        return attendanceService.getAttendance(id);
//    }

    @PutMapping(value = "/update")
    public String attendanceUpdate(@RequestBody AttendanceUpdateRequestDto attendanceUpdateRequestDto){
        return attendanceService.updateAttendance(attendanceUpdateRequestDto);
    }

    @GetMapping(value = "/{id}")
    public AttendanceResponseDto getAttendanceById(@PathVariable Integer id){
        return attendanceService.getAttendanceByAttendanceId(id);
    }

    @GetMapping(value = "/employee")
    public List<AttendanceResponseDto> getAttendanceByMonth(Integer empId, @NotNull Month month, @NotNull Integer year){
        return attendanceService.getAttendanceByMonth(empId,month,year);
    }

}



