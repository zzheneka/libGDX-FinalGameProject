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
    private Game game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;

    private KitchenManager kitchenManager;
    private AnimationManager animationManager;
    private Texture kitchenTexture;
    private Texture fridgeClosedTexture;
    private Texture fridgeOpenTexture;
    private Texture backTexture;

    private Pet pet;

    public KitchenScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;

        // Инициализация менеджеров
        kitchenManager = new KitchenManager();
        animationManager = new AnimationManager(pet);

        // Инициализация рендеров
        batch = new SpriteBatch();
        font = new BitmapFont();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Загрузка текстур
        kitchenTexture = new Texture(Gdx.files.internal("backgrounds/kitchen.png"));
        fridgeClosedTexture = new Texture(Gdx.files.internal("ui/fridge_closed.png"));
        fridgeOpenTexture = new Texture(Gdx.files.internal("ui/fridge_open.png"));
        backTexture = new Texture(Gdx.files.internal("buttoms/back.png"));

        // Создаем UI
        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().right().pad(20);
        stage.addActor(table);

        // Кнопка Back в правом нижнем углу
        ImageButton backButton = createButton(backTexture);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, pet));
                System.out.println("Возврат на главный экран!");
            }
        });

        // Добавляем кнопку "Back" в правый нижний угол
        table.add(backButton).pad(5).size(100, 50).bottom().right().row();

        // Добавляем продукты из холодильника
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE);

        for (Map.Entry<String, FoodItem> entry : kitchenManager.getFridge().entrySet()) {
            String itemName = entry.getKey();
            FoodItem item = entry.getValue();

            // Создаем метку с продуктом
            Label foodLabel = new Label(itemName + ": " + item.getQuantity(), labelStyle);

            // Добавляем обработчик нажатия на продукт
            foodLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (kitchenManager.consumeItem(itemName)) {
                        pet.increaseEnergy(5);
                        System.out.println("Питомец съел: " + itemName);
                        foodLabel.setText(itemName + ": " + item.getQuantity());
                    } else {
                        System.out.println("Еда закончилась: " + itemName);
                        foodLabel.setText(itemName + ": 0");
                    }

                    // Эффект уменьшения при нажатии
                    foodLabel.setFontScale(0.8f); // уменьшить при нажатии
                    Gdx.app.postRunnable(() -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        foodLabel.setFontScale(1.0f); // вернуть размер
                    });
                }
            });

            table.add(foodLabel).pad(5).left().row();
        }
    }

    private ImageButton createButton(Texture texture) {
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(texture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(texture));
        return new ImageButton(buttonStyle);
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            System.out.println("Клик: X=" + x + ", Y=" + y);

            // Координаты холодильника
            int fridgeX = 50;
            int fridgeY = 100;
            int fridgeWidth = (int) (fridgeClosedTexture.getWidth() * 0.3f);
            int fridgeHeight = (int) (fridgeClosedTexture.getHeight() * 0.3f);

            // Проверка клика по холодильнику
            if (x >= fridgeX && x <= fridgeX + fridgeWidth && y >= fridgeY && y <= fridgeY + fridgeHeight) {
                kitchenManager.toggleFridgeState();
                System.out.println("Холодильник " + (kitchenManager.isFridgeOpen() ? "открыт!" : "закрыт!"));
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();

        // Отображаем фон кухни
        batch.draw(kitchenTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Логика отображения холодильника
        Texture fridgeTexture = kitchenManager.isFridgeOpen() ? fridgeOpenTexture : fridgeClosedTexture;
        batch.draw(fridgeTexture, -20, 30, fridgeTexture.getWidth() * 0.3f, fridgeTexture.getHeight() * 0.3f);

        // Анимация питомца
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
        System.out.println("Ресурсы экрана кухни очищены.");
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
