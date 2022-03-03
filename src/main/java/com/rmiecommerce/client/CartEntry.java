package com.rmiecommerce.client;

public class CartEntry {
    public int articleId;
    public int purchaseQuantity;

    public CartEntry(int articleId, int purchaseQuantity) {
        this.articleId = articleId;
        this.purchaseQuantity = purchaseQuantity;
    }
}
