package com.istore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection connectionDb = null;

    public synchronized Connection getConnectionDb() {
        try {
            if (this.connectionDb == null || this.connectionDb.isClosed()) {
                this.connectToDb();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la vérification de l'état de la connexion à la base de données.");
        }
        return this.connectionDb;
    }

    private void connectToDb() {
        String url = System.getenv("DB_URL") + System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connectionDb = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur pendant la connexion à la base de donnée.");
            System.exit(0);
        }
    }

    // Ajoutez une méthode pour fermer la connexion si nécessaire
    public void closeConnection() {
        if (this.connectionDb != null) {
            try {
                this.connectionDb.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}