package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.zhaniya.finalproject.model.FoodItem;

import java.util.HashMap;
import java.util.Map;

public class KitchenManager {
    private final Map<String, FoodItem> fridge;
    private boolean fridgeOpen;

    public KitchenManager() {
        fridge = new HashMap<>();
        addFood("Apple", 5, "ui/food/apple.png");
        addFood("Juice", 2, "ui/food/juice.png");
        addFood("Milk", 3, "ui/food/milk.png");
        fridgeOpen = false;
    }

    private void addFood(String name, int quantity, String path) {
        if (fileExists(path)) {
            fridge.put(name, new FoodItem(name, quantity, path));
            System.out.println("Загружено: " + name);
        } else {
            System.out.println("Ошибка: файл не найден - " + path);
        }
    }

    private boolean fileExists(String path) {
        FileHandle file = Gdx.files.internal(path);
        return file.exists();
    }

    public Map<String, FoodItem> getFridge() {
        return fridge;
    }

    public void openFridge() {
        fridgeOpen = true;
        System.out.println("Холодильник открыт.");
    }

    public void closeFridge() {
        fridgeOpen = false;
        System.out.println("Холодильник закрыт.");
    }

    public boolean isFridgeOpen() {
        return fridgeOpen;
    }

    public boolean consumeItem(String itemName) {
        FoodItem item = fridge.get(itemName);
        if (item != null && item.getQuantity() > 0) {
            item.setQuantity(item.getQuantity() - 1);
            System.out.println("Съеден продукт: " + itemName);
            return true;
        }
        System.out.println("Продукт закончился: " + itemName);
        return false;
    }
}
