package com.rmiecommerce.client.scene;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

public class SuccessScene {
    private final VBox mainBox;
    private Button backToShopButton;

    public SuccessScene(EventHandler<MouseEvent> mouseEventHandler) {
        Label successLabel = new Label("Votre paiement a été validé ! Merci pour vos achats.");
        successLabel.setWrapText(true);

        backToShopButton = new Button("Revenir au magasin");
        backToShopButton.setOnMouseClicked(mouseEventHandler);

        HBox buttonBox = new HBox(backToShopButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox contentBox = new VBox(successLabel, buttonBox);
        contentBox.setMaxWidth(400);
        contentBox.setSpacing(16);
        contentBox.setPadding(new Insets(16));

        mainBox = new VBox(contentBox);
        mainBox.setAlignment(Pos.CENTER);
    }

    public void show(Scene scene) {
        scene.setRoot(mainBox);
    }
}
