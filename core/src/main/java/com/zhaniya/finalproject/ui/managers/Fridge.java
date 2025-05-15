package com.zhaniya.finalproject.ui.managers;


import com.zhaniya.finalproject.model.FoodItem;
import java.util.HashMap;

public class Fridge {
    private HashMap<String, FoodItem> items;
    private boolean isOpen;

    public Fridge() {
        items = new HashMap<>();
        isOpen = false;
        // Инициализация продуктов в холодильнике
        items.put("Milk", new FoodItem("Milk", 3));
        items.put("Apple", new FoodItem("Apple", 5));
        items.put("Juice", new FoodItem("Juice", 2));
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public HashMap<String, FoodItem> getItems() {
        return items;
    }

    public void useItem(String itemName) {
        FoodItem item = items.get(itemName);
        if (item != null && item.isAvailable()) {
            item.useItem();
            System.out.println("Использован продукт: " + itemName + ". Осталось: " + item.getQuantity());
        } else {
            System.out.println("Продукт закончился: " + itemName);
        }
    }
}
