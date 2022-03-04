package com.rmiecommerce.shop;

import com.rmiecommerce.common.Article;
import com.rmiecommerce.common.CartEntry;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IShopRemote extends Remote {
    public Article[] getArticles() throws RemoteException;
    public CartEntry[] getCart() throws RemoteException;
    public boolean pay(String creditCardNumber,
        String creditCardCryptogram) throws RemoteException;
}
