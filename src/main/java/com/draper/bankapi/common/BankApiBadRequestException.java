package com.draper.bankapi.common;

/**
 * Indicates that a bad request was received from the client.
 */
public class BankApiBadRequestException extends RuntimeException {
    public BankApiBadRequestException(String message) {
        super(message);
    }
}
