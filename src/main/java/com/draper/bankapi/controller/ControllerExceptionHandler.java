package com.draper.bankapi.controller;

import com.draper.bankapi.common.BankApiBadRequestException;
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
}
