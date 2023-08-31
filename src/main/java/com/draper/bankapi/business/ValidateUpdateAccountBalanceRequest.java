package com.draper.bankapi.business;

import com.draper.bankapi.common.TransactionType;
import com.draper.bankapi.common.BankApiBadRequestException;
import com.draper.bankapi.common.Constants;
import com.draper.bankapi.controller.account.request.UpdateAccountBalanceRequest;

import java.util.regex.Pattern;

public abstract class ValidateUpdateAccountBalanceRequest {
    private ValidateUpdateAccountBalanceRequest() throws Exception {
        String message = String.format(Constants.MSG_DO_NOT_INSTANTIATE, ValidateUpdateAccountBalanceRequest.class.getName());
        throw new Exception(message);
    }

    /**
     * Ensure all values expected of a request are present.
     *
     * @param request the request to validate
     */
    public static void perform(UpdateAccountBalanceRequest request) {
        if (request == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "body"));
        }

        if (request.getAction() == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "action"));
        }

        try {
            TransactionType.valueOf(request.getAction().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BankApiBadRequestException("action must be 'deposit' or 'withdraw' (case insensitive)");
        }

        if (request.getAmount() == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "amount"));
        }

        if (request.getAmount() < 1) {
            throw new BankApiBadRequestException("amount must be greater than zero");
        }

        if (request.getPasscode() == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "passcode"));
        }

        if (!Pattern.matches("\\d{4}", request.getPasscode())) {
            throw new BankApiBadRequestException("passcode must be 4 digits");
        }
    }
}
