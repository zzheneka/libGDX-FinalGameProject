package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.managers.PetManager;
import com.zhaniya.finalproject.ui.managers.UIManager;
import com.zhaniya.finalproject.ui.managers.InputManager;
import com.zhaniya.finalproject.ui.managers.AnimationManager;
import com.zhaniya.finalproject.ui.minigames.MiniGameSelectionScreen;

public class GameScreen implements Screen {
    private final Game game;
    private final Pet pet;

    private final PetManager petManager;
    private final UIManager uiManager;
    private final InputManager inputManager;
    private final AnimationManager animationManager;

    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout layout;

    private Texture backgroundTexture;
    private Texture playgroundTexture;
    private Texture sleepTexture;
    private boolean isSleeping = false;

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;

        petManager = new PetManager(pet);
        uiManager = new UIManager();
        inputManager = new InputManager(petManager, uiManager.getStage());
        animationManager = new AnimationManager(pet);

        Gdx.input.setInputProcessor(uiManager.getStage());
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout = new GlyphLayout();

        playgroundTexture = new Texture("backgrounds/playground.png");
        sleepTexture = new Texture("backgrounds/sleep_with_dragon.png");
        backgroundTexture = playgroundTexture;

        setupButtons();
    }

    private void setupButtons() {
        TextButton feedButton = new TextButton("Feed", uiManager.getSkin());
        feedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                petManager.feedPet(20);
                updateBackground(playgroundTexture);
                System.out.println("Питомец накормлен!");
            }
        });

        TextButton playButton = new TextButton("Play", uiManager.getSkin());
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                petManager.playWithPet();
                isSleeping = false;
                updateBackground(playgroundTexture);
                System.out.println("Играем с питомцем!");
            }
        });

        TextButton sleepButton = new TextButton("Sleep", uiManager.getSkin());
        sleepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                petManager.putPetToSleep();
                isSleeping = true;
                updateBackground(sleepTexture);
                System.out.println("Питомец спит!");
            }
        });

        TextButton miniGamesButton = new TextButton("Mini Games", uiManager.getSkin());
        miniGamesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MiniGameSelectionScreen(game));
                System.out.println("Переход на экран мини-игр!");
            }
        });

        uiManager.getTable().add(feedButton).pad(10);
        uiManager.getTable().add(playButton).pad(10);
        uiManager.getTable().add(sleepButton).pad(10);
        uiManager.getTable().add(miniGamesButton).pad(10).row();
    }

    private void updateBackground(Texture newTexture) {
        if (backgroundTexture != newTexture) {
            backgroundTexture = newTexture;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        layout.setText(font, "Energy: " + pet.getEnergy() + "\nMood: " + pet.getMood());
        font.draw(batch, layout, 20, Gdx.graphics.getHeight() - 20);
        if (!isSleeping) {
            animationManager.render(batch);
        }
        batch.end();
        uiManager.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        uiManager.resize(width, height);
        System.out.println("Экран изменён: ширина = " + width + ", высота = " + height);
    }

    @Override
    public void pause() {
        System.out.println("Игра на паузе");
    }

    @Override
    public void resume() {
        System.out.println("Игра возобновлена");
    }

    @Override
    public void hide() {
        System.out.println("Экран скрыт");
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        uiManager.dispose();
        playgroundTexture.dispose();
        sleepTexture.dispose();
    }
}
