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
     * Read the specified account.
     *
     * @param id the ID of the account to read
     * @return the specified account or {@code null} if not found
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
     * Delete the specified account.
     *
     * @param id the ID of the account to delete
     * @return the number of accounts deleted
     */
    int deleteAccount(int id);

    /**
     * Delete the specified accounts.
     *
     * @param ids the IDs of the accounts to delete
     * @return an array containing the numbers of rows affected by each update in the batch
     */
    int[] deleteAccounts(List<Integer> ids);
}
