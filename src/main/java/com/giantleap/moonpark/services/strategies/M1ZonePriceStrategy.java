package com.giantleap.moonpark.services.strategies;

import static com.giantleap.moonpark.utils.PriceUtils.DECIMAL_FORMAT;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import com.giantleap.moonpark.services.ParkingPriceStrategy;
import com.giantleap.moonpark.utils.DateTimeUtils;
import com.giantleap.moonpark.utils.PriceUtils;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class M1ZonePriceStrategy implements ParkingPriceStrategy {

    private static final float NOK_PER_HOUR = 60;
    private static final float MINUTES_IN_HOUR = 60;

    @Override
    public PriceDetailsRecord calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {

        float parkingSeconds = ChronoUnit.SECONDS.between(arrivalDateTime, departureDateTime);
        float amountToPay = (NOK_PER_HOUR / MINUTES_IN_HOUR) * (parkingSeconds / 60);

        return new PriceDetailsRecord(DateTimeUtils.formatDateTimeString(arrivalDateTime.toString()),
            DateTimeUtils.formatDateTimeString(departureDateTime.toString()),
            String.valueOf(Float.parseFloat(DECIMAL_FORMAT.format(amountToPay))),
            PriceUtils.NORWAY_CURRENCY_NAME);
    }
}
