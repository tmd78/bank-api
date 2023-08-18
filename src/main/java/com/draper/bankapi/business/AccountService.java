package com.draper.bankapi.business;

import com.draper.bankapi.common.BankApiConflictException;
import com.draper.bankapi.controller.account.OpenNewAccountRequest;
import com.draper.bankapi.controller.account.TransactionResponse;
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
        return accountRepository.createAccount(0, openNewAccountRequest.getPasscode());
    }

    public TransactionResponse withdrawFromAccount(int accountId, int withdrawAmount) {
        Account accountUnaltered = accountRepository.readAccount(accountId);

        int oldBalance = accountUnaltered.getBalance();
        int newBalance = oldBalance - withdrawAmount;

        if (newBalance < 0) {
            String message = String.format("balance ($%d) is insufficient for requested withdraw amount", oldBalance);
            throw new BankApiConflictException(message);
        }

        Account accountAltered = accountRepository.updateAccountBalance(accountId, newBalance);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccountId(accountAltered.getId());
        transactionResponse.setBalance(accountAltered.getBalance());

        return transactionResponse;
    }
}
