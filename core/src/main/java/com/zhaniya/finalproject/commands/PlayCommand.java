package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.TiredState;
import com.zhaniya.finalproject.model.pet.SickState;

public class PlayCommand implements Command {
    private final Pet pet;

    public PlayCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null || pet.getState() == null) {
            System.out.println("Ошибка: питомец или его состояние не определены.");
            return;
        }

        // ⚠️ Проверка: нельзя играть, если питомец болен или устал
        if (pet.getState() instanceof SickState) {
            System.out.println(pet.getName() + " болеет и не может играть сейчас.");
            return;
        }

        if (pet.getState() instanceof TiredState) {
            System.out.println(pet.getName() + " слишком устал, чтобы играть.");
            return;
        }

        // 🎮 Игра
        pet.getState().play(pet);
        System.out.println(pet.getName() + " поиграл с тобой! Настроение: " + pet.getMood());

        // 🎨 Дополнительно: можно включить анимацию/звук/UI
        // GameUI.showAction("play");
        // SoundManager.play("play.wav");
    }
}
