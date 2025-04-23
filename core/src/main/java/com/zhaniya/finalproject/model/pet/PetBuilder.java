package com.zhaniya.finalproject.model.pet;

public class PetBuilder {
    private String name;
    private PetType type = PetType.DOG;
    private int energy = 100;
    private int mood = 100;
    private int health = 100;

    public PetBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PetBuilder setType(PetType type) {
        this.type = type;
        return this;
    }

    public PetBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public PetBuilder setMood(int mood) {
        this.mood = mood;
        return this;
    }

    public PetBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    public Pet build() {
        return new Pet(name, type, energy, mood, health);
    }
}
