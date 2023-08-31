package com.draper.bankapi.data.transaction;

import com.draper.bankapi.common.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getInt(Transaction.COLUMN_ID));
        transaction.setAccountId(rs.getInt(Transaction.COLUMN_ACCOUNT_ID));
        transaction.setAction(TransactionType.valueOf(rs.getString(Transaction.COLUMN_ACTION).toUpperCase()));
        transaction.setAmount(rs.getInt(Transaction.COLUMN_AMOUNT));

        return transaction;
    }
}
