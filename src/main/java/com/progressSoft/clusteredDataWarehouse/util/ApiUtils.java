package com.progressSoft.clusteredDataWarehouse.util;

import com.progressSoft.clusteredDataWarehouse.dto.responses.ApiResponse;
import org.springframework.http.HttpStatus;

public class ApiUtils {

    public static ApiResponse buildApiResponse(Object d, HttpStatus status){
        return ApiResponse.builder()
                .message("successful")
                .data(d)
                .status(status)
                .build();
    }

    public static ApiResponse buildErrorResponse(Object d, HttpStatus status) {
        return ApiResponse.builder()
                .message("error")
                .status(status)
                .data(d)
                .build();
    }
}
