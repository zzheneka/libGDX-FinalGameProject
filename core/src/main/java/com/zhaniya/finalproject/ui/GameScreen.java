package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.managers.PetManager;
import com.zhaniya.finalproject.ui.managers.UIManager;
import com.zhaniya.finalproject.ui.managers.InputManager;
import com.zhaniya.finalproject.ui.managers.AnimationManager;
import com.zhaniya.finalproject.utils.TimerUtil;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;


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

    // Текстуры фона
    private Texture backgroundTexture;
    private Texture playgroundTexture;
    private Texture kitchenTexture;
    private Texture sleepTexture;

    // Логическое поле для состояния сна
    private boolean isSleeping = false;

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;

        // Инициализация менеджеров
        petManager = new PetManager(pet);
        uiManager = new UIManager();
        inputManager = new InputManager(petManager, uiManager.getStage());
        animationManager = new AnimationManager(pet);

        // Установка обработчика ввода
        Gdx.input.setInputProcessor(uiManager.getStage());
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout = new GlyphLayout();

        // Загрузка текстур фона
        playgroundTexture = new Texture("backgrounds/playground.png");
        kitchenTexture = new Texture("backgrounds/kitchen.png");
        sleepTexture = new Texture("backgrounds/sleep_with_dragon.png");

        // Изначально фон - игровая площадка
        backgroundTexture = playgroundTexture;

        // Настройка таймера для питомца
        TimerUtil.startPetTimer(pet);

        // Настройка кнопок в UI
        uiManager.setFeedButtonListener(new FeedButtonListener());
        uiManager.setPlayButtonListener(new PlayButtonListener());
        uiManager.setSleepButtonListener(new SleepButtonListener());
    }

    // Метод для обновления фона
    private void updateBackground(Texture newTexture) {
        if (backgroundTexture != newTexture) {
            backgroundTexture = newTexture;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();

        // Отрисовка фона
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Отрисовка текста
        font.draw(batch, "Энергия: " + pet.getEnergy() + "\nНастроение: " + pet.getMood(), 20, 450);

        // Отрисовка анимации питомца только если не спит
        if (!isSleeping) {
            animationManager.render(batch);
        }

        batch.end();

        // Отрисовка UI
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

        // Освобождение всех текстур при выходе
        if (playgroundTexture != null) playgroundTexture.dispose();
        if (kitchenTexture != null) kitchenTexture.dispose();
        if (sleepTexture != null) sleepTexture.dispose();
    }

    // Слушатели для кнопок
    class FeedButtonListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            petManager.feedPet(10);
            System.out.println("Кормим питомца!");
            isSleeping = false;  // Выходим из состояния сна
            updateBackground(kitchenTexture);  // Меняем фон на кухню
        }
    }

    class PlayButtonListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            petManager.playWithPet();
            System.out.println("Играем с питомцем!");
            isSleeping = false;  // Выходим из состояния сна
            updateBackground(playgroundTexture);  // Меняем фон на игровую площадку
        }
    }

    class SleepButtonListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            petManager.putPetToSleep();
            System.out.println("Питомец спит!");
            isSleeping = true;  // Входим в состояние сна
            updateBackground(sleepTexture);  // Меняем фон на сон
        }
    }
}
