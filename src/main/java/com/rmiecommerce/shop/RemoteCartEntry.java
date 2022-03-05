package com.rmiecommerce.shop;

import com.rmiecommerce.common.CartEntry;

import java.io.Serializable;

public class RemoteCartEntry implements Serializable {
    public int remoteArticleId;
    public int purchaseQuantity;

    public RemoteCartEntry(int remoteArticleId, int purchaseQuantity) {
        this.remoteArticleId = remoteArticleId;
        this.purchaseQuantity = purchaseQuantity;
    }

    public CartEntry getCartEntry(RemoteArticle[] remoteArticles) {
        for(int i = 0; i < remoteArticles.length; i++) {
            if(remoteArticleId == remoteArticles[i].remoteId) {
                return new CartEntry(i, purchaseQuantity);
            }
        }

        return null;
    }
}
