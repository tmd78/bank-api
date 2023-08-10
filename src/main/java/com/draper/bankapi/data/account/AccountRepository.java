package com.draper.bankapi.data.account;

public interface AccountRepository {
    /**
     * Create a record in the {@code account} table.
     *
     * @param account contains values for the new record
     * @return the newly created record as an {@link Account} object
     */
    Account createAccount(Account account);

    /**
     * Read a record from the {@code account} table by ID.
     *
     * @param id the ID of the record to read
     * @return the resulting record as an {@link Account} object
     */
    Account readAccount(int id);
}
