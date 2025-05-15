package com.zhaniya.finalproject.ui.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.Game;
import com.zhaniya.finalproject.ui.GameScreen;

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
        stage.addActor(table);

        Label title = new Label("Выберите мини-игру", skin);
        TextButton catchFoodButton = new TextButton("Catch Food", skin);
        TextButton backButton = new TextButton("Назад", skin);

        catchFoodButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CatchFoodGameScreen(game));
                System.out.println("Запуск мини-игры: Catch Food");
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, null));
                System.out.println("Возврат на главный экран");
            }
        });

        table.add(title).padBottom(20).row();
        table.add(catchFoodButton).pad(10).row();
        table.add(backButton).padTop(20);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
