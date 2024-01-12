package com.progressSoft.clusteredDataWarehouse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class FxDealResponseDTO {
    private String dealUniqueId;
    private String fromCurrencyISOCode;
    private String toCurrencyISOCode;
    private LocalDateTime dealTimeStamp;
    private BigDecimal dealAmount;
}
