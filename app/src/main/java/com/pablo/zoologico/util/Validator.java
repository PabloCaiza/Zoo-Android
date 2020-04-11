package com.pablo.zoologico.util;

import android.util.Patterns;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {

    public static boolean validateMail(String mail) {
        //el praton del mail
        Pattern patterns = Patterns.EMAIL_ADDRESS;
        //validar que el input
        Matcher matcher = patterns.matcher(mail);
        if (matcher.matches()) return true;


        return false;

    }

    public static boolean validateDate(Date date) {
        Calendar calendarCurrent = Calendar.getInstance();
        Calendar calendarAge = Calendar.getInstance();
        calendarAge.setTime(date);
        int edad = calendarCurrent.get(Calendar.YEAR) - calendarAge.get(Calendar.YEAR);
        if (edad >= 18) return true;

        return false;


    }
}
