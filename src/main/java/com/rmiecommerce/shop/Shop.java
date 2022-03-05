package com.rmiecommerce.shop;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Shop {
    public static void main(String[] args) throws RemoteException,
            MalformedURLException{

        if(args.length == 0) {
            System.err.println("You have to provide a listen port number");
            System.exit(1);
        }

        try {
            LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            ShopRemote shopRemote = new ShopRemote();
            Naming.rebind("rmi://localhost:3007/shop", shopRemote);
        } catch(NumberFormatException exception) {
            System.err.println("Incorrect port number");
            System.exit(1);
        }
    }
}
