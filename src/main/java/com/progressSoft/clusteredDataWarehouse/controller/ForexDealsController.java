package com.progressSoft.clusteredDataWarehouse.controller;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ApiResponse;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.service.ForexDealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.progressSoft.clusteredDataWarehouse.util.ApiResponseUtils.buildApiResponse;

@RestController
@RequestMapping("/api/v1/fxDeals")
@Slf4j
@RequiredArgsConstructor
public class ForexDealsController {

    private final ForexDealService forexDealService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createFxDeal(@Valid @RequestBody ForexDealsRequest forexDealsRequest) {
        ForexDealsResponse forexDealsResponse = forexDealService.createAndSaveForexDeal(forexDealsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(buildApiResponse(forexDealsResponse, HttpStatus.OK));
    }

    @GetMapping("/{dealId}")
    public ResponseEntity<ApiResponse> getDeal(@PathVariable String dealId) {
        ForexDealsResponse forexDealsResponse = forexDealService.getForexDealByUniqueId(dealId);
        return ResponseEntity.status(HttpStatus.OK).body(buildApiResponse(forexDealsResponse, HttpStatus.OK));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllFxDeals(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(buildApiResponse(forexDealService.getAllForexDealsWithPagination(pageNo, pageSize), HttpStatus.OK));
    }

}

