package com.marvel.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public interface DateServiceHelper {

    ZoneOffset MOSCOW_ZONE = ZoneOffset.ofHours(3);

    //format 'dd.MM.yyyy HH:mm:ss'
    default Long parseStringDateFormatToLong(String dateTime) {

        return LocalDateTime
                .parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                .toInstant(MOSCOW_ZONE).getEpochSecond();
    }

    //since 1970
    default String parseLongDateFormatToString(Long dateTime) {

        return Instant
                .ofEpochMilli(dateTime)
                .atOffset(MOSCOW_ZONE)
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

    }

}
