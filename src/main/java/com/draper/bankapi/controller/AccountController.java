package com.draper.bankapi.controller;

import com.draper.bankapi.business.AccountService;
import com.draper.bankapi.business.ValidateOpenNewAccountRequest;
import com.draper.bankapi.data.account.Account;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account openNewAccount(@RequestBody OpenNewAccountRequest openNewAccountRequest) {
        ValidateOpenNewAccountRequest.perform(openNewAccountRequest);

        return accountService.openNewAccount(openNewAccountRequest);
    }
}
