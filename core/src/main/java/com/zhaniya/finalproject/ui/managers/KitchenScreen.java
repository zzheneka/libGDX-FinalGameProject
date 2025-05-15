package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.GameScreen;
import com.zhaniya.finalproject.model.FoodItem;
import com.zhaniya.finalproject.ui.managers.AnimationManager;
import java.util.Map;

public class KitchenScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private BitmapFont font;

    private KitchenManager kitchenManager;
    private AnimationManager animationManager;
    private Texture kitchenTexture;
    private Texture fridgeClosedTexture;
    private Texture fridgeOpenTexture;

    private Pet pet;

    public KitchenScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;

        // Инициализация менеджеров
        kitchenManager = new KitchenManager();
        animationManager = new AnimationManager(pet);

        // Инициализация рендеров
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Загрузка текстур
        kitchenTexture = new Texture("backgrounds/kitchen.png");
        fridgeClosedTexture = new Texture("ui/fridge_closed.png");
        fridgeOpenTexture = new Texture("ui/fridge_open.png");
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

        // Отображаем холодильник с уменьшенным размером
        Texture fridgeTexture = kitchenManager.isFridgeOpen() ? fridgeOpenTexture : fridgeClosedTexture;
        batch.draw(fridgeTexture, 50, 100, fridgeTexture.getWidth() * 0.6f, fridgeTexture.getHeight() * 0.6f);

        // Отображаем продукты, если холодильник открыт
        if (kitchenManager.isFridgeOpen()) {
            int y = 300;
            for (Map.Entry<String, FoodItem> entry : kitchenManager.getFridge().getItems().entrySet()) {
                font.draw(batch, entry.getKey() + ": " + entry.getValue().getQuantity(), 200, y);
                y -= 20;
            }
        }

        // Отображаем дракона
        TextureRegion currentFrame = animationManager.getCurrentFrame();
        batch.draw(currentFrame, 300, 50, 180, 180); // Позиция дракона на кухне

        batch.end();

        // Обработка нажатий на экран
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Клик по холодильнику
            if (x > 50 && x < 140 && y > 100 && y < 250) {
                if (kitchenManager.isFridgeOpen()) {
                    kitchenManager.closeFridge();
                    System.out.println("Холодильник закрыт.");
                } else {
                    kitchenManager.openFridge();
                    System.out.println("Холодильник открыт.");
                }
            } else {
                // Если клик не по холодильнику - возвращаемся на игровой экран
                game.setScreen(new GameScreen(game, pet));
                System.out.println("Возврат на основной экран.");
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Изменение размера экрана кухни.");
    }

    @Override
    public void pause() {
        System.out.println("Пауза на экране кухни.");
    }

    @Override
    public void resume() {
        System.out.println("Возобновление экрана кухни.");
    }

    @Override
    public void hide() {
        System.out.println("Экран кухни скрыт.");
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        kitchenTexture.dispose();
        fridgeClosedTexture.dispose();
        fridgeOpenTexture.dispose();
        System.out.println("Очистка ресурсов экрана кухни.");
    }
}
