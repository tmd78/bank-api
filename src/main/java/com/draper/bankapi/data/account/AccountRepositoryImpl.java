package com.draper.bankapi.data.account;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("accountRepository")
public class AccountRepositoryImpl implements AccountRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public AccountRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("account")
                .usingGeneratedKeyColumns(Account.COLUMN_ID);
    }

    @Override
    public Account createAccount(int balance, String passcode) {
        MapSqlParameterSource insertValues = new MapSqlParameterSource();
        insertValues
                .addValue(Account.COLUMN_BALANCE, balance)
                .addValue(Account.COLUMN_PASSCODE, passcode);

        int newAccountId = simpleJdbcInsert.executeAndReturnKey(insertValues).intValue();

        return readAccount(newAccountId);
    }

    @Override
    public Account readAccount(int id) {
        Account account;

        MapSqlParameterSource queryArguments = new MapSqlParameterSource();
        queryArguments.addValue(Account.COLUMN_ID, id);

        try {
            account = namedParameterJdbcTemplate.queryForObject(
                    Account.READ_BY_ID,
                    queryArguments,
                    new AccountRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            account = null;
        }

        return account;
    }

    @Override
    public int updateAccountBalance(int id, int newBalance) {
        MapSqlParameterSource queryArguments = new MapSqlParameterSource();
        queryArguments.addValue(Account.COLUMN_ID, id);
        queryArguments.addValue(Account.COLUMN_BALANCE, newBalance);

        return namedParameterJdbcTemplate.update(Account.UPDATE_BALANCE, queryArguments);
    }

    @Override
    public int deleteAccount(int id) {
        MapSqlParameterSource queryArguments = new MapSqlParameterSource();
        queryArguments.addValue(Account.COLUMN_ID, id);

        return namedParameterJdbcTemplate.update(Account.DELETE_BY_ID, queryArguments);
    }
}
