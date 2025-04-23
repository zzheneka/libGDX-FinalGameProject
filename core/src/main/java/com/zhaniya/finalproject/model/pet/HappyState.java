package com.zhaniya.finalproject.model.pet;

import com.zhaniya.finalproject.utils.TimerUtil;

public class HappyState extends PetState {

    public HappyState(Pet pet) {
        super(pet);
        TimerUtil.startPetTimer(pet); // Запуск таймера, если реализован
    }

    @Override
    public void handle() {
        pet.setEnergy(pet.getEnergy() + 20);
        pet.setMood("Счастлив");
        System.out.println(pet.getName() + " счастлив и полон энергии!");
    }

    @Override
    public void feed(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 10);
        pet.setMood("Счастлив");
        System.out.println(pet.getName() + " снова счастлив и полон сил!");
    }

    @Override
    public void play(Pet pet) {
        pet.setEnergy(pet.getEnergy() - 10);
        pet.setMood("Веселый");
        System.out.println(pet.getName() + " веселится и играет!");
    }

    @Override
    public void sleep(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 30);
        pet.setMood("Счастлив");
        System.out.println(pet.getName() + " отдыхает и восстанавливает силы!");
    }

    @Override
    public String getMood() {
        return "Счастлив";
    }
}
