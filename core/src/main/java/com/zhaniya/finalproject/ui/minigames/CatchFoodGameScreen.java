package com.zhaniya.finalproject.ui.minigames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class CatchFoodGameScreen extends ScreenAdapter {
    private final Game game;
    private SpriteBatch batch;
    private Texture foodTexture;
    private Array<TextureRegion> foodSprites;

    private class Food {
        float x, y, speed;
        TextureRegion sprite;

        Food(float x, float y, float speed, TextureRegion sprite) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.sprite = sprite;
        }

        void update(float delta) {
            y -= speed * delta;
            if (y < -64) {  // Если упала за экран
                respawn();
            }
        }

        void respawn() {
            x = MathUtils.random(0, Gdx.graphics.getWidth() - 64);
            y = Gdx.graphics.getHeight();
            speed = MathUtils.random(100, 300);
            sprite = foodSprites.random();  // Случайная еда
        }

        void render(SpriteBatch batch) {
            batch.draw(sprite, x, y, 64, 64);
        }
    }

    private Array<Food> foodArray;

    public CatchFoodGameScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();

        // Загрузка спрайт-листа
        foodTexture = new Texture("minigames/catchfood/food.png");

        // Разрезаем спрайт-лист на 5 частей (ширина 64, высота 64)
        TextureRegion[][] tmp = TextureRegion.split(foodTexture, 64, 64);
        foodSprites = new Array<>();

        // Добавляем каждый спрайт в массив
        for (int i = 0; i < 5; i++) {
            foodSprites.add(tmp[0][i]);
        }

        // Инициализация еды
        foodArray = new Array<>();
        for (int i = 0; i < 5; i++) {
            float x = MathUtils.random(0, Gdx.graphics.getWidth() - 64);
            float y = MathUtils.random(Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight());
            float speed = MathUtils.random(100, 300);
            TextureRegion sprite = foodSprites.random();
            foodArray.add(new Food(x, y, speed, sprite));
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        // Рисуем все объекты еды
        for (Food food : foodArray) {
            food.update(delta);
            food.render(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        foodTexture.dispose();
    }
}
