package com.draper.bankapi.data.account;

import java.util.List;

public interface AccountRepository {
    /**
     * Create a record in the {@code account} table.
     *
     * @param balance  the starting balance
     * @param passcode the passcode for the new account
     * @return the newly created account
     */
    Account createAccount(int balance, String passcode);

    /**
     * Read a record from the {@code account} table by ID.
     *
     * @param id the ID of the record to read
     * @return the requested account
     */
    Account readAccount(int id);

    /**
     * Update the specified account's balance.
     *
     * @param id         the ID of the account to update
     * @param newBalance the new balance
     * @return the number of accounts modified
     */
    int updateAccountBalance(int id, int newBalance);

    /**
     * Delete the specified accounts.
     *
     * @param ids the IDs of the accounts to delete
     * @return an array containing the numbers of rows affected by each update in the batch
     */
    int[] deleteAccounts(List<Integer> ids);
}
