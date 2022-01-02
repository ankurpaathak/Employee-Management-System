package com.example.employeManagementSystem.common.util;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class Utility {

    public static Month getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Month.of(calendar.get(Calendar.MONTH) + 1);
    }

    public static Integer getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static String checkUpdate(String oldValue, String newValue){
        if (newValue != null && !newValue.trim().equals(""))
            return newValue;
        else
            return oldValue;
    }

    public static Double checkUpdate(Double oldValue, Double newValue){
        if (newValue != null)
            return newValue;
        else
            return oldValue;
    }
    public static Integer checkUpdate(Integer oldValue, Integer newValue){
        if(newValue != null)
            return newValue;
        else
            return oldValue;
    }
    public static Date checkUpdate(Date oldValue, Date newValue){
        if(newValue != null)
            return newValue;
        else
            return oldValue;
    }

    public static boolean checkCreate(String nameValue, String roleValue, Double wagesValue){
        if (nameValue != null && !nameValue.trim().equals("") && roleValue != null && !roleValue.trim().equals("") &&
                wagesValue != null)
            return true;
        else
            return false;
    }
}
