package com.moonpark.parkingpricecalculator.services;

import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import java.time.LocalDateTime;

public interface ParkingPriceStrategy {

    PriceDetailsRecord calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime);

}
