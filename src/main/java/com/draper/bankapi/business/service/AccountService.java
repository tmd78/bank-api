package com.draper.bankapi.business.service;

import com.draper.bankapi.common.BankApiConflictException;
import com.draper.bankapi.common.BankApiNotFoundException;
import com.draper.bankapi.common.TransactionType;
import com.draper.bankapi.controller.account.request.CreateAccountRequest;
import com.draper.bankapi.controller.account.response.GetAccountResponse;
import com.draper.bankapi.controller.account.response.UpdateAccountBalanceResponse;
import com.draper.bankapi.data.account.AccountRepository;
import com.draper.bankapi.data.account.Account;
import com.draper.bankapi.data.transaction.Transaction;
import com.draper.bankapi.data.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final Lock lock;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.lock = new ReentrantLock();
    }

    /**
     * Create an account using the provided information.
     *
     * @param createAccountRequest the information needed to create account
     * @return the created account
     */
    public Account createAccount(CreateAccountRequest createAccountRequest) {
        return accountRepository.createAccount(0, createAccountRequest.getPasscode());
    }

    /**
     * Retrieve the specified account.
     *
     * @param accountId the ID of the account to retrieve
     * @return the specified account
     */
    public GetAccountResponse getAccount(int accountId) {
        Account account = readAccount(accountId);

        GetAccountResponse response = new GetAccountResponse();
        response.setId(account.getId());
        response.setBalance(account.getBalance());

        return response;
    }

    /**
     * Deposit into the specified account.
     *
     * @param accountId the ID of the account to deposit into
     * @param amount    the amount to deposit
     * @return the result of the transaction
     */
    public UpdateAccountBalanceResponse depositIntoAccount(int accountId, int amount) {
        int balance;

        try {
            lock.lock();
            Account account = readAccount(accountId);
            balance = account.getBalance();
            balance = balance + amount;
            accountRepository.updateAccountBalance(accountId, balance);
        } finally {
            lock.unlock();
        }

        Transaction transaction = transactionRepository.createTransaction(
                accountId,
                TransactionType.DEPOSIT,
                amount
        );

        UpdateAccountBalanceResponse updateAccountBalanceResponse = new UpdateAccountBalanceResponse();
        updateAccountBalanceResponse.setAccountId(accountId);
        updateAccountBalanceResponse.setBalance(balance);
        updateAccountBalanceResponse.setTransactionId(transaction.getId());

        return updateAccountBalanceResponse;
    }

    /**
     * Withdraw from the specified account.
     *
     * @param accountId the ID of the account to withdraw from
     * @param amount    the amount to withdraw
     * @return the result of the transaction
     */
    public UpdateAccountBalanceResponse withdrawFromAccount(int accountId, int amount) {
        int balance;

        try {
            lock.lock();
            Account account = readAccount(accountId);
            balance = account.getBalance();
            balance = balance - amount;
            if (balance < 0) {
                String message = String.format("balance ($%d) is too low for requested withdraw amount", balance);
                throw new BankApiConflictException(message);
            }
            accountRepository.updateAccountBalance(accountId, balance);
        } finally {
            lock.unlock();
        }

        Transaction transaction = transactionRepository.createTransaction(
                accountId,
                TransactionType.WITHDRAW,
                amount
        );

        UpdateAccountBalanceResponse updateAccountBalanceResponse = new UpdateAccountBalanceResponse();
        updateAccountBalanceResponse.setAccountId(accountId);
        updateAccountBalanceResponse.setBalance(balance);
        updateAccountBalanceResponse.setTransactionId(transaction.getId());

        return updateAccountBalanceResponse;
    }

    /**
     * Delete the specified account.
     *
     * @param accountId the ID of the account to delete
     */
    public void deleteAccount(int accountId) {
        try {
            lock.lock();
            readAccount(accountId);
            accountRepository.deleteAccount(accountId);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Read the specified account.
     *
     * @param accountId the ID of the account to read
     * @return the specified account
     */
    private Account readAccount(int accountId) {
        Account account = accountRepository.readAccount(accountId);
        if (account == null) {
            throw new BankApiNotFoundException(String.format("could not find account with ID %d", accountId));
        }
        return account;
    }
}
