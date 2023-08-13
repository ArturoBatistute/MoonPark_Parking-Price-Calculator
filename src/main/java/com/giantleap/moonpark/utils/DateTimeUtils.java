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
}
