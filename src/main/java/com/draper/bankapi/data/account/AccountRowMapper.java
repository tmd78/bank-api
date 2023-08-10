package com.draper.bankapi.data.account;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setBalance(rs.getInt("balance"));
        account.setPasscode(rs.getString("passcode"));

        return account;
    }
}
