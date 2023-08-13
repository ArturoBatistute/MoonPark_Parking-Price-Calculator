package com.giantleap.moonpark.services.strategies;

import com.giantleap.moonpark.services.ParkingPriceStrategy;
import com.giantleap.moonpark.utils.PriceUtils;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class M1ZonePriceStrategy implements ParkingPriceStrategy {

    private static final Integer NOK_PER_HOUR = 60;
    private static final Integer MINUTES_IN_HOUR = 60;

    @Override
    public String calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {

        long parkingMinutes = ChronoUnit.MINUTES.between(arrivalDateTime, departureDateTime);
        long amountToPay = (NOK_PER_HOUR / MINUTES_IN_HOUR) * parkingMinutes;

        return PriceUtils.setNorwayCurrency(amountToPay);
    }
}
