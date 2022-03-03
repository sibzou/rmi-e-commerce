package com.rmiecommerce.client;

import com.rmiecommerce.client.scene.CartScene;
import com.rmiecommerce.client.scene.PaymentScene;
import com.rmiecommerce.client.scene.ShopScene;
import com.rmiecommerce.client.scene.SuccessScene;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.Spinner;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class Client extends Application {
    private Scene scene;
    private FakeRmi fakeRmi;

    private ShopScene shopScene;
    private CartScene cartScene;
    private PaymentScene paymentScene;
    private SuccessScene successScene;

    private void onShopSceneClick(MouseEvent event) {
        ShopScene.ClickResult clickRes = new ShopScene.ClickResult();
        shopScene.onMouseClick(event, clickRes);

        if(clickRes.type == ShopScene.ClickResult.Type.SEE_CART) {
            cartScene.show(scene);
        } else if(clickRes.type == ShopScene.ClickResult.Type.CART_EVENT) {
            cartScene.onCartEvent(clickRes.cartEvent);
        }
    }

    private void onCartSceneClick(MouseEvent event) {
        CartScene.ClickResult clickRes = new CartScene.ClickResult();
        cartScene.onMouseClick(event, clickRes);

        if(clickRes.type == CartScene.ClickResult.Type.BACK_TO_SHOP) {
            shopScene.show(scene);
        } else if(clickRes.type == CartScene.ClickResult.Type.CART_EVENT) {
            shopScene.onCartEvent(clickRes.cartEvent);
        }
    }

    private void onMouseClick(MouseEvent event) {
        onShopSceneClick(event);
        onCartSceneClick(event);
    }

    private void onComboBoxAction(ActionEvent event) {
        Article[] articles = fakeRmi.getShopArticles();
        CartEntry[] cart = fakeRmi.getCart();

        shopScene.addArticles(articles, cart);
        cartScene.fillCart(articles, cart);
    }

    @SuppressWarnings("unchecked")
    public static void setSpinnerValue(Spinner spinner, int value) {
        spinner.getValueFactory().setValue(value);
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
