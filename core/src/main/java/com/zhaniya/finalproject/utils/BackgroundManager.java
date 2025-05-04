package com.zhaniya.finalproject.utils;

import com.badlogic.gdx.graphics.Texture;

public class BackgroundManager {

    private Texture defaultBackground;
    private Texture kitchenBackground;
    private Texture playgroundBackground;
    private Texture sleepBackground;

    private Texture currentBackground;

    public BackgroundManager() {
        // Загружаем все фоны один раз при создании
        defaultBackground = new Texture("backgrounds/default_room.png");
        kitchenBackground = new Texture("backgrounds/kitchen.png");
        playgroundBackground = new Texture("backgrounds/playground.png");
        sleepBackground = new Texture("backgrounds/sleep_with_dragon.png"); // <-- ОБНОВЛЕННЫЙ ПУТЬ

        currentBackground = defaultBackground; // фон по умолчанию
    }

    public void setDefaultBackground() {
        currentBackground = defaultBackground;
    }

    public void setKitchenBackground() {
        currentBackground = kitchenBackground;
    }

    public void setPlaygroundBackground() {
        currentBackground = playgroundBackground;
    }

    public void setBedroomBackground() {
        currentBackground = sleepBackground; // <-- используется при сне
    }

    public Texture getCurrentBackground() {
        return currentBackground;
    }

    public void dispose() {
        defaultBackground.dispose();
        kitchenBackground.dispose();
        playgroundBackground.dispose();
        sleepBackground.dispose();
    }
}
