package com.progressSoft.clusteredDataWarehouse.service.validator;

import com.progressSoft.clusteredDataWarehouse.exception.EntityAlreadyExistsException;
import com.progressSoft.clusteredDataWarehouse.exception.AmountValidationException;
import com.progressSoft.clusteredDataWarehouse.exception.ISOCodeValidationException;
import com.progressSoft.clusteredDataWarehouse.exception.DuplicateCurrencyException;
import com.progressSoft.clusteredDataWarehouse.repository.ForexDealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import java.util.Currency;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForexDealValidations {
    private final ForexDealRepository dealsRepository;

    // Check if the dealUniqueId is unique
    public boolean isDealUniqueId(String dealUniqueId) {
        // Using repository method to find an existing deal by dealUniqueId
        Optional<ForexDeals> existingDeal = dealsRepository.findForexDealByDealUniqueId(dealUniqueId);
        if (existingDeal.isPresent()) {
            log.error("Forex Deal with this id already exists");
            throw new EntityAlreadyExistsException("Forex Deal with this id already exists");
        }
        return true;
    }

    // Validate if the provided currency code is a valid ISO currency code
    public boolean isCurrencyISOCodeValid(String currencyISOCode) {
        boolean validISO = Currency.getAvailableCurrencies()
                .stream()
                .anyMatch(currency -> currency.getCurrencyCode().equals(currencyISOCode));

        if (!validISO) {
            log.error("Invalid Currency ISO Code");
            throw new ISOCodeValidationException("Invalid Currency ISO Code");
        }
        return true;
    }

    // Validate that fromCurrency and toCurrency are not the same
    public void validateFromCurrencyAndToCurrency(String fromCurrencyISOCode, String toCurrencyISOCode) {
        if (fromCurrencyISOCode.equals(toCurrencyISOCode)) {
            log.error("FromCurrency and ToCurrency should not be the same");
            throw new DuplicateCurrencyException("FromCurrency and ToCurrency should not be the same");
        }
    }

    // Validate that the dealAmount is greater than zero
    public void dealAmountValidation(BigDecimal dealAmount) {
        if (dealAmount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Invalid deal amount, amount most be greater than zero");
            throw new AmountValidationException("Invalid deal amount, amount most be greater than zero");
        }
    }
}

