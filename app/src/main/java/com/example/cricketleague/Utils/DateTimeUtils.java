package com.example.cricketleague.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class DateTimeUtils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withLocale(Locale.US);


    public static LocalDateTime getDateTimeFromString(String dateTime){
        return LocalDateTime.parse(dateTime, formatter);
    }

}
