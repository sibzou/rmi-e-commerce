package com.rmiecommerce.shop;

import com.rmiecommerce.common.Article;
import com.rmiecommerce.common.CartEntry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShopRemote extends UnicastRemoteObject implements IShopRemote {
    public ShopRemote() throws RemoteException {
        super();
    }

    @Override
    public Article[] getArticles() {
        return new Article[] {
            new Article("Chaussures", 22.87),
            new Article("Poireau", 3.02),
            new Article("Bouteille de lait", 1.50)
        };
    }

    @Override
    public CartEntry[] getCart() {
        return new CartEntry[] {
            new CartEntry(2, 3),
            new CartEntry(1, 2)
        };
    }

    @Override
    public boolean pay(String creditCartNumber, String creditCardCryptogram) {
        return creditCartNumber.equals("123")
            && creditCardCryptogram.equals("321");
    }
}
