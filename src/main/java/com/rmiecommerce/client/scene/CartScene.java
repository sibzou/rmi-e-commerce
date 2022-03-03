package com.rmiecommerce.client.scene;

import com.rmiecommerce.client.Article;
import com.rmiecommerce.client.CartEntry;
import com.rmiecommerce.client.CartEvent;

import java.util.Arrays;
import java.util.ArrayList;

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

    private Article[] articles;
    private ArrayList<CartEntry> cart;

    public CartScene(EventHandler<MouseEvent> mouseEventHandler) {
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

        Button paymentButton = new Button("Procéder au paiement");

        HBox bottomBar = new HBox(totalLabel, backButton, paymentButton);
        bottomBar.setPadding(new Insets(16));
        bottomBar.setSpacing(16);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        mainBox = new VBox(cartScrollPane, bottomBar);
    }

    private void updateTotalLabel() {
        double totalPrice = 0;

        for(CartEntry cartEntry : cart) {
            Article article = articles[cartEntry.articleIndex];
            totalPrice += article.price * cartEntry.purchaseQuantity;
        }

        totalLabel.setText("Total : " + totalPrice + " €");
    }

    private void addArticleToCart(Article article, int purchaseQuantity) {
        Label nameLabel = new Label(article.name);
        nameLabel.setFont(new Font(16));
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        Spinner purchaseQuantitySpinner = new Spinner(1, 100, purchaseQuantity);
        Label priceLabel = new Label((article.price * purchaseQuantity) + " €");
        Button deleteButton = new Button("Supprimer");

        HBox articleBox = new HBox(nameLabel, purchaseQuantitySpinner,
            priceLabel, deleteButton);

        articleBox.setAlignment(Pos.CENTER_LEFT);
        articleBox.setSpacing(16);
        cartBox.getChildren().add(articleBox);
    }

    public void fillCart(Article[] articles, CartEntry[] cart) {
        cartBox.getChildren().clear();
        this.articles = articles;
        this.cart = new ArrayList<>(Arrays.asList(cart));

        for(CartEntry cartEntry : cart) {
            Article article = articles[cartEntry.articleIndex];
            addArticleToCart(article, cartEntry.purchaseQuantity);
        }

        updateTotalLabel();
    }

    public void onCartEvent(CartEvent cartEvent) {
        if(cartEvent.type == CartEvent.Type.ADD) {
            CartEntry cartEntry = cartEvent.cartEntry;
            Article article = articles[cartEntry.articleIndex];
            addArticleToCart(article, cartEntry.purchaseQuantity);

            cart.add(cartEntry);
            updateTotalLabel();
        }
    }

    public boolean onMouseClick(MouseEvent event) {
        Object source = event.getSource();
        return source == backButton;
    }

    public void show(Scene scene) {
        scene.setRoot(mainBox);
    }
}
