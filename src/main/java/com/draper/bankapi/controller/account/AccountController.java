package com.draper.bankapi.controller.account;

import com.draper.bankapi.business.service.AccountService;
import com.draper.bankapi.business.ValidateCreateAccountRequest;
import com.draper.bankapi.business.ValidateUpdateAccountBalanceRequest;
import com.draper.bankapi.common.TransactionType;
import com.draper.bankapi.common.BankApiBadRequestException;
import com.draper.bankapi.controller.account.request.CreateAccountRequest;
import com.draper.bankapi.controller.account.request.UpdateAccountBalanceRequest;
import com.draper.bankapi.controller.account.response.ReadAccountResponse;
import com.draper.bankapi.controller.account.response.UpdateAccountBalanceResponse;
import com.draper.bankapi.data.account.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        ValidateCreateAccountRequest.perform(createAccountRequest);

        return accountService.createAccount(createAccountRequest);
    }

    @GetMapping(path = "/{accountId}")
    public ReadAccountResponse readAccount(@PathVariable int accountId) {
        return accountService.readAccount(accountId);
    }

    @PutMapping(path = "/{accountId}")
    public UpdateAccountBalanceResponse updateAccountBalance(
            @PathVariable int accountId,
            @RequestBody UpdateAccountBalanceRequest updateAccountBalanceRequest
    ) {

        ValidateUpdateAccountBalanceRequest.perform(updateAccountBalanceRequest);

        TransactionType transactionType = TransactionType.valueOf(updateAccountBalanceRequest.getAction().toUpperCase());
        UpdateAccountBalanceResponse updateAccountBalanceResponse;

        switch (transactionType) {
            case DEPOSIT:
                updateAccountBalanceResponse = accountService.depositIntoAccount(accountId, updateAccountBalanceRequest.getAmount());
                break;
            case WITHDRAW:
                updateAccountBalanceResponse = accountService.withdrawFromAccount(accountId, updateAccountBalanceRequest.getAmount());
                break;
            default:
                throw new BankApiBadRequestException(String.format("%s is not a recognized action", transactionType));
        }

        return updateAccountBalanceResponse;
    }

    @DeleteMapping(path = "/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable int accountId) {
        accountService.deleteAccount(accountId);
    }
}
