package com.zhaniya.finalproject.model.pet;


public abstract class PetState {
    protected Pet pet;


        public PetState(Pet pet) {
            this.pet = pet;
        }

        public abstract void handle();
        public abstract void feed(Pet pet);
        public abstract void play(Pet pet);
        public abstract void sleep(Pet pet);
        public abstract String getMood();

        public abstract Emotion getEmotion();
    }
