package com.draper.bankapi.business;

import com.draper.bankapi.common.BankApiBadRequestException;
import com.draper.bankapi.common.Constants;
import com.draper.bankapi.message.DeleteAccountsRequest;

public class ValidateDeleteAccountsRequest {
    private ValidateDeleteAccountsRequest() throws Exception {
        String message = String.format(Constants.MSG_DO_NOT_INSTANTIATE, ValidateDeleteAccountsRequest.class.getName());
        throw new Exception(message);
    }

    /**
     * Ensure all values expected of a request are present.
     *
     * @param request the request to validate
     */
    public static void perform(DeleteAccountsRequest request) {
        if (request == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "body"));
        }

        if (request.getAccountIds() == null) {
            throw new BankApiBadRequestException(String.format(Constants.MSG_MISSING_JSON_VALUE, "accountIds"));
        }
    }
}
