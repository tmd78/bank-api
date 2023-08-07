package com.draper.bankapi.data;

import com.draper.bankapi.model.Account;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("accountRepository")
public class AccountRepositoryImpl implements AccountRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public AccountRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("account")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Account createAccount(Account account) {
        MapSqlParameterSource sqlArguments = new MapSqlParameterSource();
        sqlArguments
                .addValue("balance", account.getBalance())
                .addValue("passcode", account.getPasscode());

        // Use a builder to build a new Account object.
        int accountId = simpleJdbcInsert.executeAndReturnKey(sqlArguments).intValue();
        account.setId(accountId);

        return account;
    }
}
