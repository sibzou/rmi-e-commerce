package com.rmiecommerce.shop;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Shop {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(3007);
            ShopRemote shopRemote = new ShopRemote();
            Naming.rebind("rmi://localhost:3007/shop", shopRemote);
        } catch(RemoteException | MalformedURLException ignored) {}
    }
}
