package com.progressSoft.clusteredDataWarehouse.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class PaginationResponse {
    private List<ForexDealsResponse> contents;
    private int pageNumber;
    private int pageSize;
}
