package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.HappyState;
import com.zhaniya.finalproject.model.pet.PetState;

public class FeedCommand implements Command {
    private final Pet pet;

    public FeedCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null || pet.getState() == null) {
            System.out.println("Ошибка: питомец или его состояние не определены.");
            return;
        }

        // Защита от перекорма (меньше 30 сек с последней еды)
        long now = System.currentTimeMillis();
        if (now - pet.getLastFedTime() < 30_000) {
            System.out.println(pet.getName() + " не голоден прямо сейчас. Подожди немного.");
            return;
        }

        // Дополнительно: не кормим, если он уже счастлив
        if (pet.getState() instanceof HappyState) {
            System.out.println(pet.getName() + " уже сыт и доволен!");
        }

        // Выполняем кормление
        PetState state = pet.getState();
        state.feed(pet);
        pet.updateLastFedTime(); // фиксируем время еды

        // Сообщение
        System.out.println(pet.getName() + " был покормлен! Настроение: " + pet.getMood());

        // Здесь можно добавить: анимации, звук, UI-обновление и т.д.
        // GameUI.updateMoodDisplay(pet.getMood());
        // SoundManager.play("eat.wav");
    }
}
