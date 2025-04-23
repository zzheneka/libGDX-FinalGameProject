package com.zhaniya.finalproject.model.pet;

import com.zhaniya.finalproject.utils.TimerUtil;

public class HungryState extends PetState {

    public HungryState(Pet pet) {
        super(pet);
        TimerUtil.startPetTimer(pet);
    }

    @Override
    public void handle() {
        pet.setEnergy(pet.getEnergy() - 5);
        pet.setMood("Голоден");
        System.out.println(pet.getName() + " голоден и нуждается в кормлении.");
    }

    @Override
    public void feed(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 20);
        pet.setMood("Насытился");
        pet.updateLastFedTime(); // ✅
        System.out.println(pet.getName() + " сыт и полон сил!");
        pet.setState(new HappyState(pet));
    }

    @Override
    public void play(Pet pet) {
        pet.setEnergy(pet.getEnergy() - 10);
        pet.setMood("Голоден");
        System.out.println(pet.getName() + " не может играть, потому что голоден.");
    }

    @Override
    public void sleep(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 10);
        pet.setMood("Голоден");
        System.out.println(pet.getName() + " продолжает чувствовать голод.");
    }

    @Override
    public String getMood() {
        return "Голоден";
    }
}
