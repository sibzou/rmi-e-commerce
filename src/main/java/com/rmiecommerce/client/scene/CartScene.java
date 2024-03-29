package com.rmiecommerce.client.scene;

import com.rmiecommerce.client.CartEvent;
import com.rmiecommerce.client.Client;
import com.rmiecommerce.common.Article;
import com.rmiecommerce.common.CartEntry;

import java.util.Arrays;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.text.Font;

public class CartScene {
    private final VBox mainBox;
    private final VBox cartBox;
    private final Label totalLabel;
    private final Button backButton;
    private final Button paymentButton;
    private final ArrayList<Spinner> purchaseQuantitySpinners;
    private final ArrayList<Label> priceLabels;
    private final ArrayList<Button> removeButtons;
    private final EventHandler<MouseEvent> mouseEventHandler;
    private final ChangeListener<Integer> spinnerEventHandler;

    private Article[] articles;
    private final ArrayList<CartEntry> cart;

    public static class ClickResult {
        public static enum Type {
            BACK_TO_SHOP,
            GO_TO_PAYMENT,
            CART_EVENT
        }

        public Type type;
        public CartEvent cartEvent;
        public double totalPrice;
    }

    public CartScene(EventHandler<MouseEvent> mouseEventHandler,
            ChangeListener<Integer> spinnerEventHandler) {

        cartBox = new VBox();
        cartBox.setSpacing(16);
        cartBox.setPadding(new Insets(16));

        ScrollPane cartScrollPane = new ScrollPane(cartBox);
        cartScrollPane.setFitToWidth(true);
        VBox.setVgrow(cartScrollPane, Priority.ALWAYS);

        totalLabel = new Label();
        totalLabel.setFont(new Font(16));

        backButton = new Button("Revenir au magasin");
        backButton.setOnMouseClicked(mouseEventHandler);

        paymentButton = new Button("Procéder au paiement");
        paymentButton.setOnMouseClicked(mouseEventHandler);

        HBox bottomBar = new HBox(totalLabel, backButton, paymentButton);
        bottomBar.setPadding(new Insets(16));
        bottomBar.setSpacing(16);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        mainBox = new VBox(cartScrollPane, bottomBar);

        cart = new ArrayList<>();
        purchaseQuantitySpinners = new ArrayList<>();
        priceLabels = new ArrayList<>();
        removeButtons = new ArrayList<>();

        this.mouseEventHandler = mouseEventHandler;
        this.spinnerEventHandler = spinnerEventHandler;
    }

    private void updateItemLabel(int index) {
        CartEntry cartEntry = cart.get(index);
        Article article = articles[cartEntry.articleIndex];

        priceLabels.get(index)
            .setText((article.price * cartEntry.purchaseQuantity) + " €");
    }

    private double getTotalPrice() {
        double totalPrice = 0;

        for(CartEntry cartEntry : cart) {
            Article article = articles[cartEntry.articleIndex];
            totalPrice += article.price * cartEntry.purchaseQuantity;
        }

        return totalPrice;
    }

    private void updateTotalLabel() {
        double totalPrice = getTotalPrice();
        totalLabel.setText("Total : " + getTotalPrice() + " €");
        paymentButton.setDisable(totalPrice == 0);
    }

    private void addArticleToCart(Article article, int purchaseQuantity) {
        Label nameLabel = new Label(article.name);
        nameLabel.setFont(new Font(16));
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        Spinner purchaseQuantitySpinner = new Spinner(1, 100, purchaseQuantity);
        Client.setSpinnerChangeListener(purchaseQuantitySpinner,
            spinnerEventHandler);
        purchaseQuantitySpinners.add(purchaseQuantitySpinner);

        Label priceLabel = new Label();
        priceLabels.add(priceLabel);

        Button removeButton = new Button("Supprimer");
        removeButton.setOnMouseClicked(mouseEventHandler);
        removeButtons.add(removeButton);

        HBox articleBox = new HBox(nameLabel, purchaseQuantitySpinner,
            priceLabel, removeButton);

        articleBox.setAlignment(Pos.CENTER_LEFT);
        articleBox.setSpacing(16);
        cartBox.getChildren().add(articleBox);
    }

    public void fillCart(Article[] articles, CartEntry[] cart) {
        cartBox.getChildren().clear();
        this.articles = articles;

        this.cart.clear();
        for(CartEntry cartEntry : cart) {
            this.cart.add(cartEntry);
        }

        purchaseQuantitySpinners.clear();
        priceLabels.clear();
        removeButtons.clear();

        for(int i = 0; i < cart.length; i++) {
            CartEntry cartEntry = cart[i];
            Article article = articles[cartEntry.articleIndex];
            addArticleToCart(article, cartEntry.purchaseQuantity);
            updateItemLabel(i);
        }

        updateTotalLabel();
    }

    private int getCartEntryIndex(int articleIndex) {
        for(int i = 0; i < cart.size(); i++) {
            if(cart.get(i).articleIndex == articleIndex) {
                return i;
            }
        }

        return -1;
    }

    private void deleteCartEntry(int index) {
        cartBox.getChildren().remove(index);
        cart.remove(index);

        purchaseQuantitySpinners.remove(index);
        priceLabels.remove(index);
        removeButtons.remove(index);

        updateTotalLabel();
    }

    public void clearCart() {
        cartBox.getChildren().clear();
        cart.clear();

        purchaseQuantitySpinners.clear();
        priceLabels.clear();
        removeButtons.clear();

        updateTotalLabel();
    }

    public void onCartEvent(CartEvent cartEvent) {
        if(cartEvent.type == CartEvent.Type.ADD) {
            CartEntry cartEntry = cartEvent.cartEntry;
            Article article = articles[cartEntry.articleIndex];

            cart.add(cartEntry);
            addArticleToCart(article, cartEntry.purchaseQuantity);

            updateItemLabel(cart.size() - 1);
            updateTotalLabel();
        } else if(cartEvent.type == CartEvent.Type.DELETE) {
            int cartEntryIndex =
                getCartEntryIndex(cartEvent.cartEntry.articleIndex);
            deleteCartEntry(cartEntryIndex);
        } else if(cartEvent.type == CartEvent.Type.CHANGE_QUANTITY) {
            CartEntry cartEntry = cartEvent.cartEntry;
            int cartEntryIndex = getCartEntryIndex(cartEntry.articleIndex);

            cart.get(cartEntryIndex).purchaseQuantity
                = cartEntry.purchaseQuantity;

            Spinner spinner = purchaseQuantitySpinners.get(cartEntryIndex);
            Client.setSpinnerValue(spinner, cartEntry.purchaseQuantity,
                spinnerEventHandler);

            updateItemLabel(cartEntryIndex);
            updateTotalLabel();
        }
    }

    public void onMouseClick(MouseEvent event, ClickResult clickResult) {
        Object source = event.getSource();

        if(source == backButton) {
            clickResult.type = ClickResult.Type.BACK_TO_SHOP;
        } else if(source == paymentButton) {
            clickResult.type = ClickResult.Type.GO_TO_PAYMENT;
            clickResult.totalPrice = getTotalPrice();
        } else {
            for(int i = 0; i < removeButtons.size(); i++) {
                if(source == removeButtons.get(i)) {
                    clickResult.type = ClickResult.Type.CART_EVENT;
                    clickResult.cartEvent = new CartEvent(CartEvent.Type.DELETE,
                        cart.get(i));

                    deleteCartEntry(i);
                }
            }
        }
    }

    public void onSpinnerValueChange(Client.SpinnerChangeResult changeRes,
            ObservableValue<? extends Integer> observable, int newValue) {

        for(int i = 0; i < purchaseQuantitySpinners.size(); i++) {
            Spinner purchaseQuantitySpinner = purchaseQuantitySpinners.get(i);

            if(observable == purchaseQuantitySpinner.valueProperty()) {
                CartEntry cartEntry = cart.get(i);
                cartEntry.purchaseQuantity = newValue;

                updateItemLabel(i);
                updateTotalLabel();

                changeRes.cartEvent
                    = new CartEvent(CartEvent.Type.CHANGE_QUANTITY, cartEntry);
            }
        }
    }

    public void show(Scene scene) {
        scene.setRoot(mainBox);
    }
}
