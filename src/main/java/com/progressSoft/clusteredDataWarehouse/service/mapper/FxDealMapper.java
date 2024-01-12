package com.progressSoft.clusteredDataWarehouse.service.mapper;

import com.progressSoft.clusteredDataWarehouse.dto.request.FxDealRequestDTO;
import com.progressSoft.clusteredDataWarehouse.dto.response.FxDealResponseDTO;
import com.progressSoft.clusteredDataWarehouse.model.FxDeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Currency;
@Component
@Slf4j
public class FxDealMapper {
    public FxDeal MapDtoRequestToFxDeal(FxDealRequestDTO fxDealRequestDTO) {
        FxDeal fxDeal = new FxDeal();
        fxDeal.setDealUniqueId(fxDealRequestDTO.getDealUniqueId());
        fxDeal.setDealAmount(fxDealRequestDTO.getDealAmount());
        fxDeal.setFromCurrencyISOCode(fxDealRequestDTO.getFromCurrencyISOCode() != null ? Currency.getInstance(fxDealRequestDTO.getFromCurrencyISOCode()) : null);
        fxDeal.setToCurrencyISOCode(fxDealRequestDTO.getToCurrencyISOCode() != null? Currency.getInstance(fxDealRequestDTO.getToCurrencyISOCode()) : null);
        fxDeal.setDealTimeStamp(LocalDateTime.now());
        return fxDeal;
    }
    public FxDealResponseDTO mapFxDealToDTORequest(FxDeal fxDeal) {
        FxDealResponseDTO fxDealDTO = new FxDealResponseDTO();
        fxDealDTO.setDealAmount(fxDeal.getDealAmount() != null? fxDeal.getDealAmount() : null);
        fxDealDTO.setDealUniqueId(fxDeal.getDealUniqueId() != null ? fxDeal.getDealUniqueId() : null);
        fxDealDTO.setFromCurrencyISOCode(fxDeal.getFromCurrencyISOCode() != null ? fxDeal.getFromCurrencyISOCode().getCurrencyCode() : null);
        fxDealDTO.setToCurrencyISOCode(fxDeal.getToCurrencyISOCode() != null ? fxDeal.getToCurrencyISOCode().getCurrencyCode() : null);
        fxDealDTO.setDealTimeStamp(fxDeal.getDealTimeStamp() != null ? fxDeal.getDealTimeStamp() : null);
        return fxDealDTO;
    }
}
