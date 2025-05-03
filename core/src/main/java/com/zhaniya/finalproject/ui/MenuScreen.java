package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.Main;

public class MenuScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture startButton;
    private Rectangle startButtonBounds;
    private BitmapFont font;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Стандартный лёгкий шрифт
        font.getData().setScale(2f); // Сделать шрифт крупнее

        startButton = new Texture("ui/start_button.png");
        startButtonBounds = new Rectangle(
            (Gdx.graphics.getWidth() - 200) / 2f,  // X центр
            (Gdx.graphics.getHeight() - 100) / 2f, // Y центр
            200, 80 // Ширина и высота кнопки
        );
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1); // Белый фон

        batch.begin();

        font.draw(batch, "Добро пожаловать в игру!", Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() - 100);
        batch.draw(startButton, startButtonBounds.x, startButtonBounds.y, startButtonBounds.width, startButtonBounds.height);
        batch.end();

        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (startButtonBounds.contains(x, y)) {
                game.setScreen(new StartScreen(game));
                dispose();
            }
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        startButton.dispose();
        font.dispose();
    }
}
