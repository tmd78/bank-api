package com.draper.bankapi.business;

import com.draper.bankapi.controller.OpenNewAccountRequest;
import com.draper.bankapi.data.account.AccountRepository;
import com.draper.bankapi.data.account.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account openNewAccount(OpenNewAccountRequest openNewAccountRequest) {
        Account account = new Account();
        account.setBalance(0);
        account.setPasscode(openNewAccountRequest.getPasscode());

        return accountRepository.createAccount(account);
    }
}
