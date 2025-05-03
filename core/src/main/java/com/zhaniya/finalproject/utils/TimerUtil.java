package com.zhaniya.finalproject.utils;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.zhaniya.finalproject.model.pet.*;

public class TimerUtil {

    private static final float UPDATE_INTERVAL = 60f;

    public static void startPetTimer(Pet pet) {
        Timer.schedule(new Task() {
            @Override
            public void run() {
                pet.handleState();

                long now = System.currentTimeMillis();
                long timeSinceLastFed = now - pet.getLastFedTime();

                if (timeSinceLastFed > 120_000 && !(pet.getState() instanceof HungryState)) {
                    pet.setState(new HungryState(pet));
                    System.out.println(pet.getName() + " не ел уже 2 минуты и теперь голоден!");
                }

                if (Math.random() < 0.05 && !(pet.getState() instanceof SickState)) {
                    pet.setState(new SickState(pet));
                    System.out.println(pet.getName() + " внезапно заболел!");
                }
            }
        }, 0, UPDATE_INTERVAL);
    }
}
