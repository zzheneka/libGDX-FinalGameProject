package com.zhaniya.finalproject.model.items;

public class Item {
    private final String name;
    private final String type; // "food", "toy", "medicine", etc.

    public Item(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
