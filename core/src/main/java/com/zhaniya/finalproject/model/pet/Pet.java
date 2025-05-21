package com.zhaniya.finalproject.model.pet;

import com.zhaniya.finalproject.utils.TimerUtil;

public class Pet implements CloneablePet {
    private String name;
    private PetType type;
    private int energy;
    private int health;
    private PetState state;
    private String mood;
    private long lastFedTime;

    private int intelligence;
    private int trustLevel;
    private int level;
    private boolean playing;

    public Pet(String name, PetType type, PetState state) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.energy = 100;
        this.health = 100;
        this.mood = (state != null) ? state.getMood() : "Neutral";
        this.lastFedTime = System.currentTimeMillis();
        this.intelligence = 0;
        this.trustLevel = 0;
        this.level = 1;
        this.playing = false;

        TimerUtil.getInstance().start(this);
    }

    @Override
    public Pet clone() {
        Pet copy = new Pet(this.name, this.type, null);
        copy.setEnergy(this.energy);
        copy.setHealth(this.health);
        copy.setMood(this.mood);
        copy.lastFedTime = this.lastFedTime;
        copy.intelligence = this.intelligence;
        copy.trustLevel = this.trustLevel;
        copy.level = this.level;

        if (this.state != null) {
            try {
                PetState clonedState = this.state.getClass().getConstructor(Pet.class).newInstance(copy);
                copy.setState(clonedState);
            } catch (Exception e) {
                System.out.println("Failed to clone state: " + e.getMessage());
            }
        }
        return copy;
    }

    public void handleState() {
        if (energy <= 0) {
            System.out.println(name + " has no energy left!");
            return;
        }

        if (energy < 30 && !isInState(TiredState.class)) {
            setState(new TiredState(this));
            System.out.println(name + " is too tired and switches to Tired state.");
            return;
        }

        if (health < 30 && !isInState(SickState.class)) {
            setState(new SickState(this));
            System.out.println(name + " got sick due to low health.");
            return;
        }

        if (energy < 50 && !isInState(TiredState.class)) {
            setState(new TiredState(this));
            System.out.println(name + " is sad due to low energy.");
            return;
        }

        if (state != null) {
            state.handle();
        }
    }

    public void setState(PetState newState) {
        if (this.state != null && this.state.getClass().equals(newState.getClass())) return;
        this.state = newState;
        this.mood = newState.getMood();
        System.out.println(name + " switched to state: " + newState.getClass().getSimpleName());
    }

    public boolean isInState(Class<? extends PetState> stateClass) {
        return state != null && stateClass.isInstance(state);
    }

    public PetState getState() {
        return state;
    }

    public String getName() {
        return name;
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
        return mood != null ? mood : (state != null ? state.getMood() : "Unknown");
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
            System.out.println(name + " leveled up to 2! 🎉");
        } else if (intelligence >= 50 && trustLevel >= 50 && level == 2) {
            level = 3;
            System.out.println(name + " leveled up to 3! 🌟");
        }
    }

    public void printStats() {
        System.out.println(name + " [" + type + "] - Energy: " + energy +
            ", Mood: " + getMood() + ", Health: " + health +
            ", Level: " + level + ", Intelligence: " + intelligence + ", Trust: " + trustLevel);
    }

    public void feed(int energyAmount) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFedTime < 5000) {
            System.out.println(name + " is not hungry right now. Wait a bit.");
            return;
        }

        energy = Math.min(100, energy + energyAmount);
        health = Math.min(100, health + (energyAmount / 2));
        updateLastFedTime();
        System.out.println(name + " ate! Energy: " + energy + ", Health: " + health);
        handleState();
    }

    public void play() {
        if (energy < 10) {
            System.out.println(name + " is too tired to play.");
            return;
        }

        setPlaying(true);
        decreaseEnergy(10);
        increaseTrust(5);
        increaseIntelligence(3);
        System.out.println(name + " played! Energy: " + energy);
        setPlaying(false);
        handleState();
    }

    public void sleep() {
        if (energy >= 90) {
            System.out.println(name + " is not sleepy, full of energy!");
            return;
        }

        energy = Math.min(100, energy + 30);
        System.out.println(name + " slept and restored energy! Energy: " + energy);
        handleState();
    }

    public void increaseEnergy(int amount) {
        this.energy = Math.min(100, this.energy + amount);
        handleState();
    }

    public void decreaseEnergy(int amount) {
        this.energy = Math.max(0, this.energy - amount);
        handleState();
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isFed() {
        return (System.currentTimeMillis() - lastFedTime) < 5000;
    }

    public PetType getType() {
        return type;
    }
}
