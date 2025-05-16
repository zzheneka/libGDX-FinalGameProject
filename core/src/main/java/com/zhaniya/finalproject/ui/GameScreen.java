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
import com.zhaniya.finalproject.commands.FeedCommand;
import com.zhaniya.finalproject.commands.PlayCommand;
import com.zhaniya.finalproject.commands.SleepCommand;

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

    // Метки для энергии и комментария
    private Label energyLabel;
    private Label commentLabel;

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Загрузка текстур
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/playground.png"));
        sleepTexture = new Texture(Gdx.files.internal("backgrounds/sleep_with_dragon.png"));
        playgroundTexture = new Texture(Gdx.files.internal("backgrounds/playground.png"));
        petTexture = new Texture(Gdx.files.internal("dragon/happy/frame1.png"));

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().pad(20);
        stage.addActor(table);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        // Заголовок
        Label title = new Label("Tamagotchi+", labelStyle);
        title.setFontScale(2);

        // Метка энергии
        energyLabel = new Label("Energy: " + pet.getEnergy(), labelStyle);
        energyLabel.setFontScale(1.5f);

        // Метка комментария
        commentLabel = new Label("Я счастлив!", labelStyle);
        commentLabel.setFontScale(1.2f);

        // Кнопки
        TextButton feedButton = new TextButton("Кормить", new Skin(Gdx.files.internal("uiskin.json")));
        TextButton playButton = new TextButton("Играть", new Skin(Gdx.files.internal("uiskin.json")));
        TextButton sleepButton = new TextButton("Спать", new Skin(Gdx.files.internal("uiskin.json")));
        TextButton miniGamesButton = new TextButton("Мини-игры", new Skin(Gdx.files.internal("uiskin.json")));

        // Логика кнопки "Кормить"
        feedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new FeedCommand(pet).execute();
                game.setScreen(new KitchenScreen(game, pet));
                System.out.println("Питомец покормлен!");
            }
        });

        // Логика кнопки "Играть"
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new PlayCommand(pet).execute();
                System.out.println("Питомец играет!");
            }
        });

        // Логика кнопки "Спать"
        sleepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleSleep();
            }
        });

        // Кнопка для перехода к мини-играм
        miniGamesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MiniGameSelectionScreen(game));
                System.out.println("Переход на экран мини-игр.");
            }
        });

        // Добавляем элементы на экран
        table.add(title).padBottom(10).row();
        table.add(energyLabel).padBottom(5).row();
        table.add(commentLabel).padBottom(20).row();
        table.add(feedButton).pad(10).width(200).height(50).row();
        table.add(playButton).pad(10).width(200).height(50).row();
        table.add(sleepButton).pad(10).width(200).height(50).row();
        table.add(miniGamesButton).pad(10).width(200).height(50).row();
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
        System.out.println("Ресурсы экрана игры очищены.");
    }
}
