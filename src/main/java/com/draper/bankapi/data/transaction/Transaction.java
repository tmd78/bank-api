package com.draper.bankapi.data.transaction;

import com.draper.bankapi.common.TransactionAction;

/**
 * A data layer model. Represents the {@code transaction} table.
 */
public class Transaction {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ACCOUNT_ID = "account_id";
    public static final String COLUMN_ACTION = "action";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_MEMO = "memo";
    public static final String READ_BY_ID = "select * from transaction where id = :id";

    private int id;
    private int accountId;
    private TransactionAction action;
    private int amount;
    private String memo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public TransactionAction getAction() {
        return action;
    }

    public void setAction(TransactionAction action) {
        this.action = action;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Optional in the {@code transaction} table.
     * Can be {@code null} after mapping from a query result.
     *
     * @return the memo for this transaction
     */
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
