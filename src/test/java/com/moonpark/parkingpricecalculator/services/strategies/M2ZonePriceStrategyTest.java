package com.moonpark.parkingpricecalculator.services.strategies;

import static org.junit.jupiter.api.Assertions.*;

import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import com.moonpark.parkingpricecalculator.utils.DateTimeUtils;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class M2ZonePriceStrategyTest {

    @InjectMocks
    private M2ZonePriceStrategy m2ZonePriceStrategy;

    @Test
    void calculatePrice_with100NokPerHourOnWeekdays_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,14,12,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,14,12,1,0);

        PriceDetailsRecord priceDetails = m2ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("1.67", priceDetails.price());
    }

    @Test
    void calculatePrice_with100NokPerHourOnWeekdaysDifferentDates_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,15,12,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,15,13,1,0);

        PriceDetailsRecord priceDetails = m2ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("101.87", priceDetails.price());
    }

    @Test
    void calculatePrice_with200NokPerHourOnWeekends_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,13,12,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,13,12,1,0);

        PriceDetailsRecord priceDetails = m2ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("3.33", priceDetails.price());
    }

    @Test
    void calculatePrice_with200NokPerHourOnWeekendsWithDifferentDates_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,13,12,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,13,14,1,0);

        PriceDetailsRecord priceDetails = m2ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("402.93", priceDetails.price());
    }
}