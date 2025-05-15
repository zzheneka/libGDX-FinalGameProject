package com.zhaniya.finalproject.model;

public class FoodItem {
    private String name;
    private int quantity;

    public FoodItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void useItem() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public boolean isAvailable() {
        return quantity > 0;
    }
}
