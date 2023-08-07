package com.draper.bankapi.controller;

import com.draper.bankapi.business.AccountService;
import com.draper.bankapi.model.Account;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Account openNewAccount() {
        return accountService.openNewAccount();
    }
}
