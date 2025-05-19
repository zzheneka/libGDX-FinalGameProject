package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class CleanScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Stage stage;

    private Texture dirtyBackground;
    private Texture cleanBackground;
    private Texture currentBackground;

    private boolean isClean;

    private Image bathroom;


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

        bathroom = new Image(new TextureRegionDrawable(new TextureRegion(currentBackground)));
        bathroom.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bathroom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleCleaning();
            }
        });
        bathroom.setZIndex(0);
        stage.addActor(bathroom);

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().padBottom(20);
        stage.addActor(table);

        // Загрузка текстур для кнопок
        Texture feedTexture = new Texture(Gdx.files.internal("ui/buttons/feed_button.png"));
        Texture playTexture = new Texture(Gdx.files.internal("ui/buttons/play_button.png"));
        Texture sleepTexture = new Texture(Gdx.files.internal("ui/buttons/sleep_button.png"));
        Texture cleanTexture = new Texture(Gdx.files.internal("ui/buttons/clean_button.png"));

// Создаем кнопки на основе изображений
        ImageButton feedButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(feedTexture)));
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)));
        ImageButton sleepButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(sleepTexture)));
        ImageButton cleanButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(cleanTexture)));

        // Добавляем слушатели на кнопки
        feedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Feed нажата");
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Play нажата");
            }
        });

        sleepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Sleep нажата");
            }
        });

        cleanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clean нажата");
            }
        });



        table.add(feedButton).pad(15).size(110, 80);
        table.add(playButton).pad(15).size(110, 80);
        table.add(sleepButton).pad(15).size(110, 80);
        table.add(cleanButton).pad(15).size(110, 80);

        table.setZIndex(1);

    }




    // Метод смены фона при клике
    private void toggleCleaning() {
        if (!isClean) {
            System.out.println("Чистим ванную...");
            currentBackground = cleanBackground;  // Меняем фон сразу
            isClean = true;  // Обновляем состояние

            bathroom.setDrawable(new TextureRegionDrawable(new TextureRegion(currentBackground)));
            System.out.println("Ванная комната чистая!");
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

