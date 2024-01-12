package com.progressSoft.clusteredDataWarehouse.service.validator;

import com.progressSoft.clusteredDataWarehouse.exception.DuplicateEntityException;
import com.progressSoft.clusteredDataWarehouse.exception.InvalidAmountException;
import com.progressSoft.clusteredDataWarehouse.exception.InvalidISOCodeException;
import com.progressSoft.clusteredDataWarehouse.exception.SameCurrencyException;
import com.progressSoft.clusteredDataWarehouse.repository.FxDealRepository;
import org.springframework.stereotype.Component;
import com.progressSoft.clusteredDataWarehouse.model.FxDeal;

import java.math.BigDecimal;
import java.util.*;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@Component
public class FxDealValidator {
    private final FxDealRepository dealsRepository;
    public FxDealValidator(FxDealRepository dealsRepository) {
        this.dealsRepository = dealsRepository;
    }

    public boolean isDealUnique(String dealUniqueId) {
        Optional<FxDeal> existingDeal = dealsRepository.findByDealUniqueId(dealUniqueId);
        if (existingDeal.isPresent()) {
            log.error("Deal with this id already exist");
            throw new DuplicateEntityException("Deal with this id already exist");
        }
        return true;
    }

    public boolean isISOCurrencyCodeValid(String currencyCode) {
        boolean validIso = Currency.getAvailableCurrencies().stream().anyMatch(a -> a.getCurrencyCode().equals(currencyCode));
        if (!validIso)
            throw new InvalidISOCodeException("Invalid Currency ISO");
        return true;
    }

    public void validateFromCurrencyAndToCurrency(String fromCurrencyISOCode, String toCurrencyISOCode) {
        if (fromCurrencyISOCode.equals(toCurrencyISOCode)) {
            log.error("fromCurrency and toCurrency cannot be the same");
            throw new SameCurrencyException("fromCurrency and toCurrency cannot be the same");
        }
    }

    public void validateDealAmount(BigDecimal dealAmount) {
        if (dealAmount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Invalid deal amount, amount should be greater than zero");
            throw new InvalidAmountException("Invalid deal amount");
        }
    }

}
