package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.GameScreen;

import java.util.Map;

public class KitchenScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;

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
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Загрузка текстур
        kitchenTexture = new Texture(Gdx.files.internal("backgrounds/kitchen.png"));
        fridgeClosedTexture = new Texture(Gdx.files.internal("ui/fridge_closed.png"));
        fridgeOpenTexture = new Texture(Gdx.files.internal("ui/fridge_open.png"));

        // Создаем UI с кнопками
        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().pad(20);
        stage.addActor(table);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        // Загрузка текстур кнопок
        Texture feedTexture = new Texture(Gdx.files.internal("ui/buttons/feed_button.png"));
        Texture playTexture = new Texture(Gdx.files.internal("ui/buttons/play_button.png"));
        Texture sleepTexture = new Texture(Gdx.files.internal("ui/buttons/sleep_button.png"));

        // Стиль кнопок
        TextButton.TextButtonStyle feedButtonStyle = new TextButton.TextButtonStyle();
        feedButtonStyle.font = font;
        feedButtonStyle.up = new Image(feedTexture).getDrawable();

        TextButton.TextButtonStyle playButtonStyle = new TextButton.TextButtonStyle();
        playButtonStyle.font = font;
        playButtonStyle.up = new Image(playTexture).getDrawable();

        TextButton.TextButtonStyle sleepButtonStyle = new TextButton.TextButtonStyle();
        sleepButtonStyle.font = font;
        sleepButtonStyle.up = new Image(sleepTexture).getDrawable();

        // Создаем кнопки
        TextButton feedButton = new TextButton("", feedButtonStyle);
        TextButton playButton = new TextButton("", playButtonStyle);
        TextButton sleepButton = new TextButton("", sleepButtonStyle);

        // Логика кнопки "Feed"
        feedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Кормление питомца!");
                pet.increaseEnergy(10);
                System.out.println("Питомец поел!");
            }
        });

        // Логика кнопки "Play"
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, pet));
                System.out.println("Возврат на главный экран.");
            }
        });

        // Логика кнопки "Sleep"
        sleepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, pet));
                System.out.println("Питомец уснул!");
            }
        });

        // Добавляем кнопки на экран
        table.add(feedButton).pad(10).width(150).height(50);
        table.add(playButton).pad(10).width(150).height(50);
        table.add(sleepButton).pad(10).width(150).height(50);
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

        batch.end();
        stage.act(delta);
        stage.draw();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            System.out.println("Клик: X=" + x + ", Y=" + y);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

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
        stage.dispose();
        System.out.println("Ресурсы экрана кухни очищены.");
    }
}
