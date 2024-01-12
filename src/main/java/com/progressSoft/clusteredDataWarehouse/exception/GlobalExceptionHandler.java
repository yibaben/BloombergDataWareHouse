package com.progressSoft.clusteredDataWarehouse.exception;

import com.progressSoft.clusteredDataWarehouse.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.progressSoft.clusteredDataWarehouse.util.ApiUtils.buildErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            if (errors.containsKey(error.getField()))
                errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
            else
                errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(new ApiResponse("Error saving deal", HttpStatus.BAD_REQUEST, LocalDateTime.now(), errors),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FxDealNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFxDealNotFoundException(FxDealNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(InvalidISOCodeException.class)
    public ResponseEntity<ApiResponse> handleInvalidISOCodeException(InvalidISOCodeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(SameCurrencyException.class)
    public ResponseEntity<ApiResponse> handleSameCurrencyException(SameCurrencyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiResponse> handleInvalidAmountException(InvalidAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ApiResponse>handleDuplicateEntityException(DuplicateEntityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
}

