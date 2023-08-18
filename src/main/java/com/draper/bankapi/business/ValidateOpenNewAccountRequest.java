package com.draper.bankapi.business;

import com.draper.bankapi.common.Constants;
import com.draper.bankapi.controller.account.OpenNewAccountRequest;
import com.draper.bankapi.common.BankApiBadRequestException;

import java.util.regex.Pattern;

public abstract class ValidateOpenNewAccountRequest {
    private ValidateOpenNewAccountRequest() throws Exception {
        String message = String.format("%s should not be instantiated", ValidateOpenNewAccountRequest.class.getName());
        throw new Exception(message);
    }

    public static void perform(OpenNewAccountRequest request) {
        if (request == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "body"));
        }

        if (request.getPasscode() == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "passcode"));
        }

        if (!Pattern.matches("\\d{4}", request.getPasscode())) {
            throw new BankApiBadRequestException("passcode must be 4 digits");
        }
    }
}