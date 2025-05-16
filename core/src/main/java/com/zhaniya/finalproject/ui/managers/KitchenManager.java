package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.zhaniya.finalproject.model.FoodItem;

import java.util.HashMap;
import java.util.Map;

public class KitchenManager {
    private final Map<String, FoodItem> fridge;
    private boolean fridgeOpen;

    public KitchenManager() {
        fridge = new HashMap<>();
        addFood("Apple", 5, "ui/food/apple.png");
        addFood("Juice", 3, "ui/food/juice.png");
        addFood("Milk", 2, "ui/food/milk.png");
        fridgeOpen = false;
    }

    private void addFood(String name, int quantity, String path) {
        fridge.put(name, new FoodItem(name, quantity, path));
        System.out.println("Загружено: " + name);
    }

    public Map<String, FoodItem> getFridge() {
        return fridge;
    }

    public boolean consumeItem(String itemName) {
        FoodItem item = fridge.get(itemName);
        if (item != null && item.getQuantity() > 0) {
            item.setQuantity(item.getQuantity() - 1);
            System.out.println("Питомец съел: " + itemName + ". Осталось: " + item.getQuantity());
            return true;
        }
        System.out.println("Еда закончилась: " + itemName);
        return false;
    }

    public void toggleFridgeState() {
        fridgeOpen = !fridgeOpen;
        System.out.println("Холодильник " + (fridgeOpen ? "открыт!" : "закрыт!"));
    }

    public boolean isFridgeOpen() {
        return fridgeOpen;
    }
}
