package com.moonpark.parkingpricecalculator.services.strategies;

import static com.moonpark.parkingpricecalculator.utils.PriceUtils.DECIMAL_FORMAT;

import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import com.moonpark.parkingpricecalculator.services.ParkingPriceStrategy;
import com.moonpark.parkingpricecalculator.utils.DateTimeUtils;
import com.moonpark.parkingpricecalculator.utils.PriceUtils;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class M2ZonePriceStrategy implements ParkingPriceStrategy {

    private static final float NOK_PER_HOUR_WEEKDAYS = 100;
    private static final float NOK_PER_HOUR_WEEKENDS = 200;
    private static final float MINUTES_IN_HOUR = 60;

    /**
     * Calculate parking price for the M2 zone based on arrival / departure date time.
     * @param arrivalDateTime initial time date of charging
     * @param departureDateTime end time date of charging
     * @return the details of the amount to pay
     */
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
