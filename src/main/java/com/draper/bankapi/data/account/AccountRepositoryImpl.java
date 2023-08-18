package com.draper.bankapi.data.account;

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
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Account createAccount(int balance, String passcode) {
        MapSqlParameterSource insertValues = new MapSqlParameterSource();
        insertValues
                .addValue("balance", balance)
                .addValue("passcode", passcode);

        int newAccountId = simpleJdbcInsert.executeAndReturnKey(insertValues).intValue();

        return readAccount(newAccountId);
    }

    @Override
    public Account readAccount(int id) {
        MapSqlParameterSource queryArguments = new MapSqlParameterSource();
        queryArguments.addValue("id", id);

        Account account = namedParameterJdbcTemplate.queryForObject(
                Account.READ_BY_ID,
                queryArguments,
                new AccountRowMapper()
        );

        return account;
    }

    @Override
    public Account updateAccountBalance(int id, int newBalance) {
        MapSqlParameterSource queryArguments = new MapSqlParameterSource();
        queryArguments.addValue("balance", newBalance);
        queryArguments.addValue("id", id);

        namedParameterJdbcTemplate.update(Account.UPDATE_BALANCE, queryArguments);

        return readAccount(id);
    }
}
