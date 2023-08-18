package com.draper.bankapi.data.transaction;

import com.draper.bankapi.common.TransactionAction;

public interface TransactionRepository {
    /**
     * Create a record in the {@code transaction} table.
     *
     * @param accountId    the ID of the modified account
     * @param action       the action performed on the account
     * @param amount       the amount of the action
     * @param memo         a description <b>(optional)</b>
     * @param isSuccessful indicates if the transaction was successful
     * @return the newly created transaction
     */
    Transaction createTransaction(int accountId, TransactionAction action, int amount, String memo, boolean isSuccessful);

    /**
     * Read a record from the {@code transaction} table by ID.
     *
     * @param id the ID of the record to read
     * @return the requested transaction
     */
    Transaction readTransaction(int id);
}
