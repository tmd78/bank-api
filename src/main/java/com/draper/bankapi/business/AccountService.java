package com.draper.bankapi.business;

import com.draper.bankapi.common.BankApiConflictException;
import com.draper.bankapi.common.TransactionAction;
import com.draper.bankapi.controller.account.OpenNewAccountRequest;
import com.draper.bankapi.controller.account.TransactionResponse;
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
     * Withdraw the requested amount from the specified account.
     *
     * @param accountId      the ID of the account to withdraw from
     * @param withdrawAmount the amount to withdraw
     * @return the resulting balance
     */
    public TransactionResponse withdrawFromAccount(int accountId, int withdrawAmount) {
        Account accountUnaltered = accountRepository.readAccount(accountId);

        int oldBalance = accountUnaltered.getBalance();
        int newBalance = oldBalance - withdrawAmount;

        if (newBalance < 0) {
            String message = String.format("balance ($%d) is too low for requested withdraw amount", oldBalance);
            throw new BankApiConflictException(message);
        }

        Account accountAltered = accountRepository.updateAccountBalance(accountId, newBalance);

        Transaction transaction = transactionRepository.createTransaction(
                accountId,
                TransactionAction.WITHDRAW,
                withdrawAmount,
                null
        );

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccountId(accountAltered.getId());
        transactionResponse.setBalance(accountAltered.getBalance());
        transactionResponse.setTransactionId(transaction.getId());

        return transactionResponse;
    }
}
