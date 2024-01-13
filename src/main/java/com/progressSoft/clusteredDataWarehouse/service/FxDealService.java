package com.progressSoft.clusteredDataWarehouse.service;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.dto.responses.PaginationResponse;

public interface FxDealService {
    ForexDealsResponse saveFxDeal(ForexDealsRequest forexDealsRequest);
    ForexDealsResponse getDealByUniqueId(String dealUniqueId);
    PaginationResponse getAllFxDeals(int pageNo, int pageSize);

}

