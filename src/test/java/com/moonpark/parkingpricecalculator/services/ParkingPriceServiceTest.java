package com.moonpark.parkingpricecalculator.services;

import static org.junit.jupiter.api.Assertions.*;

import com.moonpark.parkingpricecalculator.exceptions.MoonParkException;
import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import com.moonpark.parkingpricecalculator.model.enums.ParkingZoneEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingPriceServiceTest {

    @Spy
    private ParkingPriceStrategy parkingPriceStrategy;

    @Spy
    private ParkingPriceContext parkingPriceContext;

    @InjectMocks
    private ParkingPriceService parkingPriceService;

    @Test
    void calculatePrice_withStartDateTimeBeforeEndStartDate_shouldThrowException() {

        String startDateTime = "2023-08-15 14:00:00";
        String endDateTime = "2023-08-13 14:01:00";

        assertThrows(MoonParkException.class, () -> parkingPriceService.calculatePrice(ParkingZoneEnum.M1.name(), startDateTime, endDateTime));
    }

    @Test
    void calculatePrice_withNonexistentParkingZone_shouldThrowException() {

        String startDateTime = "2023-08-13 14:00:00";
        String endDateTime = "2023-08-13 14:01:00";

        assertThrows(MoonParkException.class, () -> parkingPriceService.calculatePrice("M0", startDateTime, endDateTime));
    }

    @Test
    void calculatePrice_withM1Area_shouldReturnAmountToPayInsidePriceDetails() {

        String startDateTime = "2023-08-13 14:00:00";
        String endDateTime = "2023-08-13 14:01:00";

        PriceDetailsRecord priceDetails = parkingPriceService.calculatePrice(ParkingZoneEnum.M1.name(), startDateTime, endDateTime);

        assertEquals("1.0", priceDetails.price());
    }

    @Test
    void calculatePrice_withM2Area_shouldReturnAmountToPayInsidePriceDetails() {

        String startDateTime = "2023-08-14 14:00:00";
        String endDateTime = "2023-08-14 14:01:00";

        PriceDetailsRecord priceDetails = parkingPriceService.calculatePrice(ParkingZoneEnum.M2.name(), startDateTime, endDateTime);

        assertEquals("1.67", priceDetails.price());
    }

    @Test
    void calculatePrice_withM3Area_shouldReturnAmountToPayInsidePriceDetails() {

        String startDateTime = "2023-08-14 08:00:00";
        String endDateTime = "2023-08-14 09:01:00";

        PriceDetailsRecord priceDetails = parkingPriceService.calculatePrice(ParkingZoneEnum.M3.name(), startDateTime, endDateTime);

        assertEquals("2.0", priceDetails.price());
    }


}