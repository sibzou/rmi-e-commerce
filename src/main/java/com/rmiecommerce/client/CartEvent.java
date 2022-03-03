package com.rmiecommerce.client;

public class CartEvent {
    public static enum Type {
        ADD,
        DELETE
    }

    public final Type type;
    public final CartEntry cartEntry;
    public final int articleIndex;

    public CartEvent(Type type, CartEntry cartEntry) {
        this.type = type;
        this.cartEntry = cartEntry;
        this.articleIndex = 0;
    }

    public CartEvent(Type type, int articleIndex) {
        this.type = type;
        this.cartEntry = null;
        this.articleIndex = articleIndex;
    }
}
