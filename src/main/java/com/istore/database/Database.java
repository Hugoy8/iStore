package com.istore.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private Connection connectionDb = null;

    public Connection getConnectionDb(){
        if (this.connectionDb == null){
            this.connectToDb();
        }
        return this.connectionDb;
    }

    public void connectToDb(){
        String BDD = System.getenv("DB_NAME");
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connectionDb = DriverManager.getConnection(url, user, password);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
        }
    }

}