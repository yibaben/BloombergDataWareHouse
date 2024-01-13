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
public class FxDealMapper {
    public ForexDeals MapDtoRequestToFxDeal(ForexDealsRequest forexDealsRequest) {
        ForexDeals forexDeals = new ForexDeals();
        forexDeals.setDealUniqueId(forexDealsRequest.getDealUniqueId());
        forexDeals.setDealAmount(forexDealsRequest.getDealAmount());
        forexDeals.setFromCurrencyISOCode(forexDealsRequest.getFromCurrencyISOCode() != null ? Currency.getInstance(forexDealsRequest.getFromCurrencyISOCode()) : null);
        forexDeals.setToCurrencyISOCode(forexDealsRequest.getToCurrencyISOCode() != null? Currency.getInstance(forexDealsRequest.getToCurrencyISOCode()) : null);
        forexDeals.setDealTimeStamp(LocalDateTime.now());
        return forexDeals;
    }
    public ForexDealsResponse mapFxDealToDTORequest(ForexDeals forexDeals) {
        ForexDealsResponse fxDealDTO = new ForexDealsResponse();
        fxDealDTO.setDealAmount(forexDeals.getDealAmount() != null? forexDeals.getDealAmount() : null);
        fxDealDTO.setDealUniqueId(forexDeals.getDealUniqueId() != null ? forexDeals.getDealUniqueId() : null);
        fxDealDTO.setFromCurrencyISOCode(forexDeals.getFromCurrencyISOCode() != null ? forexDeals.getFromCurrencyISOCode().getCurrencyCode() : null);
        fxDealDTO.setToCurrencyISOCode(forexDeals.getToCurrencyISOCode() != null ? forexDeals.getToCurrencyISOCode().getCurrencyCode() : null);
        fxDealDTO.setDealTimeStamp(forexDeals.getDealTimeStamp() != null ? forexDeals.getDealTimeStamp() : null);
        return fxDealDTO;
    }
}
