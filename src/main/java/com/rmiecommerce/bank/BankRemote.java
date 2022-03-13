package com.rmiecommerce.bank;

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankRemote extends UnicastRemoteObject implements IBankRemote {
    private Database database;

    public BankRemote(String dbPath) throws RemoteException {
        super();
        database = new Database(dbPath);
    }

    @Override
    public boolean debit(String accountNumber, String accountCryptogram,
            float amountToDebit) throws RemoteException {

        try {
            if(database.canDebit(accountNumber, accountCryptogram,
                    amountToDebit)) {

                database.debit(accountNumber, accountCryptogram, amountToDebit);
                return true;
            } else {
                return false;
            }
        } catch(SQLException exception) {
            return false;
        }
    }
}
