package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.SickState;
import com.zhaniya.finalproject.model.pet.TiredState;
import com.zhaniya.finalproject.model.pet.HappyState;

public class GiveToyCommand implements Command {
    private final Pet pet;

    public GiveToyCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null || pet.getState() == null) {
            System.out.println("Ошибка: питомец или его состояние не определены.");
            return;
        }

        // 🧸 Если питомец болен
        if (pet.getState() instanceof SickState) {
            System.out.println(pet.getName() + " болен, но обрадовался игрушке. Немного повеселел.");
            pet.setMood("Слаб, но благодарен");
            pet.setHealth(pet.getHealth() + 5);
            return;
        }

        // 😴 Уставший питомец
        if (pet.getState() instanceof TiredState) {
            System.out.println(pet.getName() + " слишком устал играть, но игрушка его немного приободрила.");
            pet.setMood("Устал, но рад");
            pet.setEnergy(pet.getEnergy() + 5);
            return;
        }

        // 😻 В обычном или хорошем состоянии
        pet.setMood("В восторге от игрушки!");
        pet.setEnergy(pet.getEnergy() + 10);
        System.out.println(pet.getName() + " играет с новой игрушкой и веселится! Настроение: " + pet.getMood());

        // 🎉 Можно перевести в HappyState, если не был
        if (!(pet.getState() instanceof HappyState)) {
            pet.setState(new HappyState(pet));
        }

        // 🎨 UI / звук (если нужно)
        // GameUI.showAction("giveToy");
        // SoundManager.play("toy.wav");
    }
}
