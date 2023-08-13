package com.giantleap.moonpark.services;

import java.time.LocalDateTime;

public interface ParkingPriceStrategy {

    String calculatePrice(LocalDateTime arrivalDateTime, LocalDateTime departureDateTime);

}
