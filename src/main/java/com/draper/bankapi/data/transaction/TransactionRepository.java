package com.draper.bankapi.data.transaction;

import com.draper.bankapi.common.TransactionType;

public interface TransactionRepository {
    /**
     * Create a record in the {@code transaction} table.
     *
     * @param accountId the ID of the modified account
     * @param action    the action performed on the account
     * @param amount    the amount of the action
     * @return the newly created transaction
     */
    Transaction createTransaction(int accountId, TransactionType action, int amount);

    /**
     * Read a record from the {@code transaction} table by ID.
     *
     * @param id the ID of the record to read
     * @return the requested transaction
     */
    Transaction readTransaction(int id);
}
