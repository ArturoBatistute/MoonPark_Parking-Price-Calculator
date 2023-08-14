package com.giantleap.moonpark.controllers;

import com.giantleap.moonpark.model.PriceDetailsRecord;
import com.giantleap.moonpark.services.ParkingPriceService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<PriceDetailsRecord> calculatePriceZone(@RequestParam String parkingZone,
                                                          @RequestParam String arrivalDateTime,
                                                          @RequestParam String departureDateTime) {

        return ResponseEntity.ok(parkingPriceService.calculatePrice(parkingZone, arrivalDateTime, departureDateTime));
    }
}
