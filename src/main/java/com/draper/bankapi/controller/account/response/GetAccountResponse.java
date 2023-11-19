package com.draper.bankapi.controller.account.response;

/**
 * Response to client's request for an account's information.
 */
public class GetAccountResponse {
    private int id;
    private int balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
