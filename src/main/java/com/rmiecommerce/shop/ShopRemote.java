package com.rmiecommerce.shop;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShopRemote extends UnicastRemoteObject implements IShopRemote {
    private Database database;

    public ShopRemote(String dbPath) throws RemoteException {
        super();
        database = new Database(dbPath);
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
    }

    @Override
    public boolean pay(String creditCartNumber, String creditCardCryptogram) {
        return creditCartNumber.equals("123")
            && creditCardCryptogram.equals("321");
    }
}
