package com.marvel.services;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateServiceHelper {

    default LocalDateTime parseStringDateFormatToLong(String dateTime) {

        return LocalDateTime
                .parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    default String parseLongDateFormatToString(LocalDateTime dateTime) {

        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

    }

}
