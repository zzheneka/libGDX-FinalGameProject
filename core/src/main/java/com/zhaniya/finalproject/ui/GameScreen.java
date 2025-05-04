// GameScreen.java — обновлённый с исправленной структурой и работающим переключением еды и лекарств
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.commands.PlayCommand;
import com.zhaniya.finalproject.model.pet.Emotion;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.utils.TimerUtil;

import java.util.ArrayList;
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
    private Texture sleepSceneTexture, playgroundTexture, feedingRoomTexture;
    private Texture arrowLeftTexture, arrowRightTexture;

    private ImageButton feedButton, playButton, sleepButton;

    private boolean isSleeping = false;
    private boolean isFeeding = false;

    private ArrayList<Texture> foodList;
    private ArrayList<Texture> medicineList;
    private int selectedItemIndex = 0;
    private boolean showingFood = true;

    private String commentText = "";
    private float commentTimer = 0f;
    private final float COMMENT_DURATION = 3f;

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout = new GlyphLayout();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TimerUtil.startPetTimer(pet);

        loadAnimations();
        loadButtons();
        loadFeedingAssets();
    }

    private void loadAnimations() {
        String basePath = switch (pet.getType()) {
            case DRAGON -> "dragon";
            case CAT -> "cat";
            case DOG -> "dog";
        };

        emotionAnimations = new EnumMap<>(Emotion.class);
        emotionAnimations.put(Emotion.HAPPY, loadAnimation(basePath + "/happy", 0.2f));
        emotionAnimations.put(Emotion.SAD, loadAnimation(basePath + "/sad", 0.2f));

        sleepSceneTexture = new Texture("backgrounds/sleep_with_dragon.png");
        playgroundTexture = new Texture("backgrounds/playground.png");
        feedingRoomTexture = new Texture("backgrounds/kitchen.png");

        currentEmotion = pet.getState().getEmotion();
        stateTime = 0f;
    }

    private void loadFeedingAssets() {
        arrowLeftTexture = new Texture("ui/arrows/left.png");
        arrowRightTexture = new Texture("ui/arrows/right.png");

        foodList = new ArrayList<>();
        medicineList = new ArrayList<>();


        medicineList.add(new Texture("ui/medicine/medicine1.png"));
        foodList.add(new Texture("ui/food/milk.png"));
        foodList.add(new Texture("ui/food/apple1.png"));
        foodList.add(new Texture("ui/food/burger.png"));
        foodList.add(new Texture("ui/food/orange.png"));



    }

    private Animation<TextureRegion> loadAnimation(String folderPath, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            Texture texture = new Texture(folderPath + "/frame" + (i + 1) + ".png");
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }

    private void loadButtons() {
        feedTexture = new Texture("ui/buttons/feed_button.png");
        playTexture = new Texture("ui/buttons/play_button.png");
        sleepTexture = new Texture("ui/buttons/sleep_button.png");

        feedButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(feedTexture)));
        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)));
        sleepButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(sleepTexture)));

        feedButton.setSize(160, 70);
        playButton.setSize(160, 70);
        sleepButton.setSize(160, 70);

        float centerX = Gdx.graphics.getWidth() / 2f - 240;
        feedButton.setPosition(centerX, 20);
        playButton.setPosition(centerX + 180, 20);
        sleepButton.setPosition(centerX + 360, 20);

        sleepButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pet.getEnergy() < 50) {
                    isSleeping = true;
                    isFeeding = false;
                    pet.sleep();
                    commentText = "Zzz... Sleeping time!";
                } else {
                    commentText = "I'm not tired yet!";
                }
                commentTimer = COMMENT_DURATION;
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new PlayCommand(pet).execute();
                isSleeping = false;
                isFeeding = false;
                updateEmotion();
                commentText = "Yay! That was fun!";
                commentTimer = COMMENT_DURATION;
            }
        });

        feedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pet.getEnergy() < 100) {
                    isSleeping = false;
                    isFeeding = true;
                    selectedItemIndex = 0;
                    showingFood = true;
                    commentText = "Choose what to eat or take medicine";
                } else {
                    isFeeding = false;
                    commentText = "I'm full of energy!";
                }
                commentTimer = COMMENT_DURATION;
            }
        });

        stage.addActor(feedButton);
        stage.addActor(playButton);
        stage.addActor(sleepButton);
    }

    private void updateEmotion() {
        Emotion newEmotion = pet.getEnergy() < 50 ? Emotion.SAD : pet.getState().getEmotion();
        if (newEmotion != currentEmotion) {
            currentEmotion = newEmotion;
            stateTime = 0f;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        stateTime += delta;

        if (!isSleeping) updateEmotion();

        batch.begin();

        if (isSleeping) {
            batch.draw(sleepSceneTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else {
            batch.draw(isFeeding ? feedingRoomTexture : playgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        if (isFeeding) {
            Texture currentItem = showingFood ? foodList.get(selectedItemIndex) : medicineList.get(selectedItemIndex);
            batch.draw(currentItem, 250, 300, 100, 100);
            batch.draw(arrowLeftTexture, 180, 320, 50, 50);
            batch.draw(arrowRightTexture, 370, 320, 50, 50);
        }

        if (!isSleeping) {
            TextureRegion currentFrame = emotionAnimations.get(currentEmotion).getKeyFrame(stateTime, true);
            batch.draw(currentFrame, 200, 50, 180, 180);
        }

        font.getData().setScale(1.5f);
        layout.setText(font, "Energy: " + pet.getEnergy() + "\nMood: " + pet.getMood());
        font.draw(batch, layout, 20, 450);

        if (commentTimer > 0) {
            layout.setText(font, "Drako: " + commentText);
            font.draw(batch, layout, 20, 400);
            commentTimer -= delta;
        }

        batch.end();
        stage.act(delta);
        stage.draw();

        // Обработка стрелок (переключение по кругу)
        if (isFeeding && Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 180 && x <= 230 && y >= 320 && y <= 370) {
                if (showingFood) selectedItemIndex = (selectedItemIndex - 1 + foodList.size()) % foodList.size();
                else selectedItemIndex = (selectedItemIndex - 1 + medicineList.size()) % medicineList.size();
            } else if (x >= 370 && x <= 420 && y >= 320 && y <= 370) {
                if (showingFood) selectedItemIndex = (selectedItemIndex + 1) % foodList.size();
                else selectedItemIndex = (selectedItemIndex + 1) % medicineList.size();
            } else if (x >= 250 && x <= 350 && y >= 300 && y <= 400) {
                if (showingFood) pet.feed(10);
                else {
                    pet.setHealth(pet.getHealth() + 10);
                    pet.setMood("Happy");
                }
                commentText = showingFood ? "Yummy! Energy increased!" : "Feeling better!";
                commentTimer = COMMENT_DURATION;
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
        stage.dispose();

        feedTexture.dispose();
        playTexture.dispose();
        sleepTexture.dispose();
        sleepSceneTexture.dispose();
        playgroundTexture.dispose();
        feedingRoomTexture.dispose();
        arrowLeftTexture.dispose();
        arrowRightTexture.dispose();

        for (Texture tex : foodList) tex.dispose();
        for (Texture tex : medicineList) tex.dispose();

        for (Animation<TextureRegion> anim : emotionAnimations.values()) {
            for (TextureRegion region : anim.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
    }
}
