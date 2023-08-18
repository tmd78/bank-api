package com.draper.bankapi.data.transaction;

import com.draper.bankapi.common.TransactionAction;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("transactionRepository")
public class TransactionRepositoryImpl implements TransactionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TransactionRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("transaction")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Transaction createTransaction(int accountId, TransactionAction action, int amount, String memo, boolean isSuccessful) {
        // TODO: Implement.
        return null;
    }

    @Override
    public Transaction readTransaction(int id) {
        // TODO: Implement.
        return null;
    }
}
