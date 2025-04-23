package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.SickState;
import com.zhaniya.finalproject.model.pet.AngryState;
import com.zhaniya.finalproject.model.pet.SleepingState;

public class PetCommand implements Command {
    private final Pet pet;

    public PetCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null || pet.getState() == null) {
            System.out.println("Ошибка: питомец или его состояние не определены.");
            return;
        }

        // 😴 Не беспокоим во сне
        if (pet.getState() instanceof SleepingState) {
            System.out.println(pet.getName() + " спит. Лучше не трогать сейчас...");
            return;
        }

        // 😠 Если злой — может не понравиться
        if (pet.getState() instanceof AngryState) {
            pet.setMood("Немного смягчился");
            pet.setEnergy(pet.getEnergy() + 5);
            pet.increaseTrust(2); // 🫂 +доверие
            System.out.println(pet.getName() + " был немного успокоен поглаживанием.");
            return;
        }

        // 🤒 Если болеет — нуждается в заботе
        if (pet.getState() instanceof SickState) {
            pet.setMood("Чувствует заботу");
            pet.setHealth(pet.getHealth() + 5);
            pet.increaseTrust(3); // ❤️ +доверие
            System.out.println(pet.getName() + " чувствует твою поддержку и немного поправился.");
            return;
        }

        // 🐾 Нормальная реакция: радуется!
        pet.setMood("Радуется ласке");
        pet.setEnergy(pet.getEnergy() + 5);
        pet.increaseTrust(1);
        System.out.println(pet.getName() + " мурчит от удовольствия 😊 Настроение: " + pet.getMood());

        // 🎨 Можно добавить:
        // GameUI.showAction("pet");
        // SoundManager.play("pet.wav");
    }
}
