package com.draper.bankapi.common;

/**
 * Indicates that a requested resource cannot be found.
 */
public class BankApiNotFoundException extends RuntimeException {
    public BankApiNotFoundException(String message) {
        super(message);
    }
}
