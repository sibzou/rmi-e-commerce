package com.rmiecommerce.client;

public class FakeRmi {
    public Article[] getShopArticles() {
        return new Article[] {
            new Article("Chaussures", 22.87),
            new Article("Poireau", 3.02),
            new Article("Bouteille de lait", 1.50)
        };
    }
}
