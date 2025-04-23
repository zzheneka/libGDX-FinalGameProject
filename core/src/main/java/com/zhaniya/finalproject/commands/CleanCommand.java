package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.SickState;
import com.zhaniya.finalproject.model.pet.HappyState;

public class CleanCommand implements Command {
    private final Pet pet;

    public CleanCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null || pet.getState() == null) {
            System.out.println("Ошибка: питомец или его состояние не определены.");
            return;
        }

        // 🧼 Эффект от мытья
        pet.setMood("Свеж и чист");
        pet.setHealth(pet.getHealth() + 10);
        System.out.println(pet.getName() + " был помыт и теперь сияет чистотой! Настроение: " + pet.getMood());

        // 🤒 Если был болен, можно попробовать его "вылечить"
        if (pet.getState() instanceof SickState && pet.getHealth() > 50) {
            pet.setState(new HappyState(pet));
            System.out.println(pet.getName() + " выздоровел после заботы и гигиены!");
        }

        // 🎨 UI-поддержка (если будет):
        // GameUI.showAction("clean");
        // SoundManager.play("clean.wav");
    }
}
