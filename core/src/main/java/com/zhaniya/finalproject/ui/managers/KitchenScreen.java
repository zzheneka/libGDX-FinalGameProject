package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.FoodItem;

import java.util.Map;

public class KitchenScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private BitmapFont font;

    private KitchenManager kitchenManager;
    private AnimationManager animationManager;  // Добавил менеджер анимаций
    private Texture kitchenTexture;
    private Texture fridgeClosedTexture;
    private Texture fridgeOpenTexture;

    private Pet pet;

    public KitchenScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;

        // Инициализация менеджеров
        kitchenManager = new KitchenManager();
        animationManager = new AnimationManager(pet);  // Инициализация менеджера анимаций

        // Инициализация рендеров
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Загрузка текстур
        try {
            kitchenTexture = new Texture(Gdx.files.internal("backgrounds/kitchen.png"));
            fridgeClosedTexture = new Texture(Gdx.files.internal("ui/fridge_closed.png"));
            fridgeOpenTexture = new Texture(Gdx.files.internal("ui/fridge_open.png"));

        } catch (Exception e) {
            System.err.println("Ошибка при загрузке текстуры: " + e.getMessage());
        }
    }

    @Override
    public void show() {
        System.out.println("Переход на экран кухни.");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();

        // Отображаем фон кухни
        batch.draw(kitchenTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Логика отображения холодильника
        Texture fridgeTexture = kitchenManager.isFridgeOpen() ? fridgeOpenTexture : fridgeClosedTexture;
        batch.draw(fridgeTexture, -20, 50, fridgeTexture.getWidth() * 0.25f, fridgeTexture.getHeight() * 0.25f);

        // Анимация питомца
        animationManager.render(batch);

        // Отображение продуктов при открытом холодильнике
        if (kitchenManager.isFridgeOpen()) {
            int y = 300;
            for (Map.Entry<String, FoodItem> entry : kitchenManager.getFridge().entrySet()) {
                font.draw(batch, entry.getKey() + ": " + entry.getValue().getQuantity(), 200, y);
                y -= 30;
            }
        }

        batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Проверяем клик по холодильнику
            if (x > 20 && x < 100 && y > 50 && y < 150) {
                if (kitchenManager.isFridgeOpen()) {
                    kitchenManager.closeFridge();
                    System.out.println("Холодильник закрыт.");
                } else {
                    kitchenManager.openFridge();
                    System.out.println("Холодильник открыт.");
                }
            }
        }
    }
    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        kitchenTexture.dispose();
        fridgeClosedTexture.dispose();
        fridgeOpenTexture.dispose();
        System.out.println("Ресурсы экрана кухни очищены.");
    }
}
