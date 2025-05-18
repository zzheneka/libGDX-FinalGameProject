package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CleanScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Stage stage;

    private Texture dirtyBackground;
    private Texture cleanBackground;
    private Texture currentBackground;

    private boolean isClean;

    public CleanScreen(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Загрузка фоновых изображений
        dirtyBackground = new Texture(Gdx.files.internal("backgrounds/bathroom_dirty.png"));
        cleanBackground = new Texture(Gdx.files.internal("backgrounds/bathroom_clean.png"));
        currentBackground = dirtyBackground;
        isClean = false;

        // Создаем изображение ванной комнаты
        Image bathroom = new Image(currentBackground);
        bathroom.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Слушатель нажатия на ванну
        bathroom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleCleaning();
            }
        });

        stage.addActor(bathroom);
    }

    // Метод смены фона при клике
    private void toggleCleaning() {
        if (!isClean) {
            System.out.println("Чистим ванную...");
            // Таймер на 2 секунды для смены фона
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    currentBackground = cleanBackground;
                    isClean = true;
                    System.out.println("Ванная комната чистая!");
                }
            }, 2); // Задержка в 2 секунды перед сменой
        }
    }

    @Override
    public void show() {
        System.out.println("Экран чистки ванны открыт.");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        batch.draw(currentBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
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
        dirtyBackground.dispose();
        cleanBackground.dispose();
        stage.dispose();
    }
}

