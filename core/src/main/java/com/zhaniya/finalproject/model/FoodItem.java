package com.zhaniya.finalproject.model;

public class FoodItem {
    private String name;
    private int quantity;
    private String texturePath;

    public FoodItem(String name, int quantity, String texturePath) {
        this.name = name;
        this.quantity = quantity;
        this.texturePath = texturePath;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTexturePath() {
        return texturePath;
    }

    // Метод проверки доступности
    public boolean isAvailable() {
        return quantity > 0;
    }

    // Метод использования предмета
    public void consume() {
        if (isAvailable()) {
            quantity--;
            System.out.println(name + " использован. Осталось: " + quantity);
        } else {
            System.out.println(name + " закончился.");
        }
    }
}
