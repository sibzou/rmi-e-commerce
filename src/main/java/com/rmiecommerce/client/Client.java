package com.rmiecommerce.client;

import com.rmiecommerce.client.scene.CartScene;
import com.rmiecommerce.client.scene.PaymentScene;
import com.rmiecommerce.client.scene.ShopScene;
import com.rmiecommerce.client.scene.SuccessScene;
import com.rmiecommerce.common.Article;
import com.rmiecommerce.common.CartEntry;
import com.rmiecommerce.shop.IShopRemote;
import com.rmiecommerce.shop.RemoteArticle;
import com.rmiecommerce.shop.RemoteCartEntry;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
    private Scene scene;
    private IShopRemote shopRemote;
    private RemoteArticle[] remoteArticles;

    private ShopScene shopScene;
    private CartScene cartScene;
    private PaymentScene paymentScene;
    private SuccessScene successScene;

    public static class SpinnerChangeResult {
        public CartEvent cartEvent;
    }

    private void handleCartEvent(CartEvent event) {
        CartEntry cartEntry = event.cartEntry;
        RemoteArticle remoteArticle = remoteArticles[cartEntry.articleIndex];
        int articleRemoteId = remoteArticle.remoteId;

        try {
            if(event.type == CartEvent.Type.ADD) {
                shopRemote.setArticlePurchaseQuantity(articleRemoteId, 1);
            } else if(event.type == CartEvent.Type.DELETE) {
                shopRemote.setArticlePurchaseQuantity(articleRemoteId, 0);
            } else if(event.type == CartEvent.Type.CHANGE_QUANTITY) {
                shopRemote.setArticlePurchaseQuantity(articleRemoteId,
                    cartEntry.purchaseQuantity);
            }
        } catch(RemoteException ignored) {}
    }

    private void onShopSceneClick(MouseEvent event) {
        ShopScene.ClickResult clickRes = new ShopScene.ClickResult();
        shopScene.onMouseClick(event, clickRes);

        if(clickRes.type == ShopScene.ClickResult.Type.SEE_CART) {
            cartScene.show(scene);
        } else if(clickRes.type == ShopScene.ClickResult.Type.CART_EVENT) {
            cartScene.onCartEvent(clickRes.cartEvent);
            handleCartEvent(clickRes.cartEvent);
        }
    }

    private void onCartSceneClick(MouseEvent event) {
        CartScene.ClickResult clickRes = new CartScene.ClickResult();
        cartScene.onMouseClick(event, clickRes);

        if(clickRes.type == CartScene.ClickResult.Type.BACK_TO_SHOP) {
            shopScene.show(scene);
        } else if(clickRes.type == CartScene.ClickResult.Type.GO_TO_PAYMENT) {
            paymentScene.show(scene, clickRes.totalPrice);
        } else if(clickRes.type == CartScene.ClickResult.Type.CART_EVENT) {
            shopScene.onCartEvent(clickRes.cartEvent);
            handleCartEvent(clickRes.cartEvent);
        }
    }

    private void onPaymentSceneClick(MouseEvent event) {
        PaymentScene.ClickResult clickRes = new PaymentScene.ClickResult();
        paymentScene.onMouseClick(event, clickRes);

        if(clickRes.type == PaymentScene.ClickResult.Type.BACK_TO_CART) {
            cartScene.show(scene);
        } else if(clickRes.type
                == PaymentScene.ClickResult.Type.CONFIRM_PAYMENT) {

            try {
                if(shopRemote.pay(clickRes.creditCardNumber,
                        clickRes.creditCardCryptogram)) {

                    successScene.show(scene);
                } else {
                    paymentScene.showError();
                }
            } catch(RemoteException ignored) {}
        }
    }

    private void onSuccessSceneClick(MouseEvent event) {
        if(successScene.onMouseClick(event)) {
            shopScene.clearCart();
            cartScene.clearCart();
            shopScene.show(scene);
        }
    }

    private void onMouseClick(MouseEvent event) {
        onShopSceneClick(event);
        onCartSceneClick(event);
        onPaymentSceneClick(event);
        onSuccessSceneClick(event);
    }

    private void onComboBoxAction(ActionEvent event) {
        int port = shopScene.getSelectedShopPort();

        try {
            shopRemote = (IShopRemote)Naming.lookup("rmi://localhost:" + port
                + "/shop");
        } catch(NotBoundException | MalformedURLException
            | RemoteException ignored) {}

        try {
            remoteArticles = shopRemote.getArticles();
            Article[] articles = new Article[remoteArticles.length];

            for(int i = 0; i < articles.length; i++) {
                RemoteArticle remoteArticle = remoteArticles[i];
                articles[i] = new Article(remoteArticle.name,
                    remoteArticle.price);
            }

            RemoteCartEntry[] remoteCart = shopRemote.getCart();
            CartEntry[] cart = new CartEntry[remoteCart.length];

            for(int i = 0; i < cart.length; i++) {
                cart[i] = remoteCart[i].getCartEntry(remoteArticles);
            }

            shopScene.addArticles(articles, cart);
            cartScene.fillCart(articles, cart);
        } catch(RemoteException ignored) {}
    }

    private void onShopSceneSpinnerChange(
            ObservableValue<? extends Integer> observable, int newValue) {

        SpinnerChangeResult changeRes = new SpinnerChangeResult();
        shopScene.onSpinnerValueChange(changeRes, observable, newValue);

        if(changeRes.cartEvent == null) return;

        if(changeRes.cartEvent.type == CartEvent.Type.CHANGE_QUANTITY) {
            cartScene.onCartEvent(changeRes.cartEvent);
        }

        handleCartEvent(changeRes.cartEvent);
    }

    private void onCartSceneSpinnerChange(
            ObservableValue<? extends Integer> observable, int newValue) {

        SpinnerChangeResult changeRes = new SpinnerChangeResult();
        cartScene.onSpinnerValueChange(changeRes, observable, newValue);

        if(changeRes.cartEvent == null) return;

        if(changeRes.cartEvent.type == CartEvent.Type.CHANGE_QUANTITY) {
            shopScene.onCartEvent(changeRes.cartEvent);
        }

        handleCartEvent(changeRes.cartEvent);
    }

    private void onSpinnerValueChange(
            ObservableValue<? extends Integer> observable, Integer oldValue,
            Integer newValue) {

        onShopSceneSpinnerChange(observable, newValue);
        onCartSceneSpinnerChange(observable, newValue);
    }

    @SuppressWarnings("unchecked")
    public static void setSpinnerValue(Spinner spinner, int value,
            ChangeListener<Integer> listenerToInterrupt) {

        spinner.valueProperty().removeListener(listenerToInterrupt);
        spinner.getValueFactory().setValue(value);
        setSpinnerChangeListener(spinner, listenerToInterrupt);
    }

    @SuppressWarnings("unchecked")
    public static void setSpinnerChangeListener(Spinner spinner,
            ChangeListener<Integer> listener) {

        spinner.valueProperty().addListener(listener);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("RMI e-commerce");
        stage.setWidth(800);
        stage.setHeight(600);

        shopScene = new ShopScene(this::onMouseClick, this::onComboBoxAction,
            this::onSpinnerValueChange);

        cartScene = new CartScene(this::onMouseClick,
            this::onSpinnerValueChange);

        paymentScene = new PaymentScene(this::onMouseClick);
        successScene = new SuccessScene(this::onMouseClick);

        scene = shopScene.firstShow();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
