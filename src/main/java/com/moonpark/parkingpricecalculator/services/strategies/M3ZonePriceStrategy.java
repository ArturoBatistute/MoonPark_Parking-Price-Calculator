package com.moonpark.parkingpricecalculator.services.strategies;

import static com.moonpark.parkingpricecalculator.utils.DateTimeUtils.isDateTimeBetween;
import static com.moonpark.parkingpricecalculator.utils.PriceUtils.DECIMAL_FORMAT;

import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import com.moonpark.parkingpricecalculator.services.ParkingPriceStrategy;
import com.moonpark.parkingpricecalculator.utils.DateTimeUtils;
import com.moonpark.parkingpricecalculator.utils.PriceUtils;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class M3ZonePriceStrategy implements ParkingPriceStrategy {

    private static final float NOK_PER_MINUTES_MONDAY_TO_SATURDAY_8_TO_16 = 2;
    private static final float NOK_PER_MINUTES_MONDAY_TO_SATURDAY_OTHER_TIMES = 3;
    private static final int STARTING_HOUR = 8;
    private static final int ENDING_HOUR = 16;
    private static final float SECONDS_IN_HOUR = 3600;
    private static final String ON_PERIOD = "OnPeriod";
    private static final String AFTER_PERIOD = "AfterPeriod";

    /**
     * Calculate parking price for the M3 zone based on arrival / departure date time.
     * @param arrivalDateTime initial time date of charging
     * @param departureDateTime end time date of charging
     * @return the details of the amount to pay
     */
    @Override
    public PriceDetailsRecord calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {

        DayOfWeek arrivalWeekDay = arrivalDateTime.getDayOfWeek();
        DayOfWeek departureWeekDay = departureDateTime.getDayOfWeek();
        float amountToPay = 0;

        if(arrivalWeekDay.getValue() >= DayOfWeek.MONDAY.getValue() && departureWeekDay.getValue() <= DayOfWeek.SATURDAY.getValue()){

            Map<String, Float> parkingSeconds = getParkingSeconds(arrivalDateTime, departureDateTime);

            if(parkingSeconds.containsKey(ON_PERIOD))
                amountToPay = calculateAmount(NOK_PER_MINUTES_MONDAY_TO_SATURDAY_8_TO_16, parkingSeconds.get(ON_PERIOD));

            if(parkingSeconds.containsKey(AFTER_PERIOD))
                amountToPay += calculateAmount(NOK_PER_MINUTES_MONDAY_TO_SATURDAY_OTHER_TIMES, parkingSeconds.get(AFTER_PERIOD));
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

    private static float applyFreeHourDiscount(float parkingSeconds) {

        if(parkingSeconds <= SECONDS_IN_HOUR && parkingSeconds >= 0){
            parkingSeconds = 0;
        }else {
            parkingSeconds -= SECONDS_IN_HOUR;
        }
        return parkingSeconds;
    }

    private Map<String, Float> getParkingSeconds(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime){

        Map<String, Float> response = new HashMap<>();

        LocalDateTime periodStart = arrivalDateTime.withHour(STARTING_HOUR).withMinute(0).withSecond(0);
        LocalDateTime periodStop = arrivalDateTime.withHour(ENDING_HOUR).withMinute(0).withSecond(0);
        float secondsBetween = ChronoUnit.SECONDS.between(arrivalDateTime, departureDateTime);

        //nok 2
        if(isDateTimeBetween(arrivalDateTime, periodStart, periodStop) && departureDateTime.isBefore(periodStop) || departureDateTime.isEqual(periodStop) || arrivalDateTime.isEqual(periodStart)){

            float secondsWithDiscount = applyFreeHourDiscount(secondsBetween);
            response.put(ON_PERIOD, secondsWithDiscount);
        }

        //nok 3
        if(arrivalDateTime.isAfter(periodStop) || arrivalDateTime.isEqual(periodStop) && departureDateTime.isAfter(periodStop)){

            response.put(AFTER_PERIOD, secondsBetween);
        }

        //nok 2 and 3
        if(isDateTimeBetween(arrivalDateTime, periodStart, periodStop) && departureDateTime.isAfter(periodStop)){

            float secondsBetweenOnPeriod = ChronoUnit.SECONDS.between(arrivalDateTime, periodStop);
            float secondsBetweenAfterPeriod = ChronoUnit.SECONDS.between(periodStop, departureDateTime);

            float secondsWithDiscount = applyFreeHourDiscount(secondsBetweenOnPeriod);

            if(secondsWithDiscount > 0)
                response.put(ON_PERIOD, secondsWithDiscount);
            response.put(AFTER_PERIOD, secondsBetweenAfterPeriod);
        }

        return response;
    }
}
