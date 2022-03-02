package com.rmiecommerce.client;

public class Article {
    public String name;
    public double price;
    public int purchaseQuantity;

    public Article(String name, double price, int purchaseQuantity) {
        this.name = name;
        this.price = price;
        this.purchaseQuantity = purchaseQuantity;
    }
}
