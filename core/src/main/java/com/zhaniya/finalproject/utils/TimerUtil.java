package com.zhaniya.finalproject.utils;

import com.zhaniya.finalproject.model.pet.*;
import com.zhaniya.finalproject.model.pet.HungryState;
import com.zhaniya.finalproject.model.pet.SleepingState;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil { // Singleton Pattern

    private static TimerUtil instance;
    private Timer timer;
    private static final int UPDATE_INTERVAL = 60 * 1000; // 1 минута
    private static final int ENERGY_DECREASE_ON_PLAY = 5; // Потеря энергии при игре
    private static final int ENERGY_INCREASE_ON_FEED = 20; // Восстановление энергии при кормлении
    private static final int SLEEP_THRESHOLD = 10; // Если энергия <= 10, питомец засыпает

    private TimerUtil() {}

    public static TimerUtil getInstance() {
        if (instance == null) {
            instance = new TimerUtil();
        }
        return instance;
    }

    public void start(Pet pet) {
        stop();

        timer = new Timer(true); // daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pet.handleState();

                long now = System.currentTimeMillis();
                long timeSinceLastFed = now - pet.getLastFedTime();

                // Проверка на голод
                if (timeSinceLastFed > 120_000 && !pet.isInState(HungryState.class)) {
                    pet.setState(new HungryState(pet));
                    System.out.println(pet.getName() + " голоден! Не ел уже 2 минуты.");
                }

                // Потеря энергии при игре
                if (pet.isPlaying()) {
                    pet.decreaseEnergy(ENERGY_DECREASE_ON_PLAY);
                    System.out.println(pet.getName() + " играет и теряет энергию!");
                }

                // Автоматический сон при низкой энергии
                if (pet.getEnergy() <= SLEEP_THRESHOLD && !pet.isInState(SleepingState.class)) {
                    pet.setState(new SleepingState(pet));
                    System.out.println(pet.getName() + " устал и засыпает автоматически!");
                }

                // Восстановление энергии при кормлении
                if (pet.isFed()) {
                    pet.increaseEnergy(ENERGY_INCREASE_ON_FEED);
                    System.out.println(pet.getName() + " поел и восстановил энергию!");
                }
            }
        }, 0, UPDATE_INTERVAL);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public boolean isRunning() {
        return timer != null;
    }
}
