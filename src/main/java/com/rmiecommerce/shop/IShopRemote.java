package com.rmiecommerce.shop;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IShopRemote extends Remote {
    public RemoteArticle[] getArticles() throws RemoteException;
    public RemoteCartEntry[] getCart() throws RemoteException;

    public void setArticlePurchaseQuantity(int remoteId,
        int quantity) throws RemoteException;

    public boolean pay(String creditCardNumber,
        String creditCardCryptogram) throws RemoteException;
}
