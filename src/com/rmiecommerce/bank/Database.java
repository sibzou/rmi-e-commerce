package com.rmiecommerce.bank;

import com.rmiecommerce.CommonDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private Connection connection;

    public Database(String dbPath) {
        connection = CommonDatabase.getConnection(dbPath);
    }

    public boolean canDebit(String accountNumber, String accountCryptogram,
            float amountToDebit) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(
            "select num from account where num = ? and cryptogram = ? and " +
            "balance >= ?");

        stmt.setString(1, accountNumber);
        stmt.setString(2, accountCryptogram);
        stmt.setFloat(3, amountToDebit);

        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public void debit(String accountNumber, String accountCryptogram,
            float amountToDebit) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(
            "update account set balance = balance - ? where num = ? and " +
            "cryptogram = ? and balance >= ?");

        stmt.setFloat(1, amountToDebit);
        stmt.setString(2, accountNumber);
        stmt.setString(3, accountCryptogram);
        stmt.setFloat(4, amountToDebit);

        stmt.execute();
    }
}
