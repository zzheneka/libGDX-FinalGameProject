package com.zhaniya.finalproject.ui.minigames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.GameScreen;
import com.zhaniya.finalproject.ui.minigames.MemoryGameScreen;

public class MiniGameSelectionScreen extends ScreenAdapter {
    private final Pet pet;
    private Game game;
    private Stage stage;
    private SpriteBatch batch;

    public MiniGameSelectionScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);

        // Загрузка текстуры кнопки
        Texture buttonTexture;
        try {
            buttonTexture = new Texture(Gdx.files.internal("buttoms/back.png"));
            System.out.println("Кнопка Memory Game успешно загружена.");
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке кнопки Memory Game: " + e.getMessage());
            buttonTexture = new Texture(Gdx.files.internal("buttoms/default_back.png")); // резервная кнопка
        }

        // Создаем кнопку через текстуру
        ImageButton memoryGameButton = new ImageButton(new TextureRegionDrawable(buttonTexture));
        memoryGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MemoryGameScreen(game));
                System.out.println("Запущена мини-игра Memory Game");
            }
        });

        table.add(memoryGameButton).pad(10);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.end();
        if (stage != null) {
            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }
}
