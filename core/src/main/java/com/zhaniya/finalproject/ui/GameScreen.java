package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
        backgroundTexture = new Texture("backgrounds/playground.png");
        sleepTexture = new Texture("backgrounds/sleep_with_dragon.png");
        playgroundTexture = new Texture("backgrounds/playground.png");
        petTexture = new Texture("pets/standing.png");

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().pad(20);
        stage.addActor(table);

        Label title = new Label("Tamagotchi+", new Skin(Gdx.files.internal("uiskin.json")));
        title.setFontScale(2);

        TextButton feedButton = new TextButton("Кормить", new Skin(Gdx.files.internal("uiskin.json")));
        TextButton playButton = new TextButton("Играть", new Skin(Gdx.files.internal("uiskin.json")));
        TextButton sleepButton = new TextButton("Спать", new Skin(Gdx.files.internal("uiskin.json")));

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
                    petTexture = new Texture("pets/sleeping.png");
                    System.out.println("Питомец уснул!");
                } else {
                    backgroundTexture = playgroundTexture;
                    petTexture = new Texture("pets/standing.png");
                    System.out.println("Питомец проснулся!");
                }
            }
        });

        // Добавляем элементы на экран
        table.add(title).padBottom(20).row();
        table.add(feedButton).pad(10).width(200).height(50).row();
        table.add(playButton).pad(10).width(200).height(50).row();
        table.add(sleepButton).pad(10).width(200).height(50).row();
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

        // Рисуем питомца
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
