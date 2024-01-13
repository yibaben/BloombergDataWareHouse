package com.progressSoft.clusteredDataWarehouse.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForexDealsRequest {

    @NotEmpty(message = "Deal Id field cannot be empty")
    private String dealUniqueId;
    @NotEmpty(message = "fromCurrency code field cannot be empty")
    private String fromCurrencyISOCode;
    @NotEmpty(message = "toCurrency code field cannot be empty")
    private String toCurrencyISOCode;
    @NotNull(message = "Please enter deal amount")
    private BigDecimal dealAmount;
}
