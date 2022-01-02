package com.example.employeManagementSystem.attendance.service;

import com.example.employeManagementSystem.attendance.data.AttendanceEntity;
import com.example.employeManagementSystem.attendance.data.AttendanceRepository;
import com.example.employeManagementSystem.attendance.web.AttendanceRequestDto;
import com.example.employeManagementSystem.attendance.web.AttendanceResponseDto;
import com.example.employeManagementSystem.attendance.web.AttendanceUpdateRequestDto;
import com.example.employeManagementSystem.common.util.Utility;
import com.example.employeManagementSystem.salary.data.SalaryEntity;
import com.example.employeManagementSystem.salary.service.SalaryService;
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

//    public List<AttendanceResponseDto> getAttendance(Integer empId) {
//        SalaryEntity salaryEntity = salaryService.getEmployeeById(empId);
//        List<AttendanceEntity> attendanceEntityList = salaryEntity.getAttendanceEntityList();
//        List<AttendanceResponseDto> attendanceResponseDtoList = new ArrayList<>();
//        attendanceEntityList.forEach(attendanceEntity -> {
//            attendanceResponseDtoList.add(convertEntityToDto(attendanceEntity));
//
//        });
//        return attendanceResponseDtoList;
//    }

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
