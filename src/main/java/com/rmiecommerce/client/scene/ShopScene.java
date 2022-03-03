package com.rmiecommerce.client.scene;

import com.rmiecommerce.client.Article;
import com.rmiecommerce.client.CartEntry;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;

public class ShopScene {
    private final VBox mainBox;
    private final FlowPane goodsPane;
    private final Button seeCartButton;
    private Button[] addToCartButtons;
    private HBox[] purchaseControlBoxes;
    private Spinner[] purchaseQuantitySpinners;
    private Button[] cartRemovalButtons;
    private final EventHandler<MouseEvent> mouseEventHandler;

    public ShopScene(EventHandler<MouseEvent> mouseEventHandler,
            EventHandler<ActionEvent> comboBoxActionHandler) {

        this.mouseEventHandler = mouseEventHandler;

        Label shopSelectorLabel = new Label("Magasin");
        ComboBox<String> shopSelectorComboBox = new ComboBox<String>();
        shopSelectorComboBox.setOnAction(comboBoxActionHandler);

        shopSelectorComboBox.getItems().addAll("Brico3000",
                                               "Biomarket",
                                               "SportMax");

        HBox shopSelector = new HBox(shopSelectorLabel, shopSelectorComboBox);
        shopSelector.setAlignment(Pos.CENTER);
        shopSelector.setSpacing(16);
        shopSelector.setPadding(new Insets(16));

        goodsPane = new FlowPane();
        goodsPane.setAlignment(Pos.TOP_CENTER);
        goodsPane.setVgap(64);
        goodsPane.setHgap(32);
        goodsPane.setPadding(new Insets(16));

        ScrollPane goodsScrollPane = new ScrollPane(goodsPane);
        goodsScrollPane.setFitToWidth(true);
        VBox.setVgrow(goodsScrollPane, Priority.ALWAYS);

        seeCartButton = new Button("Voir mon panier");
        seeCartButton.setDisable(true);
        seeCartButton.setOnMouseClicked(mouseEventHandler);

        HBox bottomBar = new HBox(seeCartButton);
        bottomBar.setPadding(new Insets(16));
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        mainBox = new VBox(shopSelector, goodsScrollPane, bottomBar);
    }

    @SuppressWarnings("unchecked")
    private static void setSpinnerValue(Spinner spinner, int value) {
        spinner.getValueFactory().setValue(value);
    }

    private void addArticleInShop(Article article, int purchaseQuantity,
            int index) {

        Label nameLabel = new Label(article.name);
        nameLabel.setFont(new Font(16));

        Label priceLabel = new Label(article.price + " €");

        Button addToCartButton = new Button("Ajouter au panier");
        addToCartButton.setOnMouseClicked(mouseEventHandler);
        addToCartButtons[index] = addToCartButton;

        Spinner purchaseQuantitySpinner = new Spinner(1, 100, 1);
        purchaseQuantitySpinners[index] = purchaseQuantitySpinner;

        Button cartRemovalButton = new Button("Supprimer");
        cartRemovalButton.setOnMouseClicked(mouseEventHandler);
        cartRemovalButtons[index] = cartRemovalButton;

        HBox purchaseControlBox = new HBox(purchaseQuantitySpinner,
            cartRemovalButton);
        purchaseControlBox.setSpacing(16);
        purchaseControlBoxes[index] = purchaseControlBox;

        VBox articleBox = new VBox(nameLabel, priceLabel);

        if(purchaseQuantity > 0) {
            articleBox.getChildren().add(purchaseControlBox);
            setSpinnerValue(purchaseQuantitySpinner, purchaseQuantity);
        } else {
            articleBox.getChildren().add(addToCartButton);
        }

        articleBox.setSpacing(16);
        goodsPane.getChildren().add(articleBox);
    }

    private int getPurchaseQuantity(CartEntry[] cart, Article article) {
        for(CartEntry cartEntry : cart) {
            if(cartEntry.articleId == article.id) {
                return cartEntry.purchaseQuantity;
            }
        }

        return 0;
    }

    public void addArticles(Article[] articles, CartEntry[] cart) {
        goodsPane.getChildren().clear();
        seeCartButton.setDisable(false);

        addToCartButtons = new Button[articles.length];
        purchaseControlBoxes = new HBox[articles.length];
        purchaseQuantitySpinners = new Spinner[articles.length];
        cartRemovalButtons = new Button[articles.length];

        for(int i = 0; i < articles.length; i++) {
            Article article = articles[i];
            int purchaseQuantity = getPurchaseQuantity(cart, article);
            addArticleInShop(article, purchaseQuantity, i);
        }
    }

    public boolean onMouseClick(MouseEvent event) {
        Object source = event.getSource();

        if(source == seeCartButton) {
            return true;
        }

        for(int i = 0; i < addToCartButtons.length; i++) {
            Pane goodPane = (Pane)goodsPane.getChildren().get(i);
            List<Node> goodPaneChilds = goodPane.getChildren();

            if(source == addToCartButtons[i]) {
                goodPaneChilds.remove(2);
                goodPaneChilds.add(purchaseControlBoxes[i]);
                setSpinnerValue(purchaseQuantitySpinners[i], 1);
            }

            if(source == cartRemovalButtons[i]) {
                goodPaneChilds.remove(2);
                goodPaneChilds.add(addToCartButtons[i]);
            }
        }

        return false;
    }

    public void show(Scene scene) {
        scene.setRoot(mainBox);
    }

    public Scene firstShow() {
        return new Scene(mainBox);
    }
}
