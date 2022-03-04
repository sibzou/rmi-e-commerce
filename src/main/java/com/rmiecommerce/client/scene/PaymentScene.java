package com.rmiecommerce.client.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.text.Font;

public class PaymentScene {
    private final VBox mainBox;
    private final Label priceLabel;

    public PaymentScene() {
        priceLabel = new Label();
        priceLabel.setFont(new Font(16));

        Label instructionLabel = new Label("Veuillez saisir votre numéro de carte bancaire ainsi que votre cryptogramme visuel afin de valider le paiement.");
        instructionLabel.setWrapText(true);

        Label numLabel = new Label("Numéro de carte");
        TextField numTextField = new TextField();
        VBox numBox = new VBox(numLabel, numTextField);

        Label cryptogramLabel = new Label("Cryptogramme visuel");
        TextField cryptogramTextField = new TextField();
        VBox cryptogramBox = new VBox(cryptogramLabel, cryptogramTextField);

        Label errorLabel = new Label("Le paiement n'a pas pu être validé");
        errorLabel.setTextFill(Color.RED);

        Button backButton = new Button("Revenir au panier");
        Button confirmButton = new Button("Valider le paiement");

        HBox controlBox = new HBox(backButton, confirmButton);
        controlBox.setAlignment(Pos.CENTER_RIGHT);
        controlBox.setSpacing(16);

        VBox contentBox = new VBox(priceLabel, instructionLabel, numBox, cryptogramBox, errorLabel, controlBox);
        contentBox.setPadding(new Insets(16));
        contentBox.setMaxWidth(400);
        contentBox.setSpacing(16);

        mainBox = new VBox(contentBox);
        mainBox.setAlignment(Pos.CENTER);
    }

    public void show(Scene scene, double totalPrice) {
        priceLabel.setText("Total à payer : " + totalPrice + " €");
        scene.setRoot(mainBox);
    }
}
