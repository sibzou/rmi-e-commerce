package com.rmiecommerce.client;

public class FakeRmi {
    public Article[] getShopArticles() {
        return new Article[] {
            new Article("Chaussures", 22.87),
            new Article("Poireau", 3.02),
            new Article("Bouteille de lait", 1.50)
        };
    }

    public CartEntry[] getCart() {
        return new CartEntry[] {
            new CartEntry(2, 3),
            new CartEntry(1, 2)
        };
    }

    public boolean pay(String creditCartNumber, String creditCardCryptogram) {
        return creditCartNumber.equals("123")
            && creditCardCryptogram.equals("321");
    }
}
