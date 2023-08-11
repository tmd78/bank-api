package com.draper.bankapi.common;

/**
 * An exception that indicates a bad request was received from the API caller.
 */
public class BankApiBadRequestException extends RuntimeException {
    public BankApiBadRequestException(String message) {
        super(message);
    }
}
