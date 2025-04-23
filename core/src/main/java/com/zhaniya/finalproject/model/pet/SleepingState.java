package com.zhaniya.finalproject.model.pet;

public class SleepingState extends PetState {

    public SleepingState(Pet pet) {
        super(pet);
    }

    @Override
    public void handle() {
        pet.setEnergy(pet.getEnergy() + 30);
        if (pet.getEnergy() >= 80) {
            pet.setState(new HappyState(pet));
            System.out.println(pet.getName() + " выспался и проснулся!");
        } else {
            pet.setMood("Спит");
            System.out.println(pet.getName() + " спит и восстанавливает силы.");
        }
    }

    @Override
    public void feed(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 5);
        pet.setMood("Спит");
        System.out.println(pet.getName() + " немного поел, но продолжает спать.");
    }

    @Override
    public void play(Pet pet) {
        pet.setEnergy(pet.getEnergy() - 10);
        pet.setMood("Раздражен");
        System.out.println(pet.getName() + " не хочет играть, он спит.");

         pet.setState(new AngryState(pet));
    }

    @Override
    public void sleep(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 40);
        pet.setMood("Спит");
        System.out.println(pet.getName() + " спит и восстанавливает энергию.");
    }

    @Override
    public String getMood() {
        return "Спит";
    }
}
