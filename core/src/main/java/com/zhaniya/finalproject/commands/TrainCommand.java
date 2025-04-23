package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.SickState;
import com.zhaniya.finalproject.model.pet.TiredState;
import com.zhaniya.finalproject.model.pet.AngryState;
import com.zhaniya.finalproject.model.pet.HappyState;

public class TrainCommand implements Command {
    private final Pet pet;

    public TrainCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null || pet.getState() == null) {
            System.out.println("Ошибка: питомец не готов к обучению.");
            return;
        }

        // ⚠️ Нельзя тренировать, если состояние не позволяет
        if (pet.getState() instanceof SickState) {
            System.out.println(pet.getName() + " болен и не может тренироваться.");
            return;
        }

        if (pet.getState() instanceof TiredState) {
            System.out.println(pet.getName() + " слишком устал для тренировки.");
            return;
        }

        if (pet.getState() instanceof AngryState) {
            System.out.println(pet.getName() + " не в настроении для обучения.");
            return;
        }

        // ✅ Обучение возможно
        pet.setEnergy(pet.getEnergy() - 15);
        pet.setMood("Сконцентрирован");
        pet.increaseIntelligence(5); // 🧠 повышаем интеллект
        System.out.println(pet.getName() + " прошёл тренировку и стал умнее! Энергия: " + pet.getEnergy());

        // Переводим в HappyState при успешной тренировке
        pet.setState(new HappyState(pet));

        // 🎨 UI / звук (по желанию)
        // GameUI.showAction("train");
        // SoundManager.play("train.wav");
    }
}
