package com.rmiecommerce.common;

import java.io.Serializable;

public class Article implements Serializable {
    public final String name;
    public final double price;

    public Article(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
