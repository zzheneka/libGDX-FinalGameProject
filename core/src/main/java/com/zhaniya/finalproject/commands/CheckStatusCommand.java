package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;

public class CheckStatusCommand implements Command {
    private final Pet pet;

    public CheckStatusCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null) {
            System.out.println("Ошибка: питомец не найден.");
            return;
        }

        System.out.println("🐾 Статус питомца:");
        System.out.println("Имя: " + pet.getName());
        System.out.println("Тип: " + pet.getType());
        System.out.println("Настроение: " + pet.getMood());
        System.out.println("Энергия: " + pet.getEnergy());
        System.out.println("Здоровье: " + pet.getHealth());

        // 🔍 Добавь интеллект, доверие и т.п. — если ты их реализуешь
        // System.out.println("Интеллект: " + pet.getIntelligence());
        // System.out.println("Уровень доверия: " + pet.getTrustLevel());

        System.out.println("Состояние: " + pet.getState().getClass().getSimpleName());
        System.out.println("Последний приём пищи: " + (System.currentTimeMillis() - pet.getLastFedTime()) / 1000 + " сек назад");
    }
}
