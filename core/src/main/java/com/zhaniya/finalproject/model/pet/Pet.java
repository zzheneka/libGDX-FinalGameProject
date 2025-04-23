package com.zhaniya.finalproject.model.pet;

import com.zhaniya.finalproject.utils.TimerUtil;

public class Pet {
    private String name;
    private PetType type;
    private int energy;
    private int health;
    private PetState state;
    private String mood;
    private long lastFedTime;

    public Pet(String name, PetType type, PetState state) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.energy = 100;
        this.health = 100;
        this.mood = state.getMood();
        this.lastFedTime = System.currentTimeMillis(); // отметка, когда питомец в последний раз ел

        TimerUtil.startPetTimer(this);
    }

    public void handleState() {
        if (energy < 30 && !(state instanceof TiredState)) {
            setState(new TiredState(this));
            System.out.println(name + " слишком устал и переходит в состояние Устал.");
        }

        if (health < 30 && !(state instanceof SickState)) {
            setState(new SickState(this));
            System.out.println(name + " заболел из-за плохого здоровья.");
        }

        state.handle();
    }


    public void setState(PetState state) {
        this.state = state;
        this.mood = state.getMood();
    }

    public PetState getState() {
        return state;
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

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(100, energy));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(100, health));
    }

    public String getMood() {
        return mood != null ? mood : state.getMood();
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void printStats() {
        System.out.println(name + " [" + type + "] - Энергия: " + energy +
            ", Настроение: " + getMood() + ", Здоровье: " + health);
    }

    // 🕒 Метки времени еды
    public long getLastFedTime() {
        return lastFedTime;
    }

    public void updateLastFedTime() {
        this.lastFedTime = System.currentTimeMillis();
    }
}
