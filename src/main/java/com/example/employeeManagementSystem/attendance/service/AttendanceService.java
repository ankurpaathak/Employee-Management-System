package com.example.employeeManagementSystem.attendance.service;

import com.example.employeeManagementSystem.attendance.data.AttendanceEntity;
import com.example.employeeManagementSystem.attendance.data.AttendanceRepository;
import com.example.employeeManagementSystem.attendance.web.AttendanceRequestDto;
import com.example.employeeManagementSystem.attendance.web.AttendanceResponseDto;
import com.example.employeeManagementSystem.attendance.web.AttendanceUpdateRequestDto;
import com.example.employeeManagementSystem.common.util.Utility;
import com.example.employeeManagementSystem.salary.data.SalaryEntity;
import com.example.employeeManagementSystem.salary.service.SalaryService;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final SalaryService salaryService;

    public AttendanceService(AttendanceRepository attendanceRepository, SalaryService salaryService) {
        this.attendanceRepository = attendanceRepository;
        this.salaryService = salaryService;
    }

    public String markAttendance(AttendanceRequestDto attendanceRequestDto) {
        Date today = Calendar.getInstance().getTime();
        if(!attendanceRequestDto.getDate().before(today)){
            return "Invalid Date";
        }
        AttendanceEntity attendanceEntity = new AttendanceEntity();
        attendanceEntity.setSalaryEntity(salaryService.getSalaryByEmployeeMonthYearElseCreate(attendanceRequestDto.getEmpId(),
                Utility.getMonth(attendanceRequestDto.getDate()),Utility.getYear(attendanceRequestDto.getDate())));
        attendanceEntity.setDate(attendanceRequestDto.getDate());
        attendanceEntity.setWorkingHour(attendanceRequestDto.getWorkingHour());
        attendanceRepository.save(attendanceEntity);
        return "Attendance Marked Successfully";
    }

    public List<AttendanceResponseDto> getAttendanceByMonth(Integer empId, Month month, Integer year){
        SalaryEntity salaryEntity = salaryService.getSalaryByEmployeeMonthYear(empId, month, year);
        List<AttendanceResponseDto> attendanceResponseDtoList = new ArrayList<>();
        if(salaryEntity == null){
            return attendanceResponseDtoList;
        }
        List<AttendanceEntity> attendanceEntityList = salaryEntity.getAttendanceEntityList();
        attendanceEntityList.forEach(attendanceEntity -> {
            attendanceResponseDtoList.add(convertEntityToDto(attendanceEntity));
        });
        return attendanceResponseDtoList;
    }

    public String updateAttendance(AttendanceUpdateRequestDto attendanceUpdateRequestDto){
        Date today = Calendar.getInstance().getTime();
        if(!attendanceUpdateRequestDto.getDate().before(today)){
            return "Invalid Date";
        }
        AttendanceEntity attendanceEntity = getAttendanceById(attendanceUpdateRequestDto.getAttendanceId());
        attendanceEntity.setSalaryEntity(salaryService.getSalaryByEmployeeMonthYearElseCreate(attendanceUpdateRequestDto.getEmpId(),
                Utility.getMonth(attendanceUpdateRequestDto.getDate()),Utility.getYear(attendanceUpdateRequestDto.getDate())));
        attendanceEntity.setDate(Utility.checkUpdate(attendanceEntity.getDate(), attendanceUpdateRequestDto.getDate()));
        attendanceEntity.setWorkingHour(Utility.checkUpdate(attendanceEntity.getWorkingHour(), attendanceUpdateRequestDto.getWorkingHour()));
        attendanceRepository.save(attendanceEntity);
        return "Attendance Updated Successfully";
    }

    public AttendanceEntity getAttendanceById(Integer id){
        return attendanceRepository.findById(id).get();
    }

    private AttendanceResponseDto convertEntityToDto(AttendanceEntity attendanceEntity){
        AttendanceResponseDto attendanceResponseDto = new AttendanceResponseDto();
        attendanceResponseDto.setAttendanceId(attendanceEntity.getAttendanceId());
        attendanceResponseDto.setDate(attendanceEntity.getDate());
        attendanceResponseDto.setWorkingHour(attendanceEntity.getWorkingHour());
        return attendanceResponseDto;
    }

    public Integer getTotalWorkingHourBySalaryId(SalaryEntity salaryEntity){
        Integer totalHour =  attendanceRepository.getTotalWorkingHourBySalaryId(salaryEntity);
        return totalHour == null ? 0 : totalHour;
    }

    public AttendanceResponseDto getAttendanceByAttendanceId(Integer id) {
        return convertEntityToDto(getAttendanceById(id));
    }
}
