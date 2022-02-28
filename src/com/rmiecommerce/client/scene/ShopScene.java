package com.rmiecommerce.client.scene;

import com.rmiecommerce.client.Article;

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
        seeCartButton.setOnMouseClicked(mouseEventHandler);

        HBox bottomBar = new HBox(seeCartButton);
        bottomBar.setPadding(new Insets(16));
        bottomBar.setAlignment(Pos.CENTER_RIGHT);

        mainBox = new VBox(shopSelector, goodsScrollPane, bottomBar);
    }

    private void addArticleInShop(Article article, int index) {
        Label nameLabel = new Label(article.name);
        nameLabel.setFont(new Font(16));

        Label priceLabel = new Label(article.price + " €");

        Button addToCartButton = new Button("Ajouter au panier");
        addToCartButton.setMaxWidth(Double.MAX_VALUE);
        addToCartButton.setOnMouseClicked(mouseEventHandler);
        addToCartButtons[index] = addToCartButton;

        Spinner purchaseQuantitySpinner = new Spinner(1, 100, 1);
        purchaseQuantitySpinner.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(purchaseQuantitySpinner, Priority.ALWAYS);

        Button cartRemovalButton = new Button("Supprimer");
        cartRemovalButton.setOnMouseClicked(mouseEventHandler);
        cartRemovalButtons[index] = cartRemovalButton;

        HBox purchaseControlBox = new HBox(purchaseQuantitySpinner,
            cartRemovalButton);
        purchaseControlBox.setSpacing(16);
        purchaseControlBoxes[index] = purchaseControlBox;

        VBox articleBox = new VBox(nameLabel, priceLabel, addToCartButton);

        articleBox.setSpacing(16);
        articleBox.setMaxWidth(400);
        goodsPane.getChildren().add(articleBox);
    }

    public void addArticles(Article[] articles) {
        goodsPane.getChildren().clear();
        addToCartButtons = new Button[articles.length];
        purchaseControlBoxes = new HBox[articles.length];
        cartRemovalButtons = new Button[articles.length];

        for(int i = 0; i < articles.length; i++) {
            addArticleInShop(articles[i], i);
        }
    }

    public void onMouseClick(MouseEvent event) {
        Object source = event.getSource();

        for(int i = 0; i < addToCartButtons.length; i++) {
            Pane goodPane = (Pane)goodsPane.getChildren().get(i);
            List<Node> goodPaneChilds = goodPane.getChildren();

            if(source == addToCartButtons[i]) {
                goodPaneChilds.remove(2);
                goodPaneChilds.add(purchaseControlBoxes[i]);
            }

            if(source == cartRemovalButtons[i]) {
                goodPaneChilds.remove(2);
                goodPaneChilds.add(addToCartButtons[i]);
            }
        }
    }

    public void show(Scene scene) {
        scene.setRoot(mainBox);
    }

    public Scene firstShow() {
        return new Scene(mainBox);
    }
}
