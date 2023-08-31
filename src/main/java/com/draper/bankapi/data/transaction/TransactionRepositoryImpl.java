package com.draper.bankapi.data.transaction;

import com.draper.bankapi.common.TransactionType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
                .usingGeneratedKeyColumns(Transaction.COLUMN_ID);
    }

    @Override
    public Transaction createTransaction(int accountId, TransactionType action, int amount) {
        MapSqlParameterSource insertValues = new MapSqlParameterSource();
        insertValues
                .addValue(Transaction.COLUMN_ACCOUNT_ID, accountId)
                .addValue(Transaction.COLUMN_ACTION, action.name())
                .addValue(Transaction.COLUMN_AMOUNT, amount);

        int newTransactionId = simpleJdbcInsert.executeAndReturnKey(insertValues).intValue();

        return readTransaction(newTransactionId);
    }

    @Override
    public Transaction readTransaction(int id) {
        MapSqlParameterSource queryArguments = new MapSqlParameterSource();
        queryArguments.addValue(Transaction.COLUMN_ID, id);

        Transaction transaction = namedParameterJdbcTemplate.queryForObject(
                Transaction.READ_BY_ID,
                queryArguments,
                new TransactionRowMapper()
        );

        return transaction;
    }
}
