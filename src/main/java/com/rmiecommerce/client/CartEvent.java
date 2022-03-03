package com.rmiecommerce.client;

public class CartEvent {
    public static enum Type {
        ADD,
        DELETE,
        CHANGE_QUANTITY
    }

    public final Type type;
    public final CartEntry cartEntry;

    public CartEvent(Type type, CartEntry cartEntry) {
        this.type = type;
        this.cartEntry = cartEntry;
    }
}
