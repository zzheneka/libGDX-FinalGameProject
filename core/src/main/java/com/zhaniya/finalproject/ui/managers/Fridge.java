package com.zhaniya.finalproject.ui.managers;

import com.zhaniya.finalproject.model.FoodItem;
import java.util.HashMap;
import java.util.Map;

public class Fridge {
    private final Map<String, FoodItem> items;

    public Fridge() {
        items = new HashMap<>();
        items.put("Apple", new FoodItem("Apple", 5, "ui/food/apple.png"));
        items.put("Juice", new FoodItem("Juice", 2, "ui/food/juice.png"));
        items.put("Milk", new FoodItem("Milk", 3, "ui/food/milk.png"));
    }

    public Map<String, FoodItem> getItems() {
        return items;
    }

    public boolean useItem(String itemName) {
        FoodItem item = items.get(itemName);
        if (item != null && item.isAvailable()) {
            item.consume();
            return true;
        }
        System.out.println("Продукт не найден или закончился: " + itemName);
        return false;
    }
}
