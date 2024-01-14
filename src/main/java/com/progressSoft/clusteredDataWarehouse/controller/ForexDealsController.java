package com.progressSoft.clusteredDataWarehouse.controller;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ApiResponse;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.service.ForexDealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.progressSoft.clusteredDataWarehouse.util.ApiResponseUtils.buildApiResponse;

@Tag(
        name = "Bloomberg Forex Deal Controller",
        description = "Exposes REST APIs for Bloomberg Forex Deal Controller"
)

@RestController
@RequestMapping("/api/v1/forexDeals")
@Slf4j
@RequiredArgsConstructor
public class ForexDealsController {

    private final ForexDealService forexDealService;


    @Operation(
            summary = "Create and Save Forex Deals REST API",
            description = "This REST API is used to Create and Save Create and Save Forex Deals in a Database"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "HTTP Status 201 CREATED")
    @PostMapping("/create/and/save")
    public ResponseEntity<ApiResponse> createAndSaveFxDeal(@Valid @RequestBody ForexDealsRequest forexDealsRequest) {
        ForexDealsResponse forexDealsResponse = forexDealService.createAndSaveForexDeal(forexDealsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(buildApiResponse(forexDealsResponse, HttpStatus.OK));
    }

    @Operation(
            summary = "Get Forex Deals By UniqueId REST API",
            description = "This REST API is used to Get Forex Deals By UniqueId in a Database"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/get/by/uniqueId/{uniqueId}")
    public ResponseEntity<ApiResponse> getForexDealByUniqueId(@PathVariable String uniqueId) {
        ForexDealsResponse forexDealsResponse = forexDealService.getForexDealByUniqueId(uniqueId);
        return ResponseEntity.status(HttpStatus.OK).body(buildApiResponse(forexDealsResponse, HttpStatus.OK));
    }

    @Operation(
            summary = "Get All Forex Deals REST API",
            description = "This REST API is used to Get All Forex Deals in the Database"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllForexDealsWithPagination(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(buildApiResponse(forexDealService.getAllForexDealsWithPagination(pageNo, pageSize), HttpStatus.OK));
    }

}

