package com.progressSoft.clusteredDataWarehouse.service.serviceImpl;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.exception.EntityAlreadyExistsException;
import com.progressSoft.clusteredDataWarehouse.exception.ISOCodeValidationException;
import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
import com.progressSoft.clusteredDataWarehouse.repository.ForexDealRepository;
import com.progressSoft.clusteredDataWarehouse.service.ForexDealService;
import com.progressSoft.clusteredDataWarehouse.service.mapper.ForexDealMapper;
import com.progressSoft.clusteredDataWarehouse.service.validator.ForexDealValidations;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
class ForexDealServiceImplTest {

    ForexDealService forexDealService;

    @Mock
    ForexDealValidations forexDealValidations;

    @Mock
    ForexDealMapper forexDealMapper;
    @Mock
    ForexDealRepository forexDealRepository;


    @BeforeEach
    void setUp() {
        forexDealService = new ForexDealServiceImpl(forexDealRepository, forexDealMapper, forexDealValidations);
    }

    @Test
    void shouldSaveDealIfUniqueIdIsUnique() {
        // Arrange
        ForexDealsRequest forexDealsRequest = createSampleDealRequest();
        ForexDeals forexDeals = createSampleDeal(forexDealsRequest);

        // Mock repository behavior
        lenient().when(forexDealRepository.findForexDealByDealUniqueId(anyString()))
                .thenReturn(Optional.empty());
        lenient().when(forexDealRepository.save(forexDeals))
                .thenReturn(forexDeals);
        lenient().when(forexDealMapper.MapForexDealRequestToForexDealEntity(forexDealsRequest))
                .thenReturn(forexDeals);

        // Act
        forexDealService.createAndSaveForexDeal(forexDealsRequest);

        // Assert
        verify(forexDealValidations, times(1)).isDealUniqueId(forexDealsRequest.getDealUniqueId());
        verify(forexDealRepository, times(1)).save(forexDeals);
    }

    @Test
    void shouldThrowExceptionWhenUniqueIdAlreadyExists() {
        // Arrange
        ForexDealsRequest forexDealsRequest = createSampleDealRequest();
        ForexDeals forexDeals = createSampleDeal(forexDealsRequest);

        // Mock repository behavior
        lenient().when(forexDealRepository.findForexDealByDealUniqueId(forexDealsRequest.getDealUniqueId()))
                .thenReturn(Optional.of(forexDeals));
        lenient().when(forexDealValidations.isDealUniqueId(forexDealsRequest.getDealUniqueId()))
                .thenThrow(new EntityAlreadyExistsException("Forex Deal already exists"));

        // Act and Assert
        EntityAlreadyExistsException ex = assertThrows(EntityAlreadyExistsException.class, () -> {
            forexDealService.createAndSaveForexDeal(forexDealsRequest);
        });
        assertEquals("Forex Deal already exists", ex.getMessage());
    }

    @Test
    void shouldGetDealByUniqueId() {
        // Arrange
        ForexDealsRequest forexDealsRequest = createSampleDealRequest();
        ForexDeals forexDeals = createSampleDeal(forexDealsRequest);
        when(forexDealRepository.findForexDealByDealUniqueId(anyString()))
                .thenReturn(Optional.of(forexDeals));

        // Act
        forexDealService.getForexDealByUniqueId(forexDealsRequest.getDealUniqueId());

        // Assert
        verify(forexDealRepository, times(1)).findForexDealByDealUniqueId(anyString());
    }

    @Test
    void shouldThrowInvalidISOExceptionWhenFromCurrencyIsInvalid() {
        // Arrange
        ForexDealsRequest forexDealsRequest = createInvalidCurrencyDealRequest();

        // Mock validation behavior
        when(forexDealValidations.isCurrencyISOCodeValid(forexDealsRequest.getFromCurrencyISOCode()))
                .thenThrow(ISOCodeValidationException.class);

        // Act and Assert
        assertThrows(ISOCodeValidationException.class, () -> forexDealService.createAndSaveForexDeal(forexDealsRequest));
    }

    @Test
    void shouldThrowInvalidISOExceptionWhenToCurrencyIsInvalid() {
        // Arrange
        ForexDealsRequest forexDealsRequest = createInvalidCurrencyDealRequest();

        // Mock validation behavior
        when(forexDealValidations.isCurrencyISOCodeValid(forexDealsRequest.getFromCurrencyISOCode())).thenReturn(true);
        when(forexDealValidations.isCurrencyISOCodeValid(forexDealsRequest.getToCurrencyISOCode()))
                .thenThrow(ISOCodeValidationException.class);

        // Act and Assert
        assertThrows(ISOCodeValidationException.class, () -> forexDealService.createAndSaveForexDeal(forexDealsRequest));
    }

    private ForexDealsRequest createSampleDealRequest() {
        return ForexDealsRequest.builder()
                .dealUniqueId("id")
                .toCurrencyISOCode("GBP")
                .fromCurrencyISOCode("USD")
                .dealAmount(new BigDecimal("10000.0"))
                .build();
    }

    private ForexDealsRequest createInvalidCurrencyDealRequest() {
        return ForexDealsRequest.builder()
                .dealAmount(BigDecimal.valueOf(10000.0))
                .fromCurrencyISOCode("jjj")
                .toCurrencyISOCode("NGN")
                .build();
    }

    private ForexDeals createSampleDeal(ForexDealsRequest requestDto) {
        Currency toCurrencyISOCode = Currency.getInstance(requestDto.getToCurrencyISOCode());
        Currency fromCurrencyISOCode = Currency.getInstance(requestDto.getFromCurrencyISOCode());

        return ForexDeals.builder()
                .dealUniqueId(requestDto.getDealUniqueId())
                .toCurrencyISOCode(toCurrencyISOCode)
                .fromCurrencyISOCode(fromCurrencyISOCode)
                .dealAmount(requestDto.getDealAmount())
                .id(1L)
                .build();
    }
}
