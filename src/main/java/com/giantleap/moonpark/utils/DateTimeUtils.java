package com.giantleap.moonpark.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

    private DateTimeUtils(){}

    public static LocalDateTime formatParkingDateTime(String parkingDateTime){

        LocalDateTime parkingDateTimeFormatted;

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            parkingDateTimeFormatted = LocalDateTime.parse(parkingDateTime, format);
        }catch (Exception e){
            throw new RuntimeException("Parking date time must follow this pattern: yyyy-MM-dd HH:mm:ss");
        }
        return parkingDateTimeFormatted;
    }

    public static String formatDateTimeString(String dateTime){

        return dateTime.replace("T", " ");
    }

    public static void validateDateTime(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime){

        if (arrivalDateTime.isAfter(departureDateTime))
            throw new RuntimeException("Arrival date time must be before and departure date time.");
    }

    public static boolean isDateTimeBetween(LocalDateTime source, LocalDateTime startDateTime, LocalDateTime endDateTime){

        return source.isAfter(startDateTime) && source.isBefore(endDateTime);
    }
}
