package com.draper.bankapi.message;

import java.util.List;

public class DeleteAccountsRequest {
    private List<Integer> accountIds;

    public List<Integer> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(List<Integer> accountIds) {
        this.accountIds = accountIds;
    }
}
