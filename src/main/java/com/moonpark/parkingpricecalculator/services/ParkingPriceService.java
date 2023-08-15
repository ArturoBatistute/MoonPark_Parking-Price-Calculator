package com.moonpark.parkingpricecalculator.services;

import com.moonpark.parkingpricecalculator.exceptions.MoonParkException;
import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import com.moonpark.parkingpricecalculator.model.enums.ParkingZoneEnum;
import com.moonpark.parkingpricecalculator.services.strategies.M1ZonePriceStrategy;
import com.moonpark.parkingpricecalculator.services.strategies.M2ZonePriceStrategy;
import com.moonpark.parkingpricecalculator.services.strategies.M3ZonePriceStrategy;
import com.moonpark.parkingpricecalculator.utils.DateTimeUtils;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ParkingPriceService {

    @Autowired
    private ParkingPriceContext parkingPriceContext;

    /**
     * Calculate parking price based on parking zone and arrival / departure date time.
     * @param parkingZone the zone that will be applied the tax
     * @param arrivalDateTimeString initial time date of charging
     * @param departureDateTimeString end time date of charging
     * @return the details of the amount to pay
     */
    public PriceDetailsRecord calculatePrice(String parkingZone, String arrivalDateTimeString, String departureDateTimeString){

        LocalDateTime arrivalDateTime = DateTimeUtils.formatParkingDateTime(arrivalDateTimeString);
        LocalDateTime departureDateTime = DateTimeUtils.formatParkingDateTime(departureDateTimeString);

        DateTimeUtils.validateDateTime(arrivalDateTime, departureDateTime);
        ParkingZoneEnum parkingZoneEnum = getParkingZone(parkingZone);

        if (parkingZoneEnum.equals(ParkingZoneEnum.M1))
            parkingPriceContext.setStrategy(new M1ZonePriceStrategy());

        if (parkingZoneEnum.equals(ParkingZoneEnum.M2))
            parkingPriceContext.setStrategy(new M2ZonePriceStrategy());

        if (parkingZoneEnum.equals(ParkingZoneEnum.M3))
            parkingPriceContext.setStrategy(new M3ZonePriceStrategy());

        return parkingPriceContext.executeStrategy(arrivalDateTime, departureDateTime);
    }

    private ParkingZoneEnum getParkingZone(String parkingZone){

        ParkingZoneEnum parkingZoneEnum;

        try{
            parkingZoneEnum = ParkingZoneEnum.valueOf(parkingZone);
        }catch (Exception e){
            throw new MoonParkException("Parking zone not found.", HttpStatus.NOT_FOUND);
        }

        return parkingZoneEnum;
    }
}
