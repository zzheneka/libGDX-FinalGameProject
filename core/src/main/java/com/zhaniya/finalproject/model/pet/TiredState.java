package com.zhaniya.finalproject.model.pet;

import com.zhaniya.finalproject.utils.TimerUtil;

public class TiredState extends PetState {

    public TiredState(Pet pet) {
        super(pet);
        TimerUtil.startPetTimer(pet); // Можно оставить, если нужно
    }

    @Override
    public void handle() {
        if (pet.getEnergy() <= 20) {
            pet.setState(new SleepingState(pet));
            System.out.println(pet.getName() + " слишком устал и заснул.");
        } else {
            pet.setEnergy(pet.getEnergy() + 10);
            pet.setMood("Устал");
            System.out.println(pet.getName() + " отдыхает и восстанавливает силы.");
        }
    }

    @Override
    public void feed(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 5);
        pet.setMood("Устал");
        pet.updateLastFedTime(); // ✅
        System.out.println(pet.getName() + " поел и немного восстановил силы.");
    }


    @Override
    public void play(Pet pet) {
        pet.setEnergy(pet.getEnergy() - 15);
        pet.setMood("Устал");
        System.out.println(pet.getName() + " играет, но быстро утомляется.");
    }

    @Override
    public void sleep(Pet pet) {
        pet.setEnergy(pet.getEnergy() + 30);
        pet.setMood("Счастлив");
        pet.setState(new HappyState(pet));
        System.out.println(pet.getName() + " хорошо поспал и теперь счастлив.");
    }

    @Override
    public String getMood() {
        return "Устал";
    }
}
