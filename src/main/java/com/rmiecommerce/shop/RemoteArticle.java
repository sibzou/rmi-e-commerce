package com.rmiecommerce.shop;

import java.io.Serializable;

public class RemoteArticle implements Serializable {
    public final int remoteId;
    public final String name;
    public final double price;

    public RemoteArticle(int remoteId, String name, double price) {
        this.remoteId = remoteId;
        this.name = name;
        this.price = price;
    }
}
