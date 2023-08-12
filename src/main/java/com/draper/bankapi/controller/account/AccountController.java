package com.draper.bankapi.controller.account;

import com.draper.bankapi.business.AccountService;
import com.draper.bankapi.business.ValidateOpenNewAccountRequest;
import com.draper.bankapi.business.ValidateTransactionRequest;
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

    @PutMapping(path = "/{accountId}")
    public Account transact(@PathVariable Integer accountId, @RequestBody TransactionRequest transactionRequest) {
        ValidateTransactionRequest.perform(transactionRequest);

        // TODO: Use service to carry out transaction.
        return new Account();
    }
}
