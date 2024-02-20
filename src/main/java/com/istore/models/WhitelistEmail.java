package com.istore.models;

public class WhitelistEmail {
    private int id;
    private String email;

    // Constructeur, getters et setters
    public WhitelistEmail(int id, String email) {
        this.id = id;
        this.email = email;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
