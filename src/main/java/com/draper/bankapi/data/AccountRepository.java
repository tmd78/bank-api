package com.draper.bankapi.data;

import com.draper.bankapi.model.Account;

public interface AccountRepository {
    Account createAccount(Account account);
}
