package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.commands.FeedCommand;
import com.zhaniya.finalproject.commands.PlayCommand;
import com.zhaniya.finalproject.commands.SleepCommand;
import com.zhaniya.finalproject.model.pet.Emotion;
import com.zhaniya.finalproject.model.pet.Pet;

import java.util.EnumMap;

public class GameScreen implements Screen {
    private final Game game;
    private final Pet pet;
    private SpriteBatch batch;
    private EnumMap<Emotion, Animation<TextureRegion>> emotionAnimations;
    private Emotion currentEmotion;
    private float stateTime;
    private BitmapFont font;
    private GlyphLayout layout;

    private Stage stage;
    private Texture feedTexture, playTexture, sleepTexture;

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        emotionAnimations = new EnumMap<>(Emotion.class);

        String basePath = switch (pet.getType()) {
            case DRAGON -> "dragon";
            case CAT -> "cat";
            case DOG -> "dog";
        };

        emotionAnimations.put(Emotion.HAPPY, loadAnimation(basePath + "/happy", 0.2f));
        emotionAnimations.put(Emotion.ANGRY, loadAnimation(basePath + "/angry", 0.2f));
        emotionAnimations.put(Emotion.SAD, loadAnimation(basePath + "/sad", 0.2f));
        emotionAnimations.put(Emotion.SLEEP, loadAnimation(basePath + "/sleep", 0.4f));

        currentEmotion = pet.getState().getEmotion();
        stateTime = 0f;

        font = new BitmapFont();
        layout = new GlyphLayout();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        loadButtons();
    }

    private void loadButtons() {
        // Загрузка текстур
        feedTexture = new Texture("ui/buttons/feed_button.png");
        playTexture = new Texture("ui/buttons/play_button.png");
        sleepTexture = new Texture("ui/buttons/sleep_button.png");

        // Кнопки
        ImageButton feedButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(feedTexture)));
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)));
        ImageButton sleepButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(sleepTexture)));

        // Размер и позиция
        feedButton.setSize(120, 50);
        playButton.setSize(120, 50);
        sleepButton.setSize(120, 50);

        feedButton.setPosition(40, 30);
        playButton.setPosition(180, 30);
        sleepButton.setPosition(320, 30);

        // Слушатели
        feedButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                new FeedCommand(pet).execute();
                updateEmotion();
            }
        });

        playButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                new PlayCommand(pet).execute();
                updateEmotion();
            }
        });

        sleepButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                new SleepCommand(pet).execute();
                updateEmotion();
            }
        });

        // Добавляем кнопки на сцену
        stage.addActor(feedButton);
        stage.addActor(playButton);
        stage.addActor(sleepButton);
    }

    private Animation<TextureRegion> loadAnimation(String folderPath, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            Texture texture = new Texture(folderPath + "/frame" + (i + 1) + ".png");
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        stateTime += delta;

        Emotion newEmotion = pet.getState().getEmotion();
        if (newEmotion != currentEmotion) {
            currentEmotion = newEmotion;
            stateTime = 0f;
        }

        Animation<TextureRegion> animation = emotionAnimations.get(currentEmotion);
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        batch.begin();
        font.getData().setScale(2f);
        layout.setText(font, "Your Pet");
        font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 550);

        batch.draw(currentFrame, 150, 150, 200, 200);

        font.getData().setScale(1.5f);
        String status = "Energy: " + pet.getEnergy() + "\nMood: " + pet.getMood();
        layout.setText(font, status);
        font.draw(batch, layout, 20, 450);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    public void updateEmotion() {
        Emotion newEmotion = pet.getState().getEmotion();
        if (newEmotion != currentEmotion) {
            currentEmotion = newEmotion;
            stateTime = 0f;
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
        stage.dispose();
        feedTexture.dispose();
        playTexture.dispose();
        sleepTexture.dispose();
        for (Animation<TextureRegion> anim : emotionAnimations.values()) {
            for (TextureRegion region : anim.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
    }
}
