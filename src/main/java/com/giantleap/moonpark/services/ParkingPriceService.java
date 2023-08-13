package com.giantleap.moonpark.services;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import com.giantleap.moonpark.model.enums.ParkingZoneEnum;
import com.giantleap.moonpark.services.strategies.M1ZonePriceStrategy;
import com.giantleap.moonpark.services.strategies.M2ZonePriceStrategy;
import com.giantleap.moonpark.utils.DateTimeUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingPriceService {

    @Autowired
    private ParkingPriceContext parkingPriceContext;

    public PriceDetailsRecord calculatePrice(String parkingZone, String arrivalDateTimeString, String departureDateTimeString){

        LocalDateTime arrivalDateTime = DateTimeUtils.formatParkingDateTime(arrivalDateTimeString);
        LocalDateTime departureDateTime = DateTimeUtils.formatParkingDateTime(departureDateTimeString);
        ParkingZoneEnum parkingZoneEnum;

        try{
            parkingZoneEnum = ParkingZoneEnum.valueOf(parkingZone);
        }catch (Exception e){
            throw new RuntimeException("Parking zone not found.");
        }

        if (parkingZoneEnum.equals(ParkingZoneEnum.M1))
            parkingPriceContext.setStrategy(new M1ZonePriceStrategy());

        if (parkingZoneEnum.equals(ParkingZoneEnum.M2))
            parkingPriceContext.setStrategy(new M2ZonePriceStrategy());

        return parkingPriceContext.executeStrategy(arrivalDateTime, departureDateTime);
    }
}
