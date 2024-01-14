package com.progressSoft.clusteredDataWarehouse.service;

import com.progressSoft.clusteredDataWarehouse.dto.request.ForexDealsRequest;
import com.progressSoft.clusteredDataWarehouse.dto.responses.ForexDealsResponse;
import com.progressSoft.clusteredDataWarehouse.dto.responses.PaginationResponse;

public interface ForexDealService {
    ForexDealsResponse createAndSaveForexDeal(ForexDealsRequest forexDealsRequest);
    ForexDealsResponse getForexDealByUniqueId(String dealUniqueId);
    PaginationResponse getAllForexDealsWithPagination(int pageNo, int pageSize);

}

