package com.draper.bankapi.business.service;

import com.draper.bankapi.common.BankApiConflictException;
import com.draper.bankapi.common.TransactionType;
import com.draper.bankapi.controller.account.request.CreateAccountRequest;
import com.draper.bankapi.controller.account.response.ReadAccountResponse;
import com.draper.bankapi.controller.account.response.UpdateAccountBalanceResponse;
import com.draper.bankapi.data.account.AccountRepository;
import com.draper.bankapi.data.account.Account;
import com.draper.bankapi.data.transaction.Transaction;
import com.draper.bankapi.data.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
     * Read the account with the specified ID.
     *
     * @param accountId the ID of the account to read
     * @return the requested account
     */
    public ReadAccountResponse readAccount(int accountId) {
        Account account = accountRepository.readAccount(accountId);

        ReadAccountResponse response = new ReadAccountResponse();
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
            Account account = accountRepository.readAccount(accountId);
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
            Account account = accountRepository.readAccount(accountId);
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
            accountRepository.readAccount(accountId);
            accountRepository.deleteAccount(accountId);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Delete the specified accounts.
     *
     * @param accountIds the IDs of the accounts to delete
     * @return the number of accounts deleted
     */
    public long deleteAccounts(List<Integer> accountIds) {
        int[] results;

        try {
            lock.lock();
            results = accountRepository.deleteAccounts(accountIds);
        } finally {
            lock.unlock();
        }

        return Arrays.stream(results).filter(x -> x > 0).count();
    }
}
