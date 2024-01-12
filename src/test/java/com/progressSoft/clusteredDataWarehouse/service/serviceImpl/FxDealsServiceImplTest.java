package com.progressSoft.clusteredDataWarehouse.service.serviceImpl;

import com.progressSoft.clusteredDataWarehouse.dto.request.FxDealRequestDTO;
import com.progressSoft.clusteredDataWarehouse.dto.response.FxDealResponseDTO;
import com.progressSoft.clusteredDataWarehouse.exception.DuplicateEntityException;
import com.progressSoft.clusteredDataWarehouse.exception.InvalidISOCodeException;
import com.progressSoft.clusteredDataWarehouse.model.FxDeal;
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
        FxDealRequestDTO requestDto = createDealRequest();
        FxDeal fxDeal = createDeal(requestDto);
        lenient().when(fxDealsRepository.findByDealUniqueId(anyString()))
                .thenReturn(Optional.empty());
        lenient().when(fxDealsRepository.save(fxDeal)).
                thenReturn(fxDeal);
        lenient().when(fxDealMapper.MapDtoRequestToFxDeal(requestDto))
                .thenReturn(fxDeal);
        FxDealResponseDTO responseDto = fxDealService.saveFxDeal(requestDto);
        verify(fxDealValidator, times(1)).isDealUnique(requestDto.getDealUniqueId());
        verify(fxDealsRepository, times(1)).save(fxDeal);
    }

    @Test
    void testExceptionThrownWhenUniqueIdAlreadyExists() {
        FxDealRequestDTO dealRequestDto = createDealRequest();
        FxDeal deal = createDeal(dealRequestDto);

        lenient().when(fxDealsRepository.findByDealUniqueId(dealRequestDto.getDealUniqueId()))
                .thenReturn(Optional.of(deal));
        lenient().when(fxDealValidator.isDealUnique("id"))
                .thenThrow(new DuplicateEntityException("Deal already exists"));

        DuplicateEntityException ex = assertThrows(DuplicateEntityException.class, () -> {
            fxDealService.saveFxDeal(dealRequestDto);
        });
        assertEquals("Deal already exists", ex.getMessage());
    }


    @Test
    void getDealByUniqueId() {
        FxDealRequestDTO dealRequestDto = createDealRequest();
        FxDeal deal = createDeal(dealRequestDto);
        when(fxDealsRepository.findByDealUniqueId(anyString()))
                .thenReturn(Optional.of(deal));
        fxDealService.getDealByUniqueId(dealRequestDto.getDealUniqueId());
        verify(fxDealsRepository, times(1)).findByDealUniqueId(anyString());
    }


    @Test
    void shouldThrowAnInvalidISOExceptionWhenFromCurrencyIsInvalid() {
        FxDealRequestDTO fxDealDTO = FxDealRequestDTO.builder()
                .dealAmount(BigDecimal.valueOf(20000.0))
                .fromCurrencyISOCode("jjj")
                .toCurrencyISOCode("NGN")
                .build();
        when(fxDealValidator.isISOCurrencyCodeValid(fxDealDTO.getFromCurrencyISOCode())).thenThrow(InvalidISOCodeException.class);
        assertThrows(InvalidISOCodeException.class, () -> fxDealService.saveFxDeal(fxDealDTO));
    }


    @Test
    void shouldThrowAnInvalidISOExceptionWhenToCurrencyIsInvalid() {
        FxDealRequestDTO fxDealDTO = FxDealRequestDTO.builder()
                .dealAmount(BigDecimal.valueOf(20000.0))
                .fromCurrencyISOCode("USD")
                .toCurrencyISOCode("fff")
                .build();
        when(fxDealValidator.isISOCurrencyCodeValid(fxDealDTO.getFromCurrencyISOCode())).thenReturn(true);
        when(fxDealValidator.isISOCurrencyCodeValid(fxDealDTO.getToCurrencyISOCode())).thenThrow(InvalidISOCodeException.class);
        assertThrows(InvalidISOCodeException.class, () -> fxDealService.saveFxDeal(fxDealDTO));
    }

    private FxDealRequestDTO createDealRequest() {
        return FxDealRequestDTO.builder()
                .dealUniqueId("id")
                .toCurrencyISOCode("ALL")
                .fromCurrencyISOCode("USD")
                .dealAmount(new BigDecimal("50000.0"))
                .build();
    }

    private FxDeal createDeal(FxDealRequestDTO requestDto) {
        Currency toCurrencyISOCode = Currency.getInstance(requestDto.getToCurrencyISOCode());
        Currency fromCurrencyISOCode = Currency.getInstance(requestDto.getFromCurrencyISOCode());

        return FxDeal.builder()
                .dealUniqueId(requestDto.getDealUniqueId())
                .toCurrencyISOCode(toCurrencyISOCode)
                .fromCurrencyISOCode(fromCurrencyISOCode)
                .dealAmount(requestDto.getDealAmount())
                .id(1L)
                .build();
    }
}