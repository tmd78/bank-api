package com.draper.bankapi.common;

/**
 * An exception that indicates an update request conflicts with the target resource's state.
 */
public class BankApiConflictException extends RuntimeException {
    public BankApiConflictException(String message) {
        super(message);
    }
}
