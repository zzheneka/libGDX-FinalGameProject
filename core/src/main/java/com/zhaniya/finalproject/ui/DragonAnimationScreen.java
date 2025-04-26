package com.zhaniya.finalproject.ui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class DragonAnimationScreen implements Screen {
    private final Game game;
    private SpriteBatch batch;
    private Animation<TextureRegion> dragonAnimation;
    private float stateTime;

    public DragonAnimationScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Загружаем кадры
        TextureRegion[] frames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            Texture texture = new Texture(Gdx.files.internal("dragon/happy/frame" + (i + 1) + ".png"));
            frames[i] = new TextureRegion(texture);
        }

        // Создаем анимацию
        dragonAnimation = new Animation<>(0.25f, frames);
        stateTime = 0f;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        stateTime += delta;

        TextureRegion currentFrame = dragonAnimation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(currentFrame, 150, 150, 200, 200);
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        for (TextureRegion frame : dragonAnimation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }
}
