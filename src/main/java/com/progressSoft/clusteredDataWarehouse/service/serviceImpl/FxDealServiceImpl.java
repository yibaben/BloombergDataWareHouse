package com.progressSoft.clusteredDataWarehouse.service.serviceImpl;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.dto.responses.PaginationResponse;
import com.progressSoft.clusteredDataWarehouse.exception.ForexDealEntityNotFoundException;
import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
import com.progressSoft.clusteredDataWarehouse.repository.FxDealRepository;
import com.progressSoft.clusteredDataWarehouse.service.FxDealService;
import com.progressSoft.clusteredDataWarehouse.service.mapper.FxDealMapper;
import com.progressSoft.clusteredDataWarehouse.service.validator.FxDealValidator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Data
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository fxDealRepository;

    private final FxDealMapper fxMapper;

    private final FxDealValidator fxDealValidator;

    @Override
    public ForexDealsResponse saveFxDeal(ForexDealsRequest forexDealsRequest) {

        fxDealValidator.isISOCurrencyCodeValid(forexDealsRequest.getFromCurrencyISOCode());
        fxDealValidator.isISOCurrencyCodeValid(forexDealsRequest.getToCurrencyISOCode());
        fxDealValidator.validateFromCurrencyAndToCurrency(forexDealsRequest.getFromCurrencyISOCode(), forexDealsRequest.getToCurrencyISOCode());
        fxDealValidator.validateDealAmount(forexDealsRequest.getDealAmount());
        fxDealValidator.isDealUnique(forexDealsRequest.getDealUniqueId());

        ForexDeals forexDeals = fxMapper.MapDtoRequestToFxDeal(forexDealsRequest);
        fxDealRepository.save(forexDeals);

        ForexDealsResponse responseDTO = fxMapper.mapFxDealToDTORequest(forexDeals);
        log.info("FxDeal saved successfully");

        return responseDTO;
    }

    @Override
    public ForexDealsResponse getDealByUniqueId(String dealUniqueId) {
        ForexDeals forexDeals = fxDealRepository.findByDealUniqueId(dealUniqueId)
                .orElseThrow(() -> new ForexDealEntityNotFoundException("Deal with id " + dealUniqueId + " does not exist"));
        return fxMapper.mapFxDealToDTORequest(forexDeals);
    }


    @Override
    public PaginationResponse getAllFxDeals(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ForexDeals> fxDealList = fxDealRepository.findAll(pageable);
        log.info("FxDeals successfully retrieved");

        List<ForexDealsResponse> collect = fxDealList.stream()
                .map(fxMapper::mapFxDealToDTORequest)
                .collect(Collectors.toList());
        return PaginationResponse.builder()
                .contents(collect)
                .pageNumber(fxDealList.getNumberOfElements())
                .pageSize(fxDealList.getSize())
                .build();
    }
}

