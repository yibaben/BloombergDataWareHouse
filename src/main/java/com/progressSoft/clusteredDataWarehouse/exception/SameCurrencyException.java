package com.progressSoft.clusteredDataWarehouse.exception;

public class SameCurrencyException extends RuntimeException {
    public SameCurrencyException(String message) {
        super(message);
    }
}
