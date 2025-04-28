package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.zhaniya.finalproject.model.pet.*;

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

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        emotionAnimations = new EnumMap<>(Emotion.class);

        // 💬 Выбираем базовый путь в зависимости от типа питомца
        String basePath = "";
        switch (pet.getType()) {
            case DRAGON -> basePath = "dragon";
            case CAT -> basePath = "cat";
            case DOG -> basePath = "dog";
        }

        // 💬 Загружаем эмоции для выбранного питомца
        emotionAnimations.put(Emotion.HAPPY, loadAnimation(basePath + "/happy", 0.2f));
        emotionAnimations.put(Emotion.ANGRY, loadAnimation(basePath + "/angry", 0.2f));
        emotionAnimations.put(Emotion.SAD, loadAnimation(basePath + "/sad", 0.2f));
        emotionAnimations.put(Emotion.SLEEP, loadAnimation(basePath + "/sleep", 0.4f));

        currentEmotion = pet.getState().getEmotion();
        stateTime = 0f;

        font = new BitmapFont();
        layout = new GlyphLayout();
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
        batch.draw(currentFrame, 150, 150, 200, 200);

        font.getData().setScale(1.5f);
        String status = "Энергия: " + pet.getEnergy() + "\nНастроение: " + pet.getMood();
        layout.setText(font, status);
        font.draw(batch, layout, 20, 450);
        batch.end();
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
        font.dispose();
        for (Animation<TextureRegion> anim : emotionAnimations.values()) {
            for (TextureRegion region : anim.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
    }
}
