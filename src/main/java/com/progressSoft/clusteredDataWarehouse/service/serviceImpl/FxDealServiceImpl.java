package com.progressSoft.clusteredDataWarehouse.service.serviceImpl;

import com.progressSoft.clusteredDataWarehouse.dto.request.FxDealRequestDTO;
import com.progressSoft.clusteredDataWarehouse.dto.response.FxDealResponseDTO;
import com.progressSoft.clusteredDataWarehouse.dto.response.PageResponse;
import com.progressSoft.clusteredDataWarehouse.exception.FxDealNotFoundException;
import com.progressSoft.clusteredDataWarehouse.model.FxDeal;
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
    public FxDealResponseDTO saveFxDeal(FxDealRequestDTO fxDealRequestDTO) {

        fxDealValidator.isISOCurrencyCodeValid(fxDealRequestDTO.getFromCurrencyISOCode());
        fxDealValidator.isISOCurrencyCodeValid(fxDealRequestDTO.getToCurrencyISOCode());
        fxDealValidator.validateFromCurrencyAndToCurrency(fxDealRequestDTO.getFromCurrencyISOCode(), fxDealRequestDTO.getToCurrencyISOCode());
        fxDealValidator.validateDealAmount(fxDealRequestDTO.getDealAmount());
        fxDealValidator.isDealUnique(fxDealRequestDTO.getDealUniqueId());

        FxDeal fxDeal = fxMapper.MapDtoRequestToFxDeal(fxDealRequestDTO);
        fxDealRepository.save(fxDeal);

        FxDealResponseDTO responseDTO = fxMapper.mapFxDealToDTORequest(fxDeal);
        log.info("FxDeal saved successfully");

        return responseDTO;
    }

    @Override
    public FxDealResponseDTO getDealByUniqueId(String dealUniqueId) {
        FxDeal fxDeal = fxDealRepository.findByDealUniqueId(dealUniqueId)
                .orElseThrow(() -> new FxDealNotFoundException("Deal with id " + dealUniqueId + " does not exist"));
        return fxMapper.mapFxDealToDTORequest(fxDeal);
    }


    @Override
    public PageResponse getAllFxDeals(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<FxDeal> fxDealList = fxDealRepository.findAll(pageable);
        log.info("FxDeals successfully retrieved");

        List<FxDealResponseDTO> collect = fxDealList.stream()
                .map(fxMapper::mapFxDealToDTORequest)
                .collect(Collectors.toList());
        return PageResponse.builder()
                .contents(collect)
                .pageNumber(fxDealList.getNumberOfElements())
                .pageSize(fxDealList.getSize())
                .build();
    }
}

