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
}
