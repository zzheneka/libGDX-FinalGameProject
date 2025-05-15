package com.zhaniya.finalproject.ui.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.zhaniya.finalproject.ui.minigames.CatchFoodGameScreen;
import com.zhaniya.finalproject.ui.minigames.MemoryGameScreen;
import com.zhaniya.finalproject.ui.minigames.ObstacleRunGameScreen;
import com.zhaniya.finalproject.ui.GameScreen;
import com.badlogic.gdx.Game;

public class MiniGameSelectionScreen extends ScreenAdapter {
    private final Game game;
    private Stage stage;
    private Skin skin;

    public MiniGameSelectionScreen(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // Заголовок
        Label title = new Label("Выберите мини-игру", skin, "title");
        title.setFontScale(2);

        // Кнопки мини-игр
        TextButton catchFoodButton = createGameButton("Catch Food");
        TextButton memoryGameButton = createGameButton("Memory Game");
        TextButton obstacleRunButton = createGameButton("Obstacle Run");
        TextButton backButton = createGameButton("Назад");

        // Слушатели нажатий на кнопки
        catchFoodButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CatchFoodGameScreen(game));
            }
        });

        memoryGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MemoryGameScreen(game));
            }
        });

        obstacleRunButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ObstacleRunGameScreen(game));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, null));
            }
        });

        // Добавляем элементы в таблицу
        table.add(title).padBottom(40).row();
        table.add(catchFoodButton).pad(15).width(300).height(60).row();
        table.add(memoryGameButton).pad(15).width(300).height(60).row();
        table.add(obstacleRunButton).pad(15).width(300).height(60).row();
        table.add(backButton).padTop(30).width(200).height(50);
    }

    // Метод для создания кнопок
    private TextButton createGameButton(String text) {
        TextButton button = new TextButton(text, skin);
        button.getLabel().setFontScale(1.5f);  // Увеличенный текст
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);  // Тёмный фон
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
