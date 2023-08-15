package com.moonpark.parkingpricecalculator.controllers;

import com.moonpark.parkingpricecalculator.model.PriceDetailsRecord;
import com.moonpark.parkingpricecalculator.services.ParkingPriceService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Parking Price Calculator")
@RequestMapping("/api/parkingPrice")
public class ParkingPriceController {

    @Autowired
    private ParkingPriceService parkingPriceService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Not Found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<PriceDetailsRecord> calculatePriceZone(@RequestParam String parkingZone,
                                                          @Parameter(description = "<font size=\"+0.5\">"
                                                              + "<li>ex. 2023-08-13 14:00:00 </li>") @RequestParam String arrivalDateTime,
                                                          @Parameter(description = "<font size=\"+0.5\">"
                                                              + "<li>ex. 2023-08-13 14:00:00 </li>") @RequestParam String departureDateTime) {

        return ResponseEntity.ok(parkingPriceService.calculatePrice(parkingZone, arrivalDateTime, departureDateTime));
    }
}
