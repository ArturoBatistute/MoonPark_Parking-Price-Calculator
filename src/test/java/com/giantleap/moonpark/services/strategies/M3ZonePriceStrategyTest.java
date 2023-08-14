package com.giantleap.moonpark.services.strategies;

import static org.junit.jupiter.api.Assertions.*;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import com.giantleap.moonpark.utils.DateTimeUtils;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class M3ZonePriceStrategyTest {

    @InjectMocks
    private M3ZonePriceStrategy m3ZonePriceStrategy;

    @Test
    void calculatePrice_with2NokPerMinuteFromMondayToSaturdayBetween8And16_shouldReturnTheRightAmountToPayWith1HourFree() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,14,8,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,14,8,1,0);

        PriceDetailsRecord priceDetails = m3ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("0.0", priceDetails.price());
    }

    @Test
    void calculatePrice_with2NokPerMinuteFromMondayToSaturdayBetween8And16WithDifferentDates_shouldReturnTheRightAmountToPayWith1HourFree() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,14,15,32,40);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,14,15,40,12);

        PriceDetailsRecord priceDetails = m3ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("0.0", priceDetails.price());
    }

    @Test
    void calculatePrice_with2NokPerMinuteFromMondayToSaturdayBetween8And16WithDifferentDates2_shouldReturnTheRightAmountToPayWith1HourFree() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,14,8,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,14,9,1,0);

        PriceDetailsRecord priceDetails = m3ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("2.0", priceDetails.price());
    }

    @Test
    void calculatePrice_with2NokPerMinuteFromMondayToSaturdayBetween8And16WithDifferentDates3_shouldReturnTheRightAmountToPayWith1HourFree() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,14,14,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,14,16,0,0);

        PriceDetailsRecord priceDetails = m3ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("120.0", priceDetails.price());
    }

    @Test
    void calculatePrice_with3NokPerMinuteFromMondayToSaturdayAfter8And16WithDifferentDates_shouldReturnTheRightAmountToPayWithout1HourFree() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,14,16,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,14,16,1,0);

        PriceDetailsRecord priceDetails = m3ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("3.0", priceDetails.price());
    }

    @Test
    void calculatePrice_withOnPeriodTimeAndOffPeriod_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,14,15,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,14,16,1,0);

        PriceDetailsRecord priceDetails = m3ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("3.0", priceDetails.price());
    }

    @Test
    void calculatePrice_withOnPeriodTimeAndOffPeriodWithDifferentDates_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,14,14,59,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,14,16,1,0);

        PriceDetailsRecord priceDetails = m3ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("5.0", priceDetails.price());
    }

    @Test
    void calculatePrice_withSundayDateTime_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,13,15,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,13,16,1,0);

        PriceDetailsRecord priceDetails = m3ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("0.0", priceDetails.price());
    }
}