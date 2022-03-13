package com.rmiecommerce.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBankRemote extends Remote {
    public boolean debit(String accountNumber, String accountCryptogram,
            float amountToDebit) throws RemoteException;
}
