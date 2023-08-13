package com.giantleap.moonpark.services;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ParkingPriceContext {

    private ParkingPriceStrategy parkingPriceStrategy;

    public void setStrategy(ParkingPriceStrategy parkingPriceStrategy) {
        this.parkingPriceStrategy = parkingPriceStrategy;
    }

    public String executeStrategy(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {
        return parkingPriceStrategy.calculatePrice(arrivalDateTime, departureDateTime);
    }
}
