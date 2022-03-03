package com.rmiecommerce.client;

public class CartEntry {
    public final int articleIndex;
    public final int purchaseQuantity;

    public CartEntry(int articleIndex, int purchaseQuantity) {
        this.articleIndex = articleIndex;
        this.purchaseQuantity = purchaseQuantity;
    }
}
