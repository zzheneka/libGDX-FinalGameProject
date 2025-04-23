package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.model.pet.PetType;

public class GameScreen implements Screen {
    private final Game game;
    private final PetType type;
    private SpriteBatch batch;
    private Texture texture;

    public GameScreen(Game game, PetType type) {
        this.game = game;
        this.type = type;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        switch (type) {
            case DOG -> texture = new Texture("dog.png");
            case CAT -> texture = new Texture("cat.png");
            case DRAGON -> texture = new Texture("dragon.png");
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        batch.draw(texture, 150, 150, 150, 150);
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
