package com.giantleap.moonpark.services.strategies;

import static com.giantleap.moonpark.utils.PriceUtils.DECIMAL_FORMAT;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import com.giantleap.moonpark.services.ParkingPriceStrategy;
import com.giantleap.moonpark.utils.DateTimeUtils;
import com.giantleap.moonpark.utils.PriceUtils;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class M3ZonePriceStrategy implements ParkingPriceStrategy {

    private static final float NOK_PER_MINUTES_MONDAY_TO_SATURDAY_8_TO_16 = 2;
    private static final float NOK_PER_MINUTES_MONDAY_TO_SATURDAY_OTHER_TIMES = 3;
    private static final float STARTING_HOUR = 8;
    private static final float ENDING_HOUR = 16;
    private static final float SECONDS_IN_HOUR = 3600;

    @Override
    public PriceDetailsRecord calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {

        DayOfWeek arrivalWeekDay = arrivalDateTime.getDayOfWeek();
        DayOfWeek departureWeekDay = departureDateTime.getDayOfWeek();
        float parkingSeconds = ChronoUnit.SECONDS.between(arrivalDateTime, departureDateTime);
        float amountToPay;

        if(arrivalWeekDay.getValue() >= DayOfWeek.MONDAY.getValue() && departureWeekDay.getValue() <= DayOfWeek.SATURDAY.getValue()){

            if(isHourBetween(arrivalDateTime, departureDateTime)){

                parkingSeconds = applyFreeHourDiscount(parkingSeconds);
                amountToPay = calculateAmount(NOK_PER_MINUTES_MONDAY_TO_SATURDAY_8_TO_16, parkingSeconds);
            }else {
                amountToPay = calculateAmount(NOK_PER_MINUTES_MONDAY_TO_SATURDAY_OTHER_TIMES, parkingSeconds);
            }
        }
        else {
            amountToPay = 0;
        }

        return new PriceDetailsRecord(DateTimeUtils.formatDateTimeString(arrivalDateTime.toString()),
            DateTimeUtils.formatDateTimeString(departureDateTime.toString()),
            String.valueOf(amountToPay),
            PriceUtils.NORWAY_CURRENCY_NAME);
    }

    private float calculateAmount(float nokPerMinute, float parkingSeconds) {

        if(parkingSeconds <= 0){
            return 0;
        }

        float amount = nokPerMinute * (parkingSeconds / 60);

        return Float.parseFloat(DECIMAL_FORMAT.format(amount));
    }

    private boolean isHourBetween(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime){

        int arrivalHour = arrivalDateTime.getHour();
        int departureHour = departureDateTime.getHour();

        if(arrivalHour >= STARTING_HOUR && arrivalHour <= ENDING_HOUR && departureHour >= STARTING_HOUR && departureHour <= ENDING_HOUR){
            if(departureHour == ENDING_HOUR && departureDateTime.getMinute() != 0){
                return false;
            }
            return true;
        }
        return false;
    }

    private static float applyFreeHourDiscount(float parkingSeconds) {

        if(parkingSeconds <= SECONDS_IN_HOUR && parkingSeconds >= 0){
            parkingSeconds = 0;
        }else {
            parkingSeconds -= SECONDS_IN_HOUR;
        }
        return parkingSeconds;
    }
}
