package com.giantleap.moonpark.controllers;

import com.giantleap.moonpark.model.enums.ParkingZoneEnum;
import com.giantleap.moonpark.services.ParkingPriceService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parkingPrice")
public class ParkingPriceController {

    @Autowired
    private ParkingPriceService parkingPriceService;

    //TODO: Must return JSON!
    @GetMapping()
    ResponseEntity<String> calculatePriceZone(@RequestParam String parkingZone,
                                              @RequestParam String arrivalDateTime,
                                              @RequestParam String departureDateTime) {

        return ResponseEntity.ok(parkingPriceService.calculatePrice(parkingZone, arrivalDateTime, departureDateTime));
    }
}
