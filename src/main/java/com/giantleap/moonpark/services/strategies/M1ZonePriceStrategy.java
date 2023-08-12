package com.giantleap.moonpark.services.strategies;

import com.giantleap.moonpark.services.ParkingPriceStrategy;
import java.time.LocalDateTime;

public class M1ZonePriceStrategy implements ParkingPriceStrategy {

    @Override
    public String calculatePrice(LocalDateTime localDateTime) {


        return "hello";
    }
}
