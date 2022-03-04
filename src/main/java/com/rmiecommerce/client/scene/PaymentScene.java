package com.rmiecommerce.client.scene;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.text.Font;

public class PaymentScene {
    private final VBox mainBox;
    private final Label priceLabel;
    private final TextField numTextField;
    private final TextField cryptogramTextField;
    private final Label errorLabel;
    private final Button backButton;
    private final Button confirmButton;

    public static class ClickResult {
        public static enum Type {
            BACK_TO_CART,
            CONFIRM_PAYMENT
        }

        public Type type;
        public String creditCardNumber;
        public String creditCardCryptogram;
    }

    public PaymentScene(EventHandler<MouseEvent> mouseEventHandler) {
        priceLabel = new Label();
        priceLabel.setFont(new Font(16));

        Label instructionLabel = new Label("Veuillez saisir votre numéro de carte bancaire ainsi que votre cryptogramme visuel afin de valider le paiement.");
        instructionLabel.setWrapText(true);

        Label numLabel = new Label("Numéro de carte");
        numTextField = new TextField();
        VBox numBox = new VBox(numLabel, numTextField);

        Label cryptogramLabel = new Label("Cryptogramme visuel");
        cryptogramTextField = new TextField();
        VBox cryptogramBox = new VBox(cryptogramLabel, cryptogramTextField);

        errorLabel = new Label("Le paiement n'a pas pu être validé");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);

        backButton = new Button("Revenir au panier");
        backButton.setOnMouseClicked(mouseEventHandler);

        confirmButton = new Button("Valider le paiement");
        confirmButton.setOnMouseClicked(mouseEventHandler);

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

    public void showError() {
        errorLabel.setVisible(true);
    }

    public void onMouseClick(MouseEvent event, ClickResult clickRes) {
        Object source = event.getSource();

        if(source == backButton) {
            clickRes.type = ClickResult.Type.BACK_TO_CART;
        } else if(source == confirmButton) {
            clickRes.type = ClickResult.Type.CONFIRM_PAYMENT;
            clickRes.creditCardNumber = numTextField.getText();
            clickRes.creditCardCryptogram = cryptogramTextField.getText();
        }
    }

    public void show(Scene scene, double totalPrice) {
        priceLabel.setText("Total à payer : " + totalPrice + " €");
        scene.setRoot(mainBox);
    }
}
