package com.rmiecommerce.shop;

import com.rmiecommerce.bank.IBankRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShopRemote extends UnicastRemoteObject implements IShopRemote {
    private Database database;
    private IBankRemote bankRemote;

    public ShopRemote(String dbPath, String bankAddress,
            int bankPort) throws RemoteException {

        super();
        database = new Database(dbPath);

        try {
            bankRemote = (IBankRemote)Naming.lookup("rmi://" + bankAddress + ":"
                + bankPort + "/bank");
        } catch(NotBoundException | MalformedURLException ignored) {}
    }

    @Override
    public RemoteArticle[] getArticles() {
        return database.getArticles();
    }

    @Override
    public RemoteCartEntry[] getCart() {
        return database.getCart();
    }

    @Override
    public void setArticlePurchaseQuantity(int remoteId, int quantity) {
        database.setArticlePurchaseQuantity(remoteId, quantity);
    }

    @Override
    public boolean pay(String creditCartNumber, String creditCardCryptogram) {
        float purchaseAmount = database.getCartTotalPrice();

        try {
            boolean paymentDone = bankRemote.debit(creditCartNumber,
                creditCardCryptogram, purchaseAmount);

            if(paymentDone) {
                database.clearCart();
            }

            return paymentDone;
        } catch(RemoteException exception) {
            return false;
        }
    }
}
