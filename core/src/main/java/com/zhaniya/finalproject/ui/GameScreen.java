package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.ui.minigames.MiniGameSelectionScreen;
import com.zhaniya.finalproject.ui.managers.KitchenScreen;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.managers.CleanScreen;


public class GameScreen extends ScreenAdapter {
    private final Game game;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture petTexture;
    private Texture sleepTexture;
    private Texture playgroundTexture;
    private Stage stage;
    private boolean isSleeping = false;
    private Pet pet;

    private Label energyLabel;
    private Label commentLabel;

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/playground.png"));
        sleepTexture = new Texture(Gdx.files.internal("backgrounds/sleep_with_dragon.png"));
        playgroundTexture = new Texture(Gdx.files.internal("backgrounds/playground.png"));
        petTexture = new Texture(Gdx.files.internal("dragon/happy/frame1.png"));

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().pad(10);
        stage.addActor(table);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label title = new Label("Tamagotchi+", labelStyle);
        title.setFontScale(2);

        energyLabel = new Label("Energy: " + pet.getEnergy(), labelStyle);
        energyLabel.setFontScale(1.2f);

        commentLabel = new Label("Я счастлив!", labelStyle);
        commentLabel.setFontScale(1.2f);

        Texture feedTexture = new Texture(Gdx.files.internal("ui/buttons/feed_button.png"));
        Texture playTexture = new Texture(Gdx.files.internal("ui/buttons/play_button.png"));
        Texture sleepTexture = new Texture(Gdx.files.internal("ui/buttons/sleep_button.png"));
        Texture cleanTexture = new Texture(Gdx.files.internal("ui/buttons/clean_button.png"));

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton.TextButtonStyle feedButtonStyle = new TextButton.TextButtonStyle();
        feedButtonStyle.font = font;
        feedButtonStyle.up = new Image(feedTexture).getDrawable();

        TextButton.TextButtonStyle playButtonStyle = new TextButton.TextButtonStyle();
        playButtonStyle.font = font;
        playButtonStyle.up = new Image(playTexture).getDrawable();

        TextButton.TextButtonStyle sleepButtonStyle = new TextButton.TextButtonStyle();
        sleepButtonStyle.font = font;
        sleepButtonStyle.up = new Image(sleepTexture).getDrawable();

        TextButton.TextButtonStyle cleanButtonStyle = new TextButton.TextButtonStyle();
        cleanButtonStyle.font = font;
        cleanButtonStyle.up = new Image(cleanTexture).getDrawable();

        TextButton feedButton = new TextButton("", feedButtonStyle);
        TextButton playButton = new TextButton("", playButtonStyle);
        TextButton sleepButton = new TextButton("", sleepButtonStyle);
        TextButton cleanButton = new TextButton("", cleanButtonStyle);

        cleanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CleanScreen(game));
                System.out.println("Переход на экран чистки.");
            }
        });

        feedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new KitchenScreen(game, pet));
                System.out.println("Переход на экран кухни.");
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MiniGameSelectionScreen(game, pet));
                System.out.println("Переход на экран выбора мини-игр.");
            }
        });

        sleepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleSleep();
            }
        });

        table.add(title).padBottom(10).row();
        table.add(energyLabel).padBottom(5).row();
        table.add(commentLabel).padBottom(20).row();
        table.add(feedButton).pad(5).width(100).height(50);
        table.add(playButton).pad(5).width(100).height(50);
        table.add(sleepButton).pad(5).width(100).height(50);
        table.add(cleanButton).pad(5).width(100).height(50);
    }

    private void toggleSleep() {
        isSleeping = !isSleeping;
        backgroundTexture = isSleeping ? sleepTexture : playgroundTexture;
        petTexture = new Texture(Gdx.files.internal("dragon/happy/frame" + (isSleeping ? "3" : "1") + ".png"));
        System.out.println("Питомец " + (isSleeping ? "уснул" : "проснулся") + "!");
    }

    private void updateStatus() {
        energyLabel.setText("Energy: " + pet.getEnergy());

        if (pet.getEnergy() >= 50) {
            commentLabel.setText("Я счастлив!");
        } else if (pet.getEnergy() >= 20) {
            commentLabel.setText("Я немного устал...");
        } else if (pet.getEnergy() >= 10) {
            commentLabel.setText("Я устал... Хочу спать!");
        } else {
            commentLabel.setText("Энергия на исходе!");
            backgroundTexture = sleepTexture;
        }
    }

    @Override
    public void render(float delta) {
        updateStatus();
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(petTexture, Gdx.graphics.getWidth() / 2f - 64, 100, 128, 128);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        sleepTexture.dispose();
        playgroundTexture.dispose();
        petTexture.dispose();
        stage.dispose();
    }
}
