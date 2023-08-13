package com.giantleap.moonpark.services;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import java.time.LocalDateTime;

public interface ParkingPriceStrategy {

    PriceDetailsRecord calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime);

}
