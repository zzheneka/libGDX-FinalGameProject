package com.zhaniya.finalproject.model.pet;

public class Pet {
    private String name;
    private PetType type;
    private int energy;
    private int mood;
    private int health;

    public Pet(String name, PetType type, int energy, int mood, int health) {
        this.name = name;
        this.type = type;
        this.energy = energy;
        this.mood = mood;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public PetType getType() {
        return type;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMood() {
        return mood;
    }

    public int getHealth() {
        return health;
    }

    public void printStats() {
        System.out.println(name + " [" + type + "] - Energy: " + energy +
            ", Mood: " + mood + ", Health: " + health);
    }
}
