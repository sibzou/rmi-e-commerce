package com.rmiecommerce.common;

public class CartEntry {
    public int articleIndex;
    public int purchaseQuantity;

    public CartEntry(int articleIndex, int purchaseQuantity) {
        this.articleIndex = articleIndex;
        this.purchaseQuantity = purchaseQuantity;
    }
}
