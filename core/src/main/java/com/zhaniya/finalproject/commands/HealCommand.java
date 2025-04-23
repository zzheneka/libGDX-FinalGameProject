package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.SickState;
import com.zhaniya.finalproject.model.pet.HappyState;

public class HealCommand implements Command {
    private final Pet pet;

    public HealCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null || pet.getState() == null) {
            System.out.println("Ошибка: питомец или его состояние не определены.");
            return;
        }

        // ✅ Если питомец болен — лечим его
        if (pet.getState() instanceof SickState) {
            pet.setHealth(pet.getHealth() + 20);
            pet.setMood("Выздоравливает");
            pet.setState(new HappyState(pet)); // Переход в Happy после лечения
            System.out.println(pet.getName() + " получил лечение и чувствует себя лучше! Здоровье: " + pet.getHealth());
        } else {
            // 🧠 Не болен — не нужно лечить
            System.out.println(pet.getName() + " здоров и не нуждается в лечении.");
        }

        // 🎨 UI-реакции можно добавить:
        // GameUI.showAction("heal");
        // SoundManager.play("heal.wav");
    }
}
