package com.draper.bankapi.controller.account;

/**
 * Response sent to the client after a successful account update.
 */
public class TransactionResponse {
    private int accountId;
    private int balance;
    private int transactionId;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
