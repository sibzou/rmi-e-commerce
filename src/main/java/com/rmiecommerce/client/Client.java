package com.rmiecommerce.client;

import com.rmiecommerce.client.scene.CartScene;
import com.rmiecommerce.client.scene.PaymentScene;
import com.rmiecommerce.client.scene.ShopScene;
import com.rmiecommerce.client.scene.SuccessScene;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class Client extends Application {
    private Scene scene;
    private FakeRmi fakeRmi;

    private ShopScene shopScene;
    private CartScene cartScene;
    private PaymentScene paymentScene;
    private SuccessScene successScene;

    private void onMouseClick(MouseEvent event) {
        if(shopScene.onMouseClick(event)) {
            cartScene.show(scene);
        } else if(cartScene.onMouseClick(event)) {
            shopScene.show(scene);
        }
    }

    private void onComboBoxAction(ActionEvent event) {
        Article[] articles = fakeRmi.getShopArticles();
        CartEntry[] cart = fakeRmi.getCart();

        shopScene.addArticles(articles, cart);
        cartScene.fillCart(articles, cart);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("RMI e-commerce");
        stage.setWidth(800);
        stage.setHeight(600);

        fakeRmi = new FakeRmi();

        shopScene = new ShopScene(this::onMouseClick, this::onComboBoxAction);
        cartScene = new CartScene(this::onMouseClick);
        paymentScene = new PaymentScene();
        successScene = new SuccessScene(this::onMouseClick);

        scene = shopScene.firstShow();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
