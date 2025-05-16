package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.ui.minigames.MiniGameSelectionScreen;
import com.zhaniya.finalproject.ui.managers.KitchenScreen;

public class GameScreen extends ScreenAdapter {
    private final Game game;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture petTexture;
    private Texture sleepTexture;
    private Texture playgroundTexture;
    private Stage stage;
    private boolean isSleeping = false;

    public GameScreen(Game game, Object o) {
        this.game = game;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Загрузка текстур
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/playground.png"));
        sleepTexture = new Texture(Gdx.files.internal("backgrounds/sleep_with_dragon.png"));
        playgroundTexture = new Texture(Gdx.files.internal("backgrounds/playground.png"));
        petTexture = new Texture(Gdx.files.internal("dragon/happy/frame1.png"));

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().pad(20);  // Размещаем кнопки внизу экрана
        stage.addActor(table);

        // Создаем стиль текста и кнопок
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        // Загружаем текстуры кнопок из папки ui/buttons
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

        // Заголовок
        Label title = new Label("Tamagotchi+", labelStyle);
        title.setFontScale(2);

        // Создаем кнопки
        TextButton feedButton = new TextButton("", feedButtonStyle);
        TextButton playButton = new TextButton("", playButtonStyle);
        TextButton sleepButton = new TextButton("", sleepButtonStyle);

        // Логика кнопки "Кормить"
        feedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new KitchenScreen(game, null));
                System.out.println("Переход на экран кухни.");
            }
        });

        // Логика кнопки "Играть"
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MiniGameSelectionScreen(game));
                System.out.println("Переход на экран выбора мини-игр.");
            }
        });

        // Логика кнопки "Спать"
        sleepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isSleeping = !isSleeping;
                if (isSleeping) {
                    backgroundTexture = sleepTexture;
                    petTexture = new Texture(Gdx.files.internal("dragon/happy/frame3.png"));
                    System.out.println("Питомец уснул!");
                } else {
                    backgroundTexture = playgroundTexture;
                    petTexture = new Texture(Gdx.files.internal("dragon/happy/frame1.png"));
                    System.out.println("Питомец проснулся!");
                }
            }
        });

        // Добавляем элементы на экран
        table.add(title).padBottom(20).row();
        table.add(feedButton).pad(10).width(150).height(50);
        table.add(playButton).pad(10).width(150).height(50);
        table.add(sleepButton).pad(10).width(150).height(50);
    }

    @Override
    public void show() {
        System.out.println("Главный экран игры отображён.");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Рисуем питомца на экране
        batch.draw(petTexture, Gdx.graphics.getWidth() / 2f - 64, 100, 128, 128);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        sleepTexture.dispose();
        playgroundTexture.dispose();
        petTexture.dispose();
        stage.dispose();
    }
}
