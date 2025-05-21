package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.GameScreen;

public class CleanScreen implements Screen {
    private Game game;
    private Pet pet;

    private SpriteBatch batch;
    private Stage stage;

    private Texture dirtyBackground;
    private Texture cleanBackground;
    private Texture currentBackground;
    private Texture backTexture;

    private Image bathroom;
    private boolean isClean;

    public CleanScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Загрузка фонов
        dirtyBackground = new Texture(Gdx.files.internal("backgrounds/bathroom_dirty.png"));
        cleanBackground = new Texture(Gdx.files.internal("backgrounds/bathroom_clean.png"));
        currentBackground = dirtyBackground;

        // Загрузка текстуры кнопки back
        backTexture = new Texture(Gdx.files.internal("buttoms/back.png"));

        // Фон ванной
        bathroom = new Image(new TextureRegionDrawable(new TextureRegion(currentBackground)));
        bathroom.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bathroom.setZIndex(0);
        bathroom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleCleaning();
            }
        });
        stage.addActor(bathroom);

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().right().pad(20);
        stage.addActor(table);

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)));
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Возврат на главный экран.");
                game.setScreen(new GameScreen(game, pet));
            }
        });

        table.add(backButton).size(100, 60);
        table.setZIndex(1);
    }

    private void toggleCleaning() {
        if (!isClean) {
            currentBackground = cleanBackground;
            bathroom.setDrawable(new TextureRegionDrawable(new TextureRegion(currentBackground)));
            isClean = true;
            System.out.println("Ванная комната теперь чистая!");
        }
    }

    @Override
    public void show() {
        System.out.println("Экран чистки открыт.");
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
    public void dispose() {
        batch.dispose();
        dirtyBackground.dispose();
        cleanBackground.dispose();
        backTexture.dispose();
        stage.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
