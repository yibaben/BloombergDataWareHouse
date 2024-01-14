package com.progressSoft.clusteredDataWarehouse.service.mapper;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Currency;
@Component
@Slf4j
public class ForexDealMapper {

    /**
     * Maps a ForexDealsRequest to a ForexDeals entity.
     *
     * @param forexDealsRequest The input ForexDealsRequest.
     * @return ForexDeals entity mapped from the request.
     */
    public ForexDeals MapForexDealRequestToForexDealEntity(ForexDealsRequest forexDealsRequest) {
        // Create a new ForexDeals entity
        ForexDeals forexDeals = new ForexDeals();

        // Map properties from the request to the entity
        forexDeals.setDealUniqueId(forexDealsRequest.getDealUniqueId());
        forexDeals.setDealAmount(forexDealsRequest.getDealAmount());

        // Map Currency properties if not null
        forexDeals.setFromCurrencyISOCode(forexDealsRequest.getFromCurrencyISOCode() != null ? Currency.getInstance(forexDealsRequest.getFromCurrencyISOCode()) : null);
        forexDeals.setToCurrencyISOCode(forexDealsRequest.getToCurrencyISOCode() != null ? Currency.getInstance(forexDealsRequest.getToCurrencyISOCode()) : null);

        // Set the current timestamp for the deal
        forexDeals.setDealTimeStamp(LocalDateTime.now());

        return forexDeals;
    }

    /**
     * Maps a ForexDeals entity to a ForexDealsResponse.
     *
     * @param forexDeals The input ForexDeals entity.
     * @return ForexDealsResponse mapped from the entity.
     */
    public ForexDealsResponse mapForexDealEntityToForexDealResponse(ForexDeals forexDeals) {
        // Create a new ForexDealsResponse DTO
        ForexDealsResponse forexDealsResponse = new ForexDealsResponse();

        // Map properties from the entity to the response
        forexDealsResponse.setDealAmount(forexDeals.getDealAmount() != null ? forexDeals.getDealAmount() : null);
        forexDealsResponse.setDealUniqueId(forexDeals.getDealUniqueId() != null ? forexDeals.getDealUniqueId() : null);

        // Map Currency properties if not null
        forexDealsResponse.setFromCurrencyISOCode(forexDeals.getFromCurrencyISOCode() != null ? forexDeals.getFromCurrencyISOCode().getCurrencyCode() : null);
        forexDealsResponse.setToCurrencyISOCode(forexDeals.getToCurrencyISOCode() != null ? forexDeals.getToCurrencyISOCode().getCurrencyCode() : null);

        // Set the deal timestamp
        forexDealsResponse.setDealTimeStamp(forexDeals.getDealTimeStamp() != null ? forexDeals.getDealTimeStamp() : null);

        return forexDealsResponse;
    }
}
