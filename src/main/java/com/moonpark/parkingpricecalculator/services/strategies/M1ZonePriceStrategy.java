package com.moonpark.parkingpricecalculator.services.strategies;

import static com.moonpark.parkingpricecalculator.utils.PriceUtils.DECIMAL_FORMAT;

import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import com.moonpark.parkingpricecalculator.services.ParkingPriceStrategy;
import com.moonpark.parkingpricecalculator.utils.DateTimeUtils;
import com.moonpark.parkingpricecalculator.utils.PriceUtils;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class M1ZonePriceStrategy implements ParkingPriceStrategy {

    private static final float NOK_PER_HOUR = 60;
    private static final float MINUTES_IN_HOUR = 60;

    @Override
    public PriceDetailsRecord calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {

        float parkingSeconds = ChronoUnit.SECONDS.between(arrivalDateTime, departureDateTime);
        float nokPerMinute = Float.parseFloat(DECIMAL_FORMAT.format((NOK_PER_HOUR / MINUTES_IN_HOUR)));
        float amountToPay = nokPerMinute * (parkingSeconds / 60);

        return new PriceDetailsRecord(DateTimeUtils.formatDateTimeString(arrivalDateTime.toString()),
            DateTimeUtils.formatDateTimeString(departureDateTime.toString()),
            String.valueOf(Float.parseFloat(DECIMAL_FORMAT.format(amountToPay))),
            PriceUtils.NORWAY_CURRENCY_NAME);
    }
}
