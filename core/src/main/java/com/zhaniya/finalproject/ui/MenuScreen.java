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

    // Переменные для анимации кнопки
    private float scale;
    private boolean growing;

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

        // Начальные параметры анимации
        scale = 1.0f;
        growing = true;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1); // Белый фон

        // Логика анимации кнопки
        if (growing) {
            scale += delta * 0.5f;
            if (scale >= 1.2f) growing = false;
        } else {
            scale -= delta * 0.5f;
            if (scale <= 1.0f) growing = true;
        }

        batch.begin();

        // Отрисовка текста
        font.draw(batch, "Добро пожаловать в игру!",
            Gdx.graphics.getWidth() / 2f - 200,
            Gdx.graphics.getHeight() - 100);

        // Вычисляем размеры и координаты кнопки с учетом анимации
        float buttonWidth = startButtonBounds.width * scale;
        float buttonHeight = startButtonBounds.height * scale;
        float buttonX = startButtonBounds.x - (buttonWidth - startButtonBounds.width) / 2;
        float buttonY = startButtonBounds.y - (buttonHeight - startButtonBounds.height) / 2;

        // Отрисовка пульсирующей кнопки
        batch.draw(startButton, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.end();

        // Проверка на нажатие кнопки
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight) {
                game.setScreen(new StartScreen(game));
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        startButton.dispose();
        font.dispose();
    }
}
