package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class UIManager {
    private final Stage stage;
    private ImageButton feedButton, playButton, sleepButton;

    public UIManager() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        createButtons();
    }

    private void createButtons() {
        Texture feedTex = new Texture("ui/buttons/feed_button.png");
        Texture playTex = new Texture("ui/buttons/play_button.png");
        Texture sleepTex = new Texture("ui/buttons/sleep_button.png");

        feedButton = new ImageButton(new TextureRegionDrawable(feedTex));
        playButton = new ImageButton(new TextureRegionDrawable(playTex));
        sleepButton = new ImageButton(new TextureRegionDrawable(sleepTex));

        float y = 20;
        feedButton.setBounds(100, y, 160, 70);
        playButton.setBounds(280, y, 160, 70);
        sleepButton.setBounds(460, y, 160, 70);

        stage.addActor(feedButton);
        stage.addActor(playButton);
        stage.addActor(sleepButton);
    }

    public void addButtonToStage(ImageButton button) {
        stage.addActor(button);
    }

    public void setFeedButtonListener(ClickListener listener) {
        feedButton.addListener(listener);
    }

    public void setPlayButtonListener(ClickListener listener) {
        playButton.addListener(listener);
    }

    public void setSleepButtonListener(ClickListener listener) {
        sleepButton.addListener(listener);
    }

    public Stage getStage() {
        return stage;
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
