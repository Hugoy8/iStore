package com.istore.models;

public class Item {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int inventoryId;

    // Constructeur
    public Item(int id, String name, double price, int quantity, int inventoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.inventoryId = inventoryId;
    }

    public Item(String name, double price, int quantity, int inventoryId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.inventoryId = inventoryId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
}
