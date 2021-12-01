package com.rmiecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection(String dbPath) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch(SQLException sqlException) {
            System.err.println("Erreur d'ouverture de la base de données : " +
                sqlException.getMessage());
            System.exit(1);
        }

        return null;
    }
}
