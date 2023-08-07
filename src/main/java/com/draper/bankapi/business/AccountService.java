package com.draper.bankapi.business;

import com.draper.bankapi.data.AccountRepository;
import com.draper.bankapi.model.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account openNewAccount() {
        // Build a new Account object.
        Account account = new Account();
        account.setBalance(0);
        // TODO: Accept passcode from caller and verify the passcode.
        account.setPasscode("0000");

        return accountRepository.createAccount(account);
    }
}
