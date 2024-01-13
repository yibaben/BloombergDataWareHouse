package com.progressSoft.clusteredDataWarehouse.service.serviceImpl;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.exception.EntityAlreadyExistsException;
import com.progressSoft.clusteredDataWarehouse.exception.ISOCodeValidationException;
import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
import com.progressSoft.clusteredDataWarehouse.repository.FxDealRepository;
import com.progressSoft.clusteredDataWarehouse.service.FxDealService;
import com.progressSoft.clusteredDataWarehouse.service.mapper.FxDealMapper;
import com.progressSoft.clusteredDataWarehouse.service.validator.FxDealValidator;
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
class FxDealsServiceImplTest {
    @Mock
    FxDealRepository fxDealsRepository;
    @Mock
    FxDealMapper fxDealMapper;
    @Mock
    FxDealValidator fxDealValidator;

    FxDealService fxDealService;

    @BeforeEach
    void setUp() {
        fxDealService = new FxDealServiceImpl(fxDealsRepository, fxDealMapper, fxDealValidator);
    }

    @Test
    void saveDealUniqueDeal() {
        ForexDealsRequest requestDto = createDealRequest();
        ForexDeals forexDeals = createDeal(requestDto);
        lenient().when(fxDealsRepository.findByDealUniqueId(anyString()))
                .thenReturn(Optional.empty());
        lenient().when(fxDealsRepository.save(forexDeals)).
                thenReturn(forexDeals);
        lenient().when(fxDealMapper.MapDtoRequestToFxDeal(requestDto))
                .thenReturn(forexDeals);
        ForexDealsResponse responseDto = fxDealService.saveFxDeal(requestDto);
        verify(fxDealValidator, times(1)).isDealUnique(requestDto.getDealUniqueId());
        verify(fxDealsRepository, times(1)).save(forexDeals);
    }

    @Test
    void testExceptionThrownWhenUniqueIdAlreadyExists() {
        ForexDealsRequest dealRequestDto = createDealRequest();
        ForexDeals deal = createDeal(dealRequestDto);

        lenient().when(fxDealsRepository.findByDealUniqueId(dealRequestDto.getDealUniqueId()))
                .thenReturn(Optional.of(deal));
        lenient().when(fxDealValidator.isDealUnique("id"))
                .thenThrow(new EntityAlreadyExistsException("Deal already exists"));

        EntityAlreadyExistsException ex = assertThrows(EntityAlreadyExistsException.class, () -> {
            fxDealService.saveFxDeal(dealRequestDto);
        });
        assertEquals("Deal already exists", ex.getMessage());
    }


    @Test
    void getDealByUniqueId() {
        ForexDealsRequest dealRequestDto = createDealRequest();
        ForexDeals deal = createDeal(dealRequestDto);
        when(fxDealsRepository.findByDealUniqueId(anyString()))
                .thenReturn(Optional.of(deal));
        fxDealService.getDealByUniqueId(dealRequestDto.getDealUniqueId());
        verify(fxDealsRepository, times(1)).findByDealUniqueId(anyString());
    }


    @Test
    void shouldThrowAnInvalidISOExceptionWhenFromCurrencyIsInvalid() {
        ForexDealsRequest fxDealDTO = ForexDealsRequest.builder()
                .dealAmount(BigDecimal.valueOf(20000.0))
                .fromCurrencyISOCode("jjj")
                .toCurrencyISOCode("NGN")
                .build();
        when(fxDealValidator.isISOCurrencyCodeValid(fxDealDTO.getFromCurrencyISOCode())).thenThrow(ISOCodeValidationException.class);
        assertThrows(ISOCodeValidationException.class, () -> fxDealService.saveFxDeal(fxDealDTO));
    }


    @Test
    void shouldThrowAnInvalidISOExceptionWhenToCurrencyIsInvalid() {
        ForexDealsRequest fxDealDTO = ForexDealsRequest.builder()
                .dealAmount(BigDecimal.valueOf(20000.0))
                .fromCurrencyISOCode("USD")
                .toCurrencyISOCode("fff")
                .build();
        when(fxDealValidator.isISOCurrencyCodeValid(fxDealDTO.getFromCurrencyISOCode())).thenReturn(true);
        when(fxDealValidator.isISOCurrencyCodeValid(fxDealDTO.getToCurrencyISOCode())).thenThrow(ISOCodeValidationException.class);
        assertThrows(ISOCodeValidationException.class, () -> fxDealService.saveFxDeal(fxDealDTO));
    }

    private ForexDealsRequest createDealRequest() {
        return ForexDealsRequest.builder()
                .dealUniqueId("id")
                .toCurrencyISOCode("ALL")
                .fromCurrencyISOCode("USD")
                .dealAmount(new BigDecimal("50000.0"))
                .build();
    }

    private ForexDeals createDeal(ForexDealsRequest requestDto) {
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