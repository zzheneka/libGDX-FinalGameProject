package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.SickState;
import com.zhaniya.finalproject.model.pet.SleepingState;

public class SleepCommand implements Command {
    private final Pet pet;

    public SleepCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null || pet.getState() == null) {
            System.out.println("Ошибка: питомец или его состояние не определены.");
            return;
        }

        // 😴 Если уже спит — просто сообщение
        if (pet.getState() instanceof SleepingState) {
            System.out.println(pet.getName() + " уже спит...");
            return;
        }


        if (pet.getState() instanceof SickState) {
            System.out.println(pet.getName() + " болеет, но всё равно старается уснуть.");
            // можно всё равно дать возможность спать — не блокировать
        }


        pet.getState().sleep(pet);
        System.out.println(pet.getName() + " лёг спать. Настроение: " + pet.getMood());


    }
}
