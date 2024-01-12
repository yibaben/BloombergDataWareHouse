package com.progressSoft.clusteredDataWarehouse.repository;

import com.progressSoft.clusteredDataWarehouse.model.FxDeal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FxDealRepositoryTest {
    @Autowired
    private FxDealRepository fxDealRepository;
    private FxDeal fxDeal;

    @BeforeEach
    void setUp() {
        fxDeal = FxDeal.builder()
                .dealUniqueId("01")
                .dealAmount(BigDecimal.valueOf(20000.0))
                .fromCurrencyISOCode(Currency.getInstance("USD"))
                .toCurrencyISOCode(Currency.getInstance("NGN"))
                .dealTimeStamp(LocalDateTime.now())
                .build();
    }

    @Test
    void saveDeal() {
        FxDeal savedDeal = fxDealRepository.save(fxDeal);
        assertThat(savedDeal).isNotNull();
        assertThat(savedDeal).isEqualTo(fxDeal);
    }

    @Test
    void findAllDeals() {
        List<FxDeal> fxDealList = Arrays.asList(
                FxDeal.builder()
                        .dealUniqueId("01")
                        .dealAmount(BigDecimal.valueOf(20000.0))
                        .fromCurrencyISOCode(Currency.getInstance("USD"))
                        .toCurrencyISOCode(Currency.getInstance("NGN"))
                        .dealTimeStamp(LocalDateTime.now())
                        .build(),
                FxDeal.builder()
                        .dealUniqueId("02")
                        .dealAmount(BigDecimal.valueOf(20000.0))
                        .fromCurrencyISOCode(Currency.getInstance("USD"))
                        .toCurrencyISOCode(Currency.getInstance("NGN"))
                        .dealTimeStamp(LocalDateTime.now())
                        .build(),
                FxDeal.builder()
                        .dealUniqueId("03")
                        .dealAmount(BigDecimal.valueOf(20000.0))
                        .fromCurrencyISOCode(Currency.getInstance("USD"))
                        .toCurrencyISOCode(Currency.getInstance("NGN"))
                        .dealTimeStamp(LocalDateTime.now())
                        .build());
        fxDealRepository.saveAll(fxDealList);

        List<FxDeal> savedFxDeals = fxDealRepository.findAll();

        assertThat(savedFxDeals).isNotNull();
        assertThat(savedFxDeals.size()).isEqualTo(fxDealList.size());
    }
}