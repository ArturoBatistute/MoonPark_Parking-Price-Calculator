package com.giantleap.moonpark.utils;

import com.giantleap.moonpark.exceptions.MoonParkException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;

public final class DateTimeUtils {

    private DateTimeUtils(){}

    public static LocalDateTime formatParkingDateTime(String parkingDateTime){

        LocalDateTime parkingDateTimeFormatted;

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            parkingDateTimeFormatted = LocalDateTime.parse(parkingDateTime, format);
        }catch (Exception e){
            throw new MoonParkException("Parking date time must follow this pattern: yyyy-MM-dd HH:mm:ss", HttpStatus.BAD_REQUEST);
        }
        return parkingDateTimeFormatted;
    }

    public static String formatDateTimeString(String dateTime){

        return dateTime.replace("T", " ");
    }

    public static void validateDateTime(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime){

        if (arrivalDateTime.isAfter(departureDateTime))
            throw new MoonParkException("Arrival date time must be before and departure date time.", HttpStatus.BAD_REQUEST);
    }

    public static boolean isDateTimeBetween(LocalDateTime source, LocalDateTime startDateTime, LocalDateTime endDateTime){

        return source.isAfter(startDateTime) && source.isBefore(endDateTime);
    }
}
