package com.zhaniya.finalproject.utils;

import com.badlogic.gdx.utils.Timer;
import com.zhaniya.finalproject.model.pet.Pet;

public class TimerUtil {

    private static final float UPDATE_INTERVAL = 60f; // 60 секунд = 1 минута

    public static void startPetTimer(Pet pet) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (pet != null && pet.getState() != null) {
                    pet.getState().handle(); // Автоматически обновляем состояние
                }
            }
        }, 0, UPDATE_INTERVAL); // запуск сразу, потом каждые 60 секунд
    }
}
