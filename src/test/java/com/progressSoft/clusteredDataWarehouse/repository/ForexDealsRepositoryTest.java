package com.progressSoft.clusteredDataWarehouse.repository;

import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
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
class ForexDealsRepositoryTest {
    @Autowired
    private FxDealRepository fxDealRepository;
    private ForexDeals forexDeals;

    @BeforeEach
    void setUp() {
        forexDeals = ForexDeals.builder()
                .dealUniqueId("01")
                .dealAmount(BigDecimal.valueOf(20000.0))
                .fromCurrencyISOCode(Currency.getInstance("USD"))
                .toCurrencyISOCode(Currency.getInstance("NGN"))
                .dealTimeStamp(LocalDateTime.now())
                .build();
    }

    @Test
    void saveDeal() {
        ForexDeals savedDeal = fxDealRepository.save(forexDeals);
        assertThat(savedDeal).isNotNull();
        assertThat(savedDeal).isEqualTo(forexDeals);
    }

    @Test
    void findAllDeals() {
        List<ForexDeals> forexDealsList = Arrays.asList(
                ForexDeals.builder()
                        .dealUniqueId("01")
                        .dealAmount(BigDecimal.valueOf(20000.0))
                        .fromCurrencyISOCode(Currency.getInstance("USD"))
                        .toCurrencyISOCode(Currency.getInstance("NGN"))
                        .dealTimeStamp(LocalDateTime.now())
                        .build(),
                ForexDeals.builder()
                        .dealUniqueId("02")
                        .dealAmount(BigDecimal.valueOf(20000.0))
                        .fromCurrencyISOCode(Currency.getInstance("USD"))
                        .toCurrencyISOCode(Currency.getInstance("NGN"))
                        .dealTimeStamp(LocalDateTime.now())
                        .build(),
                ForexDeals.builder()
                        .dealUniqueId("03")
                        .dealAmount(BigDecimal.valueOf(20000.0))
                        .fromCurrencyISOCode(Currency.getInstance("USD"))
                        .toCurrencyISOCode(Currency.getInstance("NGN"))
                        .dealTimeStamp(LocalDateTime.now())
                        .build());
        fxDealRepository.saveAll(forexDealsList);

        List<ForexDeals> savedForexDeals = fxDealRepository.findAll();

        assertThat(savedForexDeals).isNotNull();
        assertThat(savedForexDeals.size()).isEqualTo(forexDealsList.size());
    }
}