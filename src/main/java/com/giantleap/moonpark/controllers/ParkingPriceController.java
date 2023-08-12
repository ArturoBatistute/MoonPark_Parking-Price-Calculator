package com.giantleap.moonpark.controllers;

import com.giantleap.moonpark.model.enums.ParkingZoneEnum;
import com.giantleap.moonpark.services.ParkingPriceService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parkingPrice")
public class ParkingPriceController {

    @Autowired
    private ParkingPriceService parkingPriceService;

    @PostMapping("/calculate")
    String calculatePriceZone() {

        LocalDateTime localDateTime = LocalDateTime.now();

        return parkingPriceService.calculatePrice(localDateTime, ParkingZoneEnum.M1);
    }
}
