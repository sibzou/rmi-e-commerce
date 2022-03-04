package com.rmiecommerce.common;

import java.io.Serializable;

public class CartEntry implements Serializable {
    public int articleIndex;
    public int purchaseQuantity;

    public CartEntry(int articleIndex, int purchaseQuantity) {
        this.articleIndex = articleIndex;
        this.purchaseQuantity = purchaseQuantity;
    }
}
