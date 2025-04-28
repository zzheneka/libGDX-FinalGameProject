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

    // Новые параметры
    private int intelligence;
    private int trustLevel;
    private int level;

    public Pet(String name, PetType type, PetState state) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.energy = 100;
        this.health = 100;

        // ✅ Безопасная инициализация настроения
        this.mood = (state != null) ? state.getMood() : "Нейтральный";
        this.lastFedTime = System.currentTimeMillis();

        this.intelligence = 0;
        this.trustLevel = 0;
        this.level = 1;

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
        return mood != null ? mood : (state != null ? state.getMood() : "Неизвестно");
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public long getLastFedTime() {
        return lastFedTime;
    }

    public void updateLastFedTime() {
        this.lastFedTime = System.currentTimeMillis();
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void increaseIntelligence(int amount) {
        this.intelligence = Math.min(100, intelligence + amount);
        checkLevelUp();
    }

    public int getTrustLevel() {
        return trustLevel;
    }

    public void increaseTrust(int amount) {
        this.trustLevel = Math.min(100, trustLevel + amount);
        checkLevelUp();
    }

    public int getLevel() {
        return level;
    }

    private void checkLevelUp() {
        if (intelligence >= 20 && trustLevel >= 20 && level == 1) {
            level = 2;
            System.out.println(name + " повысил уровень до 2! 🎉");
        } else if (intelligence >= 50 && trustLevel >= 50 && level == 2) {
            level = 3;
            System.out.println(name + " повысил уровень до 3! 🌟");
        }

    }

    public void printStats() {
        System.out.println(name + " [" + type + "] - Энергия: " + energy +
            ", Настроение: " + getMood() + ", Здоровье: " + health +
            ", Уровень: " + level + ", Интеллект: " + intelligence + ", Доверие: " + trustLevel);
    }
}
