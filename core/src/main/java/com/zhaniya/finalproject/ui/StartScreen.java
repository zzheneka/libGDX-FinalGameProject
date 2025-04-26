package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.model.pet.*;

public class StartScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    private Texture dogTexture, catTexture, dragonTexture;
    private BitmapFont font;

    public StartScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        // ✅ Загрузка актуальных путей
        dogTexture = new Texture("dog.png");
        catTexture = new Texture("cat.png");
        dragonTexture = new Texture("dragon/happy/dragon.png");  // 🐉 Путь обновлён
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        font.getData().setScale(2f);
        font.draw(batch, "Выбери питомца:", 130, 450);

        batch.draw(dogTexture, 70, 250, 100, 100);      // 🐶
        batch.draw(catTexture, 190, 250, 100, 100);     // 🐱
        batch.draw(dragonTexture, 310, 250, 100, 100);  // 🐉
        batch.end();

        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            Pet selectedPet = null;

            if (x >= 70 && x <= 170 && y >= 250 && y <= 350) {
                selectedPet = new PetBuilder()
                    .setType(PetType.DOG)
                    .setName("Бобик")
                    .build();
            } else if (x >= 190 && x <= 290 && y >= 250 && y <= 350) {
                selectedPet = new PetBuilder()
                    .setType(PetType.CAT)
                    .setName("Мурка")
                    .build();
            } else if (x >= 310 && x <= 410 && y >= 250 && y <= 350) {
                selectedPet = new PetBuilder()
                    .setType(PetType.DRAGON)
                    .setName("Дракоша")
                    .build();
            }

            if (selectedPet != null) {
                game.setScreen(new GameScreen(game, selectedPet));
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
        dogTexture.dispose();
        catTexture.dispose();
        dragonTexture.dispose();
        font.dispose();
    }
}
