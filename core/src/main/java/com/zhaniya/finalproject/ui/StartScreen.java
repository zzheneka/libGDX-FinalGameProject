package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.PetBuilder;
import com.zhaniya.finalproject.model.pet.PetType;

public class StartScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    private Texture dogTexture, catTexture, dragonTexture;
    private BitmapFont font;
    private GlyphLayout layout;

    public StartScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout = new GlyphLayout();


        dogTexture = new Texture("dog/happy/frame1.png");
        catTexture = new Texture("cat/happy/frame1.png");
        dragonTexture = new Texture("dragon/happy/frame1.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();


        font.getData().setScale(2f);
        layout.setText(font, "Choose your pet:");
        font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, Gdx.graphics.getHeight() - 50);

        batch.draw(dogTexture, 100, 250, 120, 120);
        batch.draw(catTexture, 290, 250, 120, 120);
        batch.draw(dragonTexture, 480, 250, 120, 120);

        batch.end();


        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY(); // Переворот координат по Y

            Pet selectedPet = null;

            if (x >= 100 && x <= 220 && y >= 250 && y <= 370) { // Собака
                selectedPet = new PetBuilder()
                    .setType(PetType.DOG)
                    .setName("Bobik")
                    .build();
            } else if (x >= 290 && x <= 410 && y >= 250 && y <= 370) { // Котик
                selectedPet = new PetBuilder()
                    .setType(PetType.CAT)
                    .setName("Murzik")
                    .build();
            } else if (x >= 480 && x <= 600 && y >= 250 && y <= 370) { // Дракон
                selectedPet = new PetBuilder()
                    .setType(PetType.DRAGON)
                    .setName("Drako")
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
        font.dispose();
        dogTexture.dispose();
        catTexture.dispose();
        dragonTexture.dispose();
    }
}
