package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.managers.PetManager;
import com.zhaniya.finalproject.ui.managers.UIManager;
import com.zhaniya.finalproject.ui.managers.InputManager;
import com.zhaniya.finalproject.ui.managers.AnimationManager;
import com.zhaniya.finalproject.ui.managers.KitchenManager;  // Добавлен импорт
import com.zhaniya.finalproject.ui.managers.KitchenScreen;          // Добавлен импорт

public class GameScreen implements Screen {
    private final Game game;
    private final Pet pet;

    private final PetManager petManager;
    private final UIManager uiManager;
    private final InputManager inputManager;
    private final AnimationManager animationManager;

    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout layout;

    // Фон
    private Texture backgroundTexture;
    private Texture playgroundTexture;
    private Texture sleepTexture;

    // Логическое поле для состояния сна
    private boolean isSleeping = false;

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;

        petManager = new PetManager(pet);
        uiManager = new UIManager();
        inputManager = new InputManager(petManager, uiManager.getStage());
        animationManager = new AnimationManager(pet);

        Gdx.input.setInputProcessor(uiManager.getStage());
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout = new GlyphLayout();

        playgroundTexture = new Texture("backgrounds/playground.png");
        sleepTexture = new Texture("backgrounds/sleep_with_dragon.png");

        backgroundTexture = playgroundTexture;

        uiManager.setFeedButtonListener(new FeedButtonListener());
        uiManager.setPlayButtonListener(new PlayButtonListener());
        uiManager.setSleepButtonListener(new SleepButtonListener());
    }

    private void updateBackground(Texture newTexture) {
        if (backgroundTexture != newTexture) {
            backgroundTexture = newTexture;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "Энергия: " + pet.getEnergy() + "\nНастроение: " + pet.getMood(), 20, 450);

        if (!isSleeping) {
            animationManager.render(batch);
        }

        batch.end();
        uiManager.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Экран изменён: ширина = " + width + ", высота = " + height);
    }

    @Override
    public void pause() {
        System.out.println("Игра на паузе");
    }

    @Override
    public void resume() {
        System.out.println("Игра возобновлена");
    }

    @Override
    public void hide() {
        System.out.println("Экран скрыт");
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        uiManager.dispose();
        animationManager.dispose();
        playgroundTexture.dispose();
        sleepTexture.dispose();
    }

    // Слушатели для кнопок
    // Слушатель для кнопки кормления
    class FeedButtonListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            try {
                // Попытка открыть экран кухни
                KitchenScreen kitchenScreen = new KitchenScreen(game, pet);
                game.setScreen(kitchenScreen);
                System.out.println("Переход на экран кухни");
            } catch (Exception e) {
                System.err.println("Ошибка при переходе на экран кухни: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    class PlayButtonListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            petManager.playWithPet();
            isSleeping = false;
            updateBackground(playgroundTexture);
            System.out.println("Играем с питомцем!");
        }
    }

    class SleepButtonListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            petManager.putPetToSleep();
            isSleeping = true;
            updateBackground(sleepTexture);
            System.out.println("Питомец спит!");
        }
    }
}
