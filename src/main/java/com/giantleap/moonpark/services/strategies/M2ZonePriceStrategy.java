package com.giantleap.moonpark.services.strategies;

import static com.giantleap.moonpark.utils.PriceUtils.DECIMAL_FORMAT;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import com.giantleap.moonpark.services.ParkingPriceStrategy;
import com.giantleap.moonpark.utils.DateTimeUtils;
import com.giantleap.moonpark.utils.PriceUtils;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class M2ZonePriceStrategy implements ParkingPriceStrategy {

    private static final float NOK_PER_HOUR_WEEKDAYS = 100;
    private static final float NOK_PER_HOUR_WEEKENDS = 200;
    private static final float MINUTES_IN_HOUR = 60;

    @Override
    public PriceDetailsRecord calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {

        DayOfWeek arrivalWeekDay = arrivalDateTime.getDayOfWeek();
        DayOfWeek departureWeekDay = departureDateTime.getDayOfWeek();
        float parkingSeconds = ChronoUnit.SECONDS.between(arrivalDateTime, departureDateTime);
        float amountToPay = 0;

        if(arrivalWeekDay.getValue() >= 6 && departureWeekDay.getValue() <= 7){
            amountToPay = calculateAmount(NOK_PER_HOUR_WEEKENDS, parkingSeconds);
        }
        if(arrivalWeekDay.getValue() >= 1 && departureWeekDay.getValue() <= 5){
            amountToPay = calculateAmount(NOK_PER_HOUR_WEEKDAYS, parkingSeconds);
        }
//        else {
//            amountToPay = calculateBasedMixedWeekdaysAndWeekends(arrivalDateTime, departureDateTime);
//        }

        return new PriceDetailsRecord(DateTimeUtils.formatDateTimeString(arrivalDateTime.toString()),
            DateTimeUtils.formatDateTimeString(departureDateTime.toString()),
            String.valueOf(amountToPay),
            PriceUtils.NORWAY_CURRENCY_NAME);
    }

    private float calculateAmount(float nokPerHour, float parkingSeconds) {

        float amount = (nokPerHour / MINUTES_IN_HOUR) * (parkingSeconds / 60);

        return Float.parseFloat(DECIMAL_FORMAT.format(amount));
    }

//    private float calculateBasedMixedWeekdaysAndWeekends(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime){
//
//        DayOfWeek arrivalWeekDay = arrivalDateTime.getDayOfWeek();
//        DayOfWeek departureWeekDay = departureDateTime.getDayOfWeek();
//
//
//        LocalDateTime endOfSundayDateTime = LocalDateTime.of(2023,1,7,23,59,59);
//
//
//        float weekendsParkingSeconds = ChronoUnit.SECONDS.between(endOfSundayDateTime, endOfSundayDateTime);
//
//        float weekdaysAmount = calculateAmount(NOK_PER_HOUR_WEEKDAYS, weekdaysParkingSeconds);
//        float weekendsAmount = calculateAmount(NOK_PER_HOUR_WEEKENDS, weekendsParkingSeconds);
//
//        return Float.parseFloat(decimalFormat.format(weekdaysAmount + weekendsAmount));
//    }

//    private float check(LocalDateTime dateTime){
//
//        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
//
//        if(dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <= 5){
//
//            LocalDateTime endOfFridayDateTime = LocalDateTime.of(dateTime.getYear(),dateTime.getMonth(),dateTime.getDayOfMonth(),23,59,59);
//
//            float weekdaysParkingSeconds = ChronoUnit.SECONDS.between(dateTime, endOfFridayDateTime);
//        }else {
//
//        }
}
