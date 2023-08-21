package com.draper.bankapi.business;

import com.draper.bankapi.common.TransactionAction;
import com.draper.bankapi.common.BankApiBadRequestException;
import com.draper.bankapi.common.Constants;
import com.draper.bankapi.controller.account.TransactionRequest;

import java.util.regex.Pattern;

public abstract class ValidateTransactionRequest {
    private ValidateTransactionRequest() throws Exception {
        String message = String.format(Constants.MSG_DO_NOT_INSTANTIATE, ValidateTransactionRequest.class.getName());
        throw new Exception(message);
    }

    public static void perform(TransactionRequest request) {
        if (request == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "body"));
        }

        if (request.getAction() == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "action"));
        }

        try {
            TransactionAction.valueOf(request.getAction().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BankApiBadRequestException("action must be 'deposit' or 'withdraw' (case insensitive)");
        }

        if (request.getAmount() == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "amount"));
        }

        if (request.getAmount() < 1) {
            throw new BankApiBadRequestException("amount must be a positive number (amount > 0)");
        }

        if (request.getPasscode() == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "passcode"));
        }

        if (!Pattern.matches("\\d{4}", request.getPasscode())) {
            throw new BankApiBadRequestException("passcode must be 4 digits");
        }
    }
}
