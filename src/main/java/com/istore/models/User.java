package com.istore.models;

public class User {
    private int id;
    private String email;
    private String pseudo;
    private String passwordHash;
    private String role;

    // Constructeurs
    public User() {}

    public User(int id, String email, String pseudo, String passwordHash, String role) {
        this.id = id;
        this.email = email;
        this.pseudo = pseudo;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Getters et setters
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
