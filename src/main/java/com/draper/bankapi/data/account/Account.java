package com.draper.bankapi.data.account;

/**
 * A data layer model. Represents the {@code account} table.
 */
public class Account {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BALANCE = "balance";
    public static final String COLUMN_PASSCODE = "passcode";
    public static final String READ_BY_ID = "select * from account where id = :id";
    public static final String UPDATE_BALANCE = "update account set balance = :balance where id = :id";
    public static final String DELETE_BY_ID = "delete from account where id = :id";

    private int id;
    private int balance;
    private String passcode;

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

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
