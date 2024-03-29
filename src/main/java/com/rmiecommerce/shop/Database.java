package com.rmiecommerce.shop;

import com.rmiecommerce.CommonDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
    private Connection connection;

    public Database(String dbPath) {
        connection = CommonDatabase.getConnection(dbPath);
    }

    public RemoteArticle[] getArticles() {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "select * from item");

            ResultSet rs = stmt.executeQuery();
            ArrayList<RemoteArticle> remoteArticles = new ArrayList<>();

            while(rs.next()) {
                int remoteArticleId = rs.getInt(1);
                String articleName = rs.getString(2);
                double articlePrice = rs.getDouble(3);

                remoteArticles.add(new RemoteArticle(remoteArticleId, articleName,
                    articlePrice));
            }

            RemoteArticle[] res = new RemoteArticle[remoteArticles.size()];
            for(int i = 0; i < res.length; i++) res[i] = remoteArticles.get(i);

            return res;
        } catch(SQLException exception) {
            return null;
        }
    }

    public RemoteCartEntry[] getCart() {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "select * from cart");

            ResultSet rs = stmt.executeQuery();
            ArrayList<RemoteCartEntry> remoteCart = new ArrayList<>();

            while(rs.next()) {
                int remoteArticleId = rs.getInt(1);
                int purchaseQuantity = rs.getInt(2);

                remoteCart.add(new RemoteCartEntry(remoteArticleId,
                    purchaseQuantity));
            }

            RemoteCartEntry[] res = new RemoteCartEntry[remoteCart.size()];
            for(int i = 0; i < res.length; i++) res[i] = remoteCart.get(i);

            return res;
        } catch(SQLException exception) {
            return null;
        }
    }

    private void addInCart(int remoteId, int quantity) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "insert into cart values(?, ?)");

            stmt.setInt(1, remoteId);
            stmt.setInt(2, quantity);
            stmt.execute();
        } catch(SQLException ignored) {}
    }

    private void editCart(int remoteId, int quantity) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "update cart set purchaseQuantity = ? where articleId = ?");

            stmt.setInt(1, quantity);
            stmt.setInt(2, remoteId);
            stmt.execute();
        } catch(SQLException ignored) {ignored.printStackTrace();}
    }

    private void deleteFromCart(int remoteId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "delete from cart where articleId = ?");

            stmt.setInt(1, remoteId);
            stmt.execute();
        } catch(SQLException ignored) {}
    }

    public void setArticlePurchaseQuantity(int remoteId, int quantity) {
        if(quantity == 0) {
            deleteFromCart(remoteId);
        } else {
            try {
                PreparedStatement stmt = connection.prepareStatement(
                    "select articleId from cart where articleId = ?");

                stmt.setInt(1, remoteId);
                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    editCart(remoteId, quantity);
                } else {
                    addInCart(remoteId, quantity);
                }
            } catch(SQLException ignored) {}
        }
    }

    public float getCartTotalPrice() {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "select sum(price * purchaseQuantity) from item inner join " +
                "cart on id = articleId");

            ResultSet rs = stmt.executeQuery();
            rs.next();

            return rs.getFloat(1);
        } catch(SQLException exception) {
            return 0;
        }
    }

    public void clearCart() {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "delete from cart");

            stmt.execute();
        } catch(SQLException exception) {exception.printStackTrace();}
    }
}
