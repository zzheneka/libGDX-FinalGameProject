package com.zhaniya.finalproject.model.pet;

import com.zhaniya.finalproject.utils.TimerUtil;

public class SickState extends PetState {

    public SickState(Pet pet) {
        super(pet);
        // TimerUtil.startPetTimer(pet); // если нужно
    }

    @Override
    public void handle() {
        pet.setEnergy(pet.getEnergy() - 5);
        pet.setMood("Болен");
        System.out.println(pet.getName() + " чувствует себя " + pet.getMood() + " и теряет немного энергии.");
    }

    @Override
    public void feed(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 10);
        pet.setMood("Чувствует себя лучше");
        System.out.println(pet.getName() + " чувствует себя лучше после еды!");

        // Переход в счастливое состояние
        pet.setState(new HappyState(pet));
    }

    @Override
    public void play(Pet pet) {
        pet.setEnergy(pet.getEnergy() - 15);
        pet.setMood("Болен");
        System.out.println(pet.getName() + " не может играть, потому что болен.");
    }

    @Override
    public void sleep(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 10);
        pet.setMood("Болен");
        System.out.println(pet.getName() + " отдыхает, но все еще чувствует себя больным.");
    }

    @Override
    public String getMood() {
        return "Болен";
    }
}
