package com.zhaniya.finalproject.utils;

import com.zhaniya.finalproject.model.pet.*;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {

    private static final int UPDATE_INTERVAL = 60 * 1000; // 1 минута

    public static void startPetTimer(Pet pet) {
        Timer timer = new Timer(true); // daemon thread — не блокирует завершение программы
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                pet.handleState(); // Усталость и здоровье

                long now = System.currentTimeMillis();
                long timeSinceLastFed = now - pet.getLastFedTime();

                // Голод через 2 минуты без еды
                if (timeSinceLastFed > 120_000 && !pet.isInState(HungryState.class)) {
                    pet.setState(new HungryState(pet));
                    System.out.println(pet.getName() + " не ел уже 2 минуты и теперь голоден!");
                }

//                // Случайная болезнь — 5% шанс
//                if (Math.random() < 0.05 && !pet.isInState(SickState.class)) {
//                    pet.setState(new SickState(pet));
//                    System.out.println(pet.getName() + " внезапно заболел!");
//                }
            }
        }, 0, UPDATE_INTERVAL);
    }
}
