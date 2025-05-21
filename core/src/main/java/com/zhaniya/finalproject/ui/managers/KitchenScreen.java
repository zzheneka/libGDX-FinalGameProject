package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.model.FoodItem;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.GameScreen;

import java.util.Map;

public class KitchenScreen implements Screen {
    private final Game game;
    private final Pet pet;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;

    private KitchenManager kitchenManager;
    private AnimationManager animationManager;
    private Texture kitchenTexture;
    private Texture fridgeClosedTexture;
    private Texture fridgeOpenTexture;
    private Texture backTexture;

    private Label energyLabel;

    public KitchenScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;

        kitchenManager = new KitchenManager();
        animationManager = new AnimationManager(pet);

        batch = new SpriteBatch();
        font = new BitmapFont();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        kitchenTexture = new Texture(Gdx.files.internal("backgrounds/kitchen.png"));
        fridgeClosedTexture = new Texture(Gdx.files.internal("ui/fridge_closed.png"));
        fridgeOpenTexture = new Texture(Gdx.files.internal("ui/fridge_open.png"));
        backTexture = new Texture(Gdx.files.internal("buttoms/back.png"));

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().right().pad(20);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE);

        // Energy label
        energyLabel = new Label("Energy: " + pet.getEnergy(), labelStyle);
        table.add(energyLabel).left().padBottom(20).row();

        // Back button
        ImageButton backButton = createButton(backTexture);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, pet));
                System.out.println("Возврат на главный экран!");
            }
        });
        table.add(backButton).pad(5).size(100, 50).bottom().right().row();

        // Food items
        for (Map.Entry<String, FoodItem> entry : kitchenManager.getFridge().entrySet()) {
            String itemName = entry.getKey();
            FoodItem item = entry.getValue();

            Label foodLabel = new Label(itemName + ": " + item.getQuantity(), labelStyle);
            foodLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (pet.getEnergy() < 80) {
                        if (kitchenManager.consumeItem(itemName)) {
                            pet.increaseEnergy(10);
                            System.out.println("Питомец съел: " + itemName);
                            foodLabel.setText(itemName + ": " + item.getQuantity());
                        } else {
                            System.out.println("Еда закончилась: " + itemName);
                            foodLabel.setText(itemName + ": 0");
                        }
                    } else {
                        System.out.println("Питомец не хочет кушать (энергия выше 80)");
                    }
                }
            });

            table.add(foodLabel).pad(5).left().row();
        }
    }

    private ImageButton createButton(Texture texture) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(texture));
        style.down = new TextureRegionDrawable(new TextureRegion(texture));
        return new ImageButton(style);
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            int fridgeX = 50;
            int fridgeY = 100;
            int fridgeWidth = (int) (fridgeClosedTexture.getWidth() * 0.3f);
            int fridgeHeight = (int) (fridgeClosedTexture.getHeight() * 0.3f);

            if (x >= fridgeX && x <= fridgeX + fridgeWidth && y >= fridgeY && y <= fridgeY + fridgeHeight) {
                kitchenManager.toggleFridgeState();
                System.out.println("Холодильник " + (kitchenManager.isFridgeOpen() ? "открыт!" : "закрыт!"));
            }
        }
    }

    @Override
    public void render(float delta) {
        energyLabel.setText("Energy: " + pet.getEnergy());

        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        batch.draw(kitchenTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Texture fridgeTexture = kitchenManager.isFridgeOpen() ? fridgeOpenTexture : fridgeClosedTexture;
        batch.draw(fridgeTexture, -20, 30, fridgeTexture.getWidth() * 0.3f, fridgeTexture.getHeight() * 0.3f);

        animationManager.render(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

        handleInput();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        kitchenTexture.dispose();
        fridgeClosedTexture.dispose();
        fridgeOpenTexture.dispose();
        backTexture.dispose();
        stage.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
