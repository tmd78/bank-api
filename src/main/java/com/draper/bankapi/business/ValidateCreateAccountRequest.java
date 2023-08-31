package com.draper.bankapi.business;

import com.draper.bankapi.common.Constants;
import com.draper.bankapi.controller.account.request.CreateAccountRequest;
import com.draper.bankapi.common.BankApiBadRequestException;

import java.util.regex.Pattern;

public abstract class ValidateCreateAccountRequest {
    private ValidateCreateAccountRequest() throws Exception {
        String message = String.format(Constants.MSG_DO_NOT_INSTANTIATE, ValidateCreateAccountRequest.class.getName());
        throw new Exception(message);
    }

    /**
     * Ensure all values expected of a request are present.
     *
     * @param request the request to validate
     */
    public static void perform(CreateAccountRequest request) {
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
