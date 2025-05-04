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
import com.zhaniya.finalproject.commands.FeedCommand;
import com.zhaniya.finalproject.commands.PlayCommand;
import com.zhaniya.finalproject.commands.SleepCommand;
import com.zhaniya.finalproject.model.pet.Emotion;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.utils.TimerUtil;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

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
    private Texture appleTexture, juiceTexture, soupTexture, meatTexture, medicineTexture;
    private Texture leftArrowTexture, rightArrowTexture;

    private ImageButton feedButton, playButton, sleepButton;
    private ImageButton leftArrow, rightArrow;

    private boolean showFoodMenu = false;
    private List<ImageButton> foodButtons = new ArrayList<>();
    private int foodIndex = 0;

    public GameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        emotionAnimations = new EnumMap<>(Emotion.class);
        font = new BitmapFont();
        layout = new GlyphLayout();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Запуск таймера автоматического обновления
        TimerUtil.startPetTimer(pet);

        loadAnimations();
        loadButtons();
        loadFoodItems();

        updateEmotion();
        updateVisibleFood();
        toggleFoodButtons(false);
    }

    private void loadAnimations() {
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

        feedButton.setSize(120, 50);
        playButton.setSize(120, 50);
        sleepButton.setSize(120, 50);

        feedButton.setPosition(40, 30);
        playButton.setPosition(180, 30);
        sleepButton.setPosition(320, 30);

        feedButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                showFoodMenu = !showFoodMenu;
                toggleFoodButtons(showFoodMenu);
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

        stage.addActor(feedButton);
        stage.addActor(playButton);
        stage.addActor(sleepButton);

        leftArrowTexture = new Texture("ui/arrows/left.png");
        rightArrowTexture = new Texture("ui/arrows/right.png");

        leftArrow = new ImageButton(new TextureRegionDrawable(new TextureRegion(leftArrowTexture)));
        rightArrow = new ImageButton(new TextureRegionDrawable(new TextureRegion(rightArrowTexture)));

        leftArrow.setSize(48, 48);
        rightArrow.setSize(48, 48);
        leftArrow.setPosition(100, 80);
        rightArrow.setPosition(340, 80);

        leftArrow.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                if (foodIndex > 0) {
                    foodIndex--;
                    updateVisibleFood();
                }
            }
        });

        rightArrow.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                if (foodIndex < foodButtons.size() - 1) {
                    foodIndex++;
                    updateVisibleFood();
                }
            }
        });

        stage.addActor(leftArrow);
        stage.addActor(rightArrow);
    }

    private void loadFoodItems() {
        appleTexture = new Texture("ui/food/apple.png");
        juiceTexture = new Texture("ui/food/juice.png");
        soupTexture = new Texture("ui/food/soup.png");
        meatTexture = new Texture("ui/food/meat.png");
        medicineTexture = new Texture("ui/medicine/medicine.png");

        foodButtons.add(createItemButton(appleTexture, 200, 80, 10));
        foodButtons.add(createItemButton(juiceTexture, 200, 80, 10));
        foodButtons.add(createItemButton(soupTexture, 200, 80, 10));
        foodButtons.add(createItemButton(meatTexture, 200, 80, 10));
        foodButtons.add(createItemButton(medicineTexture, 200, 80, 20));

        for (ImageButton btn : foodButtons) {
            stage.addActor(btn);
        }
    }

    private ImageButton createItemButton(Texture texture, float x, float y, int energyBoost) {
        ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setSize(64, 64);
        button.setPosition(x, y);
        button.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                new FeedCommand(pet).execute();
                pet.setEnergy(pet.getEnergy() + energyBoost);
                updateEmotion();
            }
        });
        return button;
    }

    private void toggleFoodButtons(boolean visible) {
        for (ImageButton btn : foodButtons) {
            btn.setVisible(visible);
        }
        leftArrow.setVisible(visible);
        rightArrow.setVisible(visible);
    }

    private void updateVisibleFood() {
        for (int i = 0; i < foodButtons.size(); i++) {
            foodButtons.get(i).setVisible(i == foodIndex);
        }
    }

    public void updateEmotion() {
        Emotion newEmotion = pet.getState().getEmotion();
        if (newEmotion != currentEmotion) {
            currentEmotion = newEmotion;
            stateTime = 0f;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        stateTime += delta;

        updateEmotion();

        TextureRegion currentFrame = emotionAnimations.get(currentEmotion).getKeyFrame(stateTime, true);

        batch.begin();

        font.getData().setScale(2f);
        layout.setText(font, "Your Pet");
        font.draw(batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, 550);

        batch.draw(currentFrame, 150, 150, 200, 200);

        font.getData().setScale(1.5f);
        layout.setText(font, "Energy: " + pet.getEnergy() + "\nMood: " + pet.getMood());
        font.draw(batch, layout, 20, 450);

        batch.end();

        stage.act(delta);
        stage.draw();
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
        appleTexture.dispose();
        juiceTexture.dispose();
        soupTexture.dispose();
        meatTexture.dispose();
        medicineTexture.dispose();
        leftArrowTexture.dispose();
        rightArrowTexture.dispose();

        for (Animation<TextureRegion> anim : emotionAnimations.values()) {
            for (TextureRegion region : anim.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
    }
}
