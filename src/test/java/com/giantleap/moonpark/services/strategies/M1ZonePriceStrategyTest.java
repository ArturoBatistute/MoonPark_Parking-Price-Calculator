package com.giantleap.moonpark.services.strategies;

import static org.junit.jupiter.api.Assertions.*;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import com.giantleap.moonpark.utils.DateTimeUtils;
import java.time.LocalDateTime;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class M1ZonePriceStrategyTest {

    @InjectMocks
    private M1ZonePriceStrategy m1ZonePriceStrategy;

    @Test
    void calculatePrice_withNokPerHourAt60_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,8,12,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,8,12,1,0);

        PriceDetailsRecord priceDetails = m1ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("1.0", priceDetails.price());
    }

    @Test
    void calculatePrice_withNokPerHourAt60AndDifferentDates_shouldReturnTheRightAmountToPay() {

        LocalDateTime startDateTime = LocalDateTime.of(2023,8,8,12,0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023,8,8,13,0,0);

        PriceDetailsRecord priceDetails = m1ZonePriceStrategy.calculatePrice(startDateTime, endDateTime);

        assertEquals(DateTimeUtils.formatDateTimeString(startDateTime.toString()), priceDetails.arrivalDateTime());
        assertEquals(DateTimeUtils.formatDateTimeString(endDateTime.toString()), priceDetails.departureDateTime());
        assertEquals("60.0", priceDetails.price());
    }
}