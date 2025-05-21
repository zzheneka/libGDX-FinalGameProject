package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.zhaniya.finalproject.model.pet.Emotion;
import com.zhaniya.finalproject.model.pet.Pet;

import java.util.EnumMap;

public class AnimationManager {
    private final Pet pet;
    private final EnumMap<Emotion, Animation<TextureRegion>> animations = new EnumMap<>(Emotion.class);
    private float stateTime = 0f;

    public AnimationManager(Pet pet) {
        this.pet = pet;
        loadAnimations();
    }

    private void loadAnimations() {
        String basePath = switch (pet.getType()) {
            case DRAGON -> "dragon";
            case DOG -> "dog";
            case CAT -> "cat";
        };

        animations.put(Emotion.HAPPY, loadAnimation(basePath + "/happy"));
        animations.put(Emotion.SAD, loadAnimation(basePath + "/sad"));
        animations.put(Emotion.SLEEP, loadAnimation(basePath + "/sleep"));
        animations.put(Emotion.DIRTY, loadAnimation(basePath + "/dirty"));
    }

    private Animation<TextureRegion> loadAnimation(String path) {
        TextureRegion[] frames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            Texture texture = new Texture(path + "/frame" + (i + 1) + ".png");
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(0.2f, frames);
    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = getCurrentFrame();
        batch.draw(frame, 200, 50, 180, 180);
    }

    public TextureRegion getCurrentFrame() {
        Emotion currentEmotion = pet.getState().getEmotion();

        // Заменяем ANGRY на SAD, если нет отдельной анимации
        if (currentEmotion == Emotion.ANGRY) {
            currentEmotion = Emotion.SAD;
        }

        Animation<TextureRegion> animation = animations.get(currentEmotion);
        if (animation == null) {
            System.err.println("Анимация не найдена для состояния: " + currentEmotion);
            return animations.getOrDefault(Emotion.HAPPY, animations.values().iterator().next()).getKeyFrames()[0];
        }

        return animation.getKeyFrame(stateTime, true);
    }


    public void dispose() {
        animations.values().forEach(anim -> {
            for (TextureRegion region : anim.getKeyFrames()) {
                region.getTexture().dispose();
            }
        });
    }
}
