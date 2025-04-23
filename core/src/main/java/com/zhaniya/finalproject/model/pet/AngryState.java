package com.zhaniya.finalproject.model.pet;

public class AngryState extends PetState {

    public AngryState(Pet pet) {
        super(pet);
    }

    @Override
    public void handle() {
        pet.setEnergy(pet.getEnergy() - 15);
        pet.setMood("Злой");
        System.out.println(pet.getName() + " злится и теряет много энергии!");

        // Автоматический переход в SleepingState, если энергия низкая
        if (pet.getEnergy() <= 20) {
            pet.setState(new SleepingState(pet));
            System.out.println(pet.getName() + " устал от злости и заснул.");
        }
    }

    @Override
    public void feed(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 10);
        pet.setMood("Смягчился");
        pet.updateLastFedTime(); // ✅
        System.out.println(pet.getName() + " немного успокоился после еды.");
        pet.setState(new HappyState(pet));
    }


    @Override
    public void play(Pet pet) {
        pet.setEnergy(pet.getEnergy() - 20);
        pet.setMood("Бешеный");
        System.out.println(pet.getName() + " злится ещё больше и не хочет играть!");
    }

    @Override
    public void sleep(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 20);
        pet.setMood("Успокаивается");
        System.out.println(pet.getName() + " понемногу успокаивается во сне.");

        pet.setState(new SleepingState(pet));
    }

    @Override
    public String getMood() {
        return "Злой";
    }
}
