package com.giantleap.moonpark.services;

import com.giantleap.moonpark.model.enums.ParkingZoneEnum;
import com.giantleap.moonpark.services.strategies.M1ZonePriceStrategy;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingPriceService {

    @Autowired
    private ParkingPriceContext parkingPriceContext;

    public String calculatePrice(LocalDateTime localDateTime, ParkingZoneEnum parkingZoneEnum){

        if (parkingZoneEnum.equals(ParkingZoneEnum.M1))
            parkingPriceContext.setStrategy(new M1ZonePriceStrategy());

        return parkingPriceContext.executeStrategy(localDateTime);
    }
}
