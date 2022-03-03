package com.rmiecommerce.client;

public class FakeRmi {
    public Article[] getShopArticles() {
        return new Article[] {
            new Article(1, "Chaussures", 22.87),
            new Article(2, "Poireau", 3.02),
            new Article(3, "Bouteille de lait", 1.50)
        };
    }

    public CartEntry[] getCart() {
        return new CartEntry[] {
            new CartEntry(3, 3),
            new CartEntry(2, 2)
        };
    }
}
