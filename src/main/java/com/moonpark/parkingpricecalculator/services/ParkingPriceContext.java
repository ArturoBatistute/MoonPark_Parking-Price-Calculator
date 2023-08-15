package com.moonpark.parkingpricecalculator.services;

import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ParkingPriceContext {

    private ParkingPriceStrategy parkingPriceStrategy;

    public void setStrategy(ParkingPriceStrategy parkingPriceStrategy) {
        this.parkingPriceStrategy = parkingPriceStrategy;
    }

    public PriceDetailsRecord executeStrategy(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime) {
        return parkingPriceStrategy.calculatePrice(arrivalDateTime, departureDateTime);
    }
}
