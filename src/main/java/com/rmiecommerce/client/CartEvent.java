package com.rmiecommerce.client;

public class CartEvent {
    public static enum Type {
        ADD
    }

    public final Type type;
    public final CartEntry cartEntry;

    public CartEvent(Type type, CartEntry cartEntry) {
        this.type = type;
        this.cartEntry = cartEntry;
    }
}
