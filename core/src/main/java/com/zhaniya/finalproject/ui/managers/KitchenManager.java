package com.zhaniya.finalproject.ui.managers;



public class KitchenManager {
    private Fridge fridge;

    public KitchenManager() {
        fridge = new Fridge();
    }

    public void openFridge() {
        fridge.open();
        System.out.println("Холодильник открыт.");
    }

    public void closeFridge() {
        fridge.close();
        System.out.println("Холодильник закрыт.");
    }

    public boolean isFridgeOpen() {
        return fridge.isOpen();
    }

    public void useFood(String itemName) {
        fridge.useItem(itemName);
    }

    public Fridge getFridge() {
        return fridge;
    }
}
