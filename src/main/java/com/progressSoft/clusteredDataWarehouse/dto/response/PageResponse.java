package com.progressSoft.clusteredDataWarehouse.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class PageResponse {

    private List<FxDealResponseDTO> contents;
    private int pageNumber;
    private int pageSize;
}
