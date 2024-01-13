package com.progressSoft.clusteredDataWarehouse.repository;

import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;




@DataJpaTest
class ForexDealsRepositoryTest {

    @Autowired
    private ForexDealRepository forexDealRepository;

    private ForexDeals forexDeals;

    @BeforeEach
    void setUp() {
        // Setting up a sample ForexDeals object for testing
        forexDeals = new ForexDeals();
        forexDeals.setDealUniqueId("1001");
        forexDeals.setFromCurrencyISOCode(Currency.getInstance("USD"));
        forexDeals.setToCurrencyISOCode(Currency.getInstance("NGN"));
        forexDeals.setDealTimeStamp(LocalDateTime.now());
        forexDeals.setDealAmount(BigDecimal.valueOf(10000.0));
    }

    @Test
    void saveForexDeal() {
        // Test saving a single ForexDeals object
        ForexDeals savedDeal = forexDealRepository.save(forexDeals);

        // Assertions
        assertThat(savedDeal).isNotNull();
        assertThat(savedDeal).isEqualTo(forexDeals);
    }

    @Test
    void getForexDealByUniqueId() {
        // Save a ForexDeals object to the repository
        ForexDeals savedDeal = forexDealRepository.save(forexDeals);

        // Retrieve the saved deal by unique id
        ForexDeals retrievedDeal = forexDealRepository.findForexDealByDealUniqueId(savedDeal.getDealUniqueId())
                .orElseThrow(() -> new RuntimeException("Deal not found"));

        // Assertions
        assertThat(retrievedDeal).isNotNull();
        assertThat(retrievedDeal).isEqualTo(savedDeal);
    }


    @Test
    void findAllForexDeals() {
        // Creating a list of ForexDeals objects for testing
        List<ForexDeals> forexDealsList = new ArrayList<>();
        ForexDeals deal1 = new ForexDeals();
        deal1.setDealUniqueId("1001");
        deal1.setFromCurrencyISOCode(Currency.getInstance("USD"));
        deal1.setToCurrencyISOCode(Currency.getInstance("NGN"));
        deal1.setDealTimeStamp(LocalDateTime.now());
        deal1.setDealAmount(BigDecimal.valueOf(10000.0));
        forexDealsList.add(deal1);

        ForexDeals deal2 = new ForexDeals();
        deal2.setDealUniqueId("1002");
        deal2.setFromCurrencyISOCode(Currency.getInstance("USD"));
        deal2.setToCurrencyISOCode(Currency.getInstance("NGN"));
        deal2.setDealTimeStamp(LocalDateTime.now());
        deal2.setDealAmount(BigDecimal.valueOf(20000.0));
        forexDealsList.add(deal2);

        ForexDeals deal3 = new ForexDeals();
        deal3.setDealUniqueId("1003");
        deal3.setFromCurrencyISOCode(Currency.getInstance("USD"));
        deal3.setToCurrencyISOCode(Currency.getInstance("NGN"));
        deal3.setDealTimeStamp(LocalDateTime.now());
        deal3.setDealAmount(BigDecimal.valueOf(30000.0));
        forexDealsList.add(deal3);

        // Saving the list of ForexDeals objects to the repository
        forexDealRepository.saveAll(forexDealsList);

        // Retrieving all saved ForexDeals objects
        List<ForexDeals> savedForexDeals = forexDealRepository.findAll();

        // Assertions
        assertThat(savedForexDeals).isNotNull();
        assertThat(savedForexDeals.size()).isEqualTo(forexDealsList.size());
    }
}



//===========TESTS FOR ForexDealRepository.java With Builder Pattern================

//@DataJpaTest
//class ForexDealsRepositoryTest {
//
//    @Autowired
//    private ForexDealRepository forexDealRepository;
//
//    private ForexDeals forexDeals;
//
//    @BeforeEach
//    void setUp() {
//        // Setting up a sample ForexDeals object for testing
//        forexDeals = ForexDeals.builder()
//                .dealUniqueId("01")
//                .dealAmount(BigDecimal.valueOf(20000.0))
//                .fromCurrencyISOCode(Currency.getInstance("USD"))
//                .toCurrencyISOCode(Currency.getInstance("NGN"))
//                .dealTimeStamp(LocalDateTime.now())
//                .build();
//    }
//
//    @Test
//    void saveDeal() {
//        // Test saving a single ForexDeals object
//        ForexDeals savedDeal = forexDealRepository.save(forexDeals);
//
//        // Assertions
//        assertThat(savedDeal).isNotNull();
//        assertThat(savedDeal).isEqualTo(forexDeals);
//    }
//
//    @Test
//    void findAllDeals() {
//        // Creating a list of ForexDeals objects for testing
//        List<ForexDeals> forexDealsList = Arrays.asList(
//                ForexDeals.builder()
//                        .dealUniqueId("01")
//                        .dealAmount(BigDecimal.valueOf(20000.0))
//                        .fromCurrencyISOCode(Currency.getInstance("USD"))
//                        .toCurrencyISOCode(Currency.getInstance("NGN"))
//                        .dealTimeStamp(LocalDateTime.now())
//                        .build(),
//                ForexDeals.builder()
//                        .dealUniqueId("02")
//                        .dealAmount(BigDecimal.valueOf(20000.0))
//                        .fromCurrencyISOCode(Currency.getInstance("USD"))
//                        .toCurrencyISOCode(Currency.getInstance("NGN"))
//                        .dealTimeStamp(LocalDateTime.now())
//                        .build(),
//                ForexDeals.builder()
//                        .dealUniqueId("03")
//                        .dealAmount(BigDecimal.valueOf(20000.0))
//                        .fromCurrencyISOCode(Currency.getInstance("USD"))
//                        .toCurrencyISOCode(Currency.getInstance("NGN"))
//                        .dealTimeStamp(LocalDateTime.now())
//                        .build());
//
//        // Saving the list of ForexDeals objects to the repository
//        forexDealRepository.saveAll(forexDealsList);
//
//        // Retrieving all saved ForexDeals objects
//        List<ForexDeals> savedForexDeals = forexDealRepository.findAll();
//
//        // Assertions
//        assertThat(savedForexDeals).isNotNull();
//        assertThat(savedForexDeals.size()).isEqualTo(forexDealsList.size());
//    }
//}
