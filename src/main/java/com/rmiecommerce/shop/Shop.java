package com.rmiecommerce.shop;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Shop {
    public static void main(String[] args) throws RemoteException,
            MalformedURLException{

        if(args.length < 2) {
            System.err.println("You have to provide in this order : " +
                "a listen port number, " +
                "a sqlite db path, " +
                "the bank process ip address and " +
                "the bank process listening port.");
            System.exit(1);
        }

        try {
            int shopPort = Integer.parseInt(args[0]);
            int bankPort = Integer.parseInt(args[3]);

            LocateRegistry.createRegistry(shopPort);

            ShopRemote shopRemote = new ShopRemote(args[1], args[2], bankPort);
            Naming.rebind("rmi://localhost:" + shopPort + "/shop", shopRemote);
        } catch(NumberFormatException exception) {
            System.err.println("Incorrect port number");
            System.exit(1);
        }
    }
}
