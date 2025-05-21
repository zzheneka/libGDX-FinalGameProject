package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.model.pet.Pet;

public class UIManager {
    private final Stage stage;
    private final Skin skin;
    private final Table table;
    private final Game game;
    private TextButton cleanButton;


    public UIManager(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        cleanButton = new TextButton("Clean", skin);
        table.add(cleanButton).pad(10);

        cleanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pet pet = null;
                game.setScreen(new CleanScreen(game,pet));
                System.out.println("Переход на экран чистки.");
            }
        });

    }

    public void setCleanButtonListener(ClickListener listener) {
        cleanButton.addListener(listener);
    }

    public Skin getSkin() {
        return skin;
    }

    public Table getTable() {
        return table;
    }

    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
