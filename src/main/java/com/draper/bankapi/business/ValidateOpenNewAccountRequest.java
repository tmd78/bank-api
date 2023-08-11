package com.draper.bankapi.business;

import com.draper.bankapi.controller.OpenNewAccountRequest;
import com.draper.bankapi.common.BankApiBadRequestException;

import java.util.regex.Pattern;

public final class ValidateOpenNewAccountRequest {
    private ValidateOpenNewAccountRequest() throws Exception {
        String message = String.format("%s should not be instantiated", ValidateOpenNewAccountRequest.class.getName());
        throw new Exception(message);
    }

    public static void perform(OpenNewAccountRequest request) {
        if (request.getPasscode() == null) {
            throw new BankApiBadRequestException(String.format("%s must be provided", "passcode"));
        }

        if (!Pattern.matches("\\d{4}", request.getPasscode())) {
            throw new BankApiBadRequestException("passcode must be 4 digits");
        }
    }
}
