package com.zhaniya.finalproject.ui.minigames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.ui.GameScreen;

public class MemoryGameScreen extends ScreenAdapter {
    private final Game game;
    private final Pet pet;
    private SpriteBatch batch;
    private Texture background;
    private Texture cardBack;
    private Array<Texture> cardFaces;
    private Array<ImageButton> cardButtons;
    private Array<Integer> cardPairs;

    private Stage stage;
    private ImageButton firstCard, secondCard;
    private boolean isFlipped = false;
    private int flippedCount = 0;

    public MemoryGameScreen(Game game, Pet pet) {
        this.game = game;
        this.pet = pet;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        loadTextures();
        createCards();
    }

    private void loadTextures() {
        // Загрузка фона
        background = new Texture(Gdx.files.internal("backgrounds/default_room.png"));

        // Загрузка рубашки карты
        cardBack = new Texture(Gdx.files.internal("minigames/memorygame/card_back.png"));

        // Загрузка лицевых сторон карт
        cardFaces = new Array<>();
        for (int i = 1; i <= 5; i++) {
            Texture card = new Texture(Gdx.files.internal("minigames/memorygame/card" + i + ".png"));
            cardFaces.add(card);
            cardFaces.add(card);
        }

        // Перемешиваем карты
        cardPairs = new Array<>();
        for (int i = 0; i < 10; i++) cardPairs.add(i);
        cardPairs.shuffle();
    }

    private void createCards() {
        cardButtons = new Array<>();
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        for (int i = 0; i < 10; i++) {
            final int index = cardPairs.get(i);
            TextureRegionDrawable cardDrawable = new TextureRegionDrawable(cardBack);
            ImageButton cardButton = new ImageButton(cardDrawable);
            cardButtons.add(cardButton);

            cardButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    flipCard(cardButton, index);
                }
            });

            table.add(cardButton).pad(10).size(100, 150);
            if ((i + 1) % 5 == 0) table.row();
        }
        stage.addActor(table);
    }

    private void flipCard(ImageButton cardButton, int index) {
        if (isFlipped && firstCard == cardButton) return;

        TextureRegionDrawable face = new TextureRegionDrawable(cardFaces.get(index));
        cardButton.getStyle().imageUp = face;

        if (!isFlipped) {
            firstCard = cardButton;
            isFlipped = true;
        } else {
            secondCard = cardButton;
            isFlipped = false;

            int firstIndex = cardButtons.indexOf(firstCard, true);
            int secondIndex = cardButtons.indexOf(secondCard, true);

            if ((cardPairs.get(firstIndex) / 2) == (cardPairs.get(secondIndex) / 2)) {
                flippedCount += 2;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        firstCard.remove();
                        secondCard.remove();
                    }
                }, 0.5f);

                if (flippedCount == 10) showVictoryMessage();
            } else {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        resetCard(firstCard);
                        resetCard(secondCard);
                    }
                }, 1.0f);
            }
        }
    }

    private void resetCard(ImageButton cardButton) {
        cardButton.getStyle().imageUp = new TextureRegionDrawable(cardBack);
    }

    private void showVictoryMessage() {
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, com.badlogic.gdx.graphics.Color.WHITE);

        Label victoryLabel = new Label("🎉 Победа! Все пары найдены!", labelStyle);
        victoryLabel.setFontScale(2);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(victoryLabel).padBottom(20).row();

        // Кнопка "Back" с текстурой
        Texture backButtonTexture = new Texture(Gdx.files.internal("buttoms/back.png"));
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backButtonTexture));
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, pet));  // Передаем питомца обратно
                System.out.println("Игра завершена! Возврат на экран игры.");
            }
        });

        // Добавляем кнопку "Back" под сообщением о победе
        table.add(backButton).padTop(20).width(150).height(75);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        cardBack.dispose();
        for (Texture card : cardFaces) {
            card.dispose();
        }
        stage.dispose();
    }
}
