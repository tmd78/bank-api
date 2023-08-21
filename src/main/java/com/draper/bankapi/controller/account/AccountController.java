package com.draper.bankapi.controller.account;

import com.draper.bankapi.business.AccountService;
import com.draper.bankapi.business.ValidateOpenNewAccountRequest;
import com.draper.bankapi.business.ValidateTransactionRequest;
import com.draper.bankapi.common.TransactionAction;
import com.draper.bankapi.common.BankApiBadRequestException;
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
    public TransactionResponse transact(@PathVariable int accountId, @RequestBody TransactionRequest transactionRequest) {
        ValidateTransactionRequest.perform(transactionRequest);

        TransactionAction transactionAction = TransactionAction.valueOf(transactionRequest.getAction().toUpperCase());
        TransactionResponse transactionResponse;

        switch (transactionAction) {
            case DEPOSIT:
                transactionResponse = accountService.depositIntoAccount(accountId, transactionRequest.getAmount());
                break;
            case WITHDRAW:
                transactionResponse = accountService.withdrawFromAccount(accountId, transactionRequest.getAmount());
                break;
            default:
                throw new BankApiBadRequestException(String.format("%s is not a recognized action", transactionAction));
        }

        return transactionResponse;
    }
}
