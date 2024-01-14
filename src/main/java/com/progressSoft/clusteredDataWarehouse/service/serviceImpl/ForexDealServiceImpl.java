package com.progressSoft.clusteredDataWarehouse.service.serviceImpl;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.dto.responses.PaginationResponse;
import com.progressSoft.clusteredDataWarehouse.exception.*;
import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
import com.progressSoft.clusteredDataWarehouse.repository.ForexDealRepository;
import com.progressSoft.clusteredDataWarehouse.service.ForexDealService;
import com.progressSoft.clusteredDataWarehouse.service.mapper.ForexDealMapper;
import com.progressSoft.clusteredDataWarehouse.service.validator.ForexDealValidations;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForexDealServiceImpl implements ForexDealService {

    private final ForexDealRepository forexDealRepository;
    private final ForexDealMapper forexDealMapper;
    private final ForexDealValidations forexDealValidations;

    @Override
    public ForexDealsResponse createAndSaveForexDeal(ForexDealsRequest forexDealsRequest) {
        try {
            // Validate inputs
            forexDealValidations.isCurrencyISOCodeValid(forexDealsRequest.getFromCurrencyISOCode());
            forexDealValidations.isCurrencyISOCodeValid(forexDealsRequest.getToCurrencyISOCode());
            forexDealValidations.validateFromCurrencyAndToCurrency(forexDealsRequest.getFromCurrencyISOCode(), forexDealsRequest.getToCurrencyISOCode());
            forexDealValidations.dealAmountValidation(forexDealsRequest.getDealAmount());
            forexDealValidations.isDealUniqueId(forexDealsRequest.getDealUniqueId());

            // Map and save ForexDeals entity
            ForexDeals forexDeals = forexDealMapper.MapForexDealRequestToForexDealEntity(forexDealsRequest);
            forexDealRepository.save(forexDeals);

            // Map and return ForexDealsResponse
            ForexDealsResponse responseDTO = forexDealMapper.mapForexDealEntityToForexDealResponse(forexDeals);
            log.info("FxDeal saved successfully");

            return responseDTO;
        } catch (EntityAlreadyExistsException | ISOCodeValidationException | DuplicateCurrencyException |
                 AmountValidationException ex) {
            // Log the specific exception details
            log.error("Error while creating and saving ForexDeal: {}", ex.getMessage());
            throw ex; // Re-throw the exception for higher-level error
        }
    }

    @Override
    public ForexDealsResponse getForexDealByUniqueId(String dealUniqueId) {
        try {
            // Retrieve ForexDeals entity by dealUniqueId
            ForexDeals forexDeals = forexDealRepository.findForexDealByDealUniqueId(dealUniqueId)
                    .orElseThrow(() -> new ForexDealEntityNotFoundException("Deal with id " + dealUniqueId + " does not exist"));

            // Map and return ForexDealsResponse
            return forexDealMapper.mapForexDealEntityToForexDealResponse(forexDeals);
        } catch (ForexDealEntityNotFoundException ex) {
            // Log the specific exception details
            log.error("Error while retrieving ForexDeal: {}", ex.getMessage());
            throw ex; // Re-throw the exception for higher-level error
        }
    }

    @Override
    public PaginationResponse getAllForexDealsWithPagination(int pageNo, int pageSize) {
        try {
            // Retrieve a page of ForexDeals entities
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<ForexDeals> fxDealList = forexDealRepository.findAll(pageable);
            log.info("ForexDeals successfully retrieved with Pagination");

            // Map and return PaginationResponse
            List<ForexDealsResponse> collect = fxDealList.stream()
                    .map(forexDealMapper::mapForexDealEntityToForexDealResponse)
                    .collect(Collectors.toList());

            return PaginationResponse.builder()
                    .contents(collect)
                    .pageNumber(fxDealList.getNumberOfElements())
                    .pageSize(fxDealList.getSize())
                    .build();
        } catch (RuntimeException ex) {
            // Log the exception details
            log.error("Error while retrieving ForexDeals with Pagination: {}", ex.getMessage());
            throw ex; // Re-throw the exception for higher-level error
        }
    }
}

