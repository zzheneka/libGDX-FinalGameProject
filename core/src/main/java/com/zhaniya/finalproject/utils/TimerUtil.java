package com.zhaniya.finalproject.utils;
import com.zhaniya.finalproject.model.pet.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil { //singleton pattern

    private static TimerUtil instance;
    private Timer timer;
    private static final int UPDATE_INTERVAL = 60 * 1000; // 1 минута

    private TimerUtil() {
        // приватный конструктор
    }

    public static TimerUtil getInstance() {
        if (instance == null) {
            instance = new TimerUtil();
        }
        return instance;
    }

    public static void startPetTimer(Pet pet) {
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
