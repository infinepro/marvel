package com.marvel.services;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateServiceHelper {

    default LocalDateTime parseStringDateFormatToLocalDateTime(String dateTime) {

        return LocalDateTime
                .parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    default String parseLocalDateTimeFormatToString(LocalDateTime dateTime) {

        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }

}
