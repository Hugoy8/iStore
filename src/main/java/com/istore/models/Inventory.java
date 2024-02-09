package com.istore.models;

public class Inventory {
    private int storeId;

    // Constructeur
    public Inventory(int storeId) {
        this.storeId = storeId;
    }

    // Getter et Setter
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
