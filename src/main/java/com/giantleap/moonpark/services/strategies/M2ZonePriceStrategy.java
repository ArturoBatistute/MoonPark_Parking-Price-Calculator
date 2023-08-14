package com.giantleap.moonpark.services.strategies;

import static com.giantleap.moonpark.utils.PriceUtils.DECIMAL_FORMAT;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import com.giantleap.moonpark.services.ParkingPriceStrategy;
import com.giantleap.moonpark.utils.DateTimeUtils;
import com.giantleap.moonpark.utils.PriceUtils;
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

        if(arrivalWeekDay.getValue() >= DayOfWeek.SATURDAY.getValue() && departureWeekDay.getValue() <= DayOfWeek.SUNDAY.getValue()){
            amountToPay = calculateAmount(NOK_PER_HOUR_WEEKENDS, parkingSeconds);
        }
        if(arrivalWeekDay.getValue() >= DayOfWeek.MONDAY.getValue() && departureWeekDay.getValue() <= DayOfWeek.FRIDAY.getValue()){
            amountToPay = calculateAmount(NOK_PER_HOUR_WEEKDAYS, parkingSeconds);
        }

        return new PriceDetailsRecord(DateTimeUtils.formatDateTimeString(arrivalDateTime.toString()),
            DateTimeUtils.formatDateTimeString(departureDateTime.toString()),
            String.valueOf(Float.parseFloat(DECIMAL_FORMAT.format(amountToPay))),
            PriceUtils.NORWAY_CURRENCY_NAME);
    }

    private float calculateAmount(float nokPerHour, float parkingSeconds) {

        float nokPerMinute = Float.parseFloat(DECIMAL_FORMAT.format((nokPerHour / MINUTES_IN_HOUR)));
        float amount =  nokPerMinute * (parkingSeconds / 60);

        return Float.parseFloat(DECIMAL_FORMAT.format(amount));
    }
}
