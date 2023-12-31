package com.draper.bankapi.controller;

import com.draper.bankapi.common.BankApiBadRequestException;
import com.draper.bankapi.common.BankApiConflictException;
import com.draper.bankapi.common.BankApiNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(BankApiBadRequestException.class)
    public ErrorResponse handle(BankApiBadRequestException e) {
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BankApiConflictException.class)
    public ErrorResponse handle(BankApiConflictException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(BankApiNotFoundException.class)
    public ErrorResponse handle(BankApiNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }
}
