package com.draper.bankapi.business.service;

import com.draper.bankapi.common.BankApiConflictException;
import com.draper.bankapi.common.TransactionAction;
import com.draper.bankapi.controller.account.request.OpenNewAccountRequest;
import com.draper.bankapi.controller.account.response.TransactionResponse;
import com.draper.bankapi.data.account.AccountRepository;
import com.draper.bankapi.data.account.Account;
import com.draper.bankapi.data.transaction.Transaction;
import com.draper.bankapi.data.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account openNewAccount(OpenNewAccountRequest openNewAccountRequest) {
        return accountRepository.createAccount(0, openNewAccountRequest.getPasscode());
    }

    /**
     * Deposit into the specified account.
     *
     * @param accountId the ID of the account to deposit into
     * @param amount    the amount to deposit
     * @return the result of the transaction
     */
    public TransactionResponse depositIntoAccount(int accountId, int amount) {
        Account account = accountRepository.readAccount(accountId);

        int oldBalance = account.getBalance();
        int newBalance = oldBalance + amount;

        accountRepository.updateAccountBalance(accountId, newBalance);

        Transaction transaction = transactionRepository.createTransaction(
                accountId,
                TransactionAction.DEPOSIT,
                amount
        );

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccountId(accountId);
        transactionResponse.setBalance(newBalance);
        transactionResponse.setTransactionId(transaction.getId());

        return transactionResponse;
    }

    /**
     * Withdraw from the specified account.
     *
     * @param accountId the ID of the account to withdraw from
     * @param amount    the amount to withdraw
     * @return the result of the transaction
     */
    public TransactionResponse withdrawFromAccount(int accountId, int amount) {
        Account account = accountRepository.readAccount(accountId);

        int oldBalance = account.getBalance();
        int newBalance = oldBalance - amount;

        if (newBalance < 0) {
            String message = String.format("balance ($%d) is too low for requested withdraw amount", oldBalance);
            throw new BankApiConflictException(message);
        }

        accountRepository.updateAccountBalance(accountId, newBalance);

        Transaction transaction = transactionRepository.createTransaction(
                accountId,
                TransactionAction.WITHDRAW,
                amount
        );

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccountId(accountId);
        transactionResponse.setBalance(newBalance);
        transactionResponse.setTransactionId(transaction.getId());

        return transactionResponse;
    }
}
