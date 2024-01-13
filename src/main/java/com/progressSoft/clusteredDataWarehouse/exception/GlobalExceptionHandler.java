package com.progressSoft.clusteredDataWarehouse.exception;

import com.progressSoft.clusteredDataWarehouse.dto.responses.ApiResponse;
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
import java.util.Objects;

import static com.progressSoft.clusteredDataWarehouse.util.ApiUtils.buildErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handling validation exceptions for method arguments
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        // Collecting field errors
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.merge(error.getField(), Objects.requireNonNull(error.getDefaultMessage()), (existing, additional) -> String.format("%s, %s", existing, additional));
        });

        // Building and returning error response
        return new ResponseEntity<>(new ApiResponse("Error saving deal", HttpStatus.BAD_REQUEST, LocalDateTime.now(), errors),
                HttpStatus.BAD_REQUEST);
    }

    // Handling AmountValidationException
    @ExceptionHandler(AmountValidationException.class)
    public ResponseEntity<ApiResponse> handleInvalidAmountException(AmountValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Handling DuplicateCurrencyException
    @ExceptionHandler(DuplicateCurrencyException.class)
    public ResponseEntity<ApiResponse> handleSameCurrencyException(DuplicateCurrencyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Handling EntityAlreadyExistsException
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleDuplicateEntityException(EntityAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Handling ForexDealEntityNotFoundException
    @ExceptionHandler(ForexDealEntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFxDealNotFoundException(ForexDealEntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Handling ISOCodeValidationException
    @ExceptionHandler(ISOCodeValidationException.class)
    public ResponseEntity<ApiResponse> handleInvalidISOCodeException(ISOCodeValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
