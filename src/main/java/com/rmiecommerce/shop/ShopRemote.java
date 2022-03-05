package com.rmiecommerce.shop;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShopRemote extends UnicastRemoteObject implements IShopRemote {
    public ShopRemote() throws RemoteException {
        super();
    }

    @Override
    public RemoteArticle[] getArticles() {
        return new RemoteArticle[] {
            new RemoteArticle(1, "Chaussures", 22.87),
            new RemoteArticle(2, "Poireau", 3.02),
            new RemoteArticle(3, "Bouteille de lait", 1.50)
        };
    }

    @Override
    public RemoteCartEntry[] getCart() {
        return new RemoteCartEntry[] {
            new RemoteCartEntry(3, 3),
            new RemoteCartEntry(2, 2)
        };
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
