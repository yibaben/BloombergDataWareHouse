package com.progressSoft.clusteredDataWarehouse.service;

import com.progressSoft.clusteredDataWarehouse.dto.request.FxDealRequestDTO;
import com.progressSoft.clusteredDataWarehouse.dto.response.FxDealResponseDTO;
import com.progressSoft.clusteredDataWarehouse.dto.response.PageResponse;

import java.util.List;

public interface FxDealService {
    FxDealResponseDTO saveFxDeal(FxDealRequestDTO fxDealRequestDTO);
    FxDealResponseDTO getDealByUniqueId(String dealUniqueId);
    PageResponse getAllFxDeals(int pageNo, int pageSize);

}

