package com.rmiecommerce.client.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.text.Font;

public class CartScene {
    private final VBox mainBox;

    public CartScene() {
        VBox cartBox = new VBox();
        cartBox.setSpacing(16);
        cartBox.setPadding(new Insets(16));

        ScrollPane cartScrollPane = new ScrollPane(cartBox);
        cartScrollPane.setFitToWidth(true);
        VBox.setVgrow(cartScrollPane, Priority.ALWAYS);

        Label totalLabel = new Label("Total : 23,87 €");
        totalLabel.setFont(new Font(16));

        Button backButton = new Button("Revenir au magasin");
        Button paymentButton = new Button("Procéder au paiement");

        HBox bottomBar = new HBox(totalLabel, backButton, paymentButton);
        bottomBar.setPadding(new Insets(16));
        bottomBar.setSpacing(16);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        mainBox = new VBox(cartScrollPane, bottomBar);
    }

    private void addArticleToCart(String articleName, float articlePrice,
        int purchaseQuantity, VBox cartBox) {

        Label nameLabel = new Label("Caisse à outil XL 350 avec tournevis cruciforme");
        nameLabel.setFont(new Font(16));
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        Spinner purchaseQuantitySpinner = new Spinner(1, 100, purchaseQuantity);
        Label priceLabel = new Label("16,90 €");
        Button deleteButton = new Button("Supprimer");

        HBox articleBox = new HBox(nameLabel, purchaseQuantitySpinner,
            priceLabel, deleteButton);

        articleBox.setAlignment(Pos.CENTER_LEFT);
        articleBox.setSpacing(16);
        cartBox.getChildren().add(articleBox);
    }

    public void show(Scene scene) {
        scene.setRoot(mainBox);
    }
}
