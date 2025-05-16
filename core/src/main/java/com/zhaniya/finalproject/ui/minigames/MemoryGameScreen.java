package com.zhaniya.finalproject.ui.minigames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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
import com.zhaniya.finalproject.ui.GameScreen;

public class MemoryGameScreen extends ScreenAdapter {
    private final Game game;
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

    public MemoryGameScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        try {
            background = new Texture(Gdx.files.internal("minigames/memorygame/background.png"));
            System.out.println("Фон загружен успешно!");
        } catch (Exception e) {
            System.err.println("Ошибка загрузки фона: используем резервный фон.");
            background = new Texture(Gdx.files.internal("backgrounds/default_room.png"));
        }

        try {
            cardBack = new Texture(Gdx.files.internal("minigames/memorygame/card_back.png"));
        } catch (Exception e) {
            System.err.println("Ошибка загрузки рубашки карты: используем резервный фон.");
            cardBack = new Texture(Gdx.files.internal("backgrounds/default.png"));
        }

        // Загрузка лицевых сторон карт
        cardFaces = new Array<>();
        for (int i = 1; i <= 5; i++) {
            try {
                Texture card = new Texture(Gdx.files.internal("minigames/memorygame/card" + i + ".png"));
                cardFaces.add(card);
                cardFaces.add(card);
                System.out.println("Загружено: card" + i + ".png");
            } catch (Exception e) {
                System.err.println("Ошибка загрузки карты: card" + i + ".png");
                cardFaces.add(cardBack);
                cardFaces.add(cardBack);
            }
        }

        // Перемешиваем пары
        cardPairs = new Array<>();
        for (int i = 0; i < 10; i++) cardPairs.add(i);
        cardPairs.shuffle();

        createCards();
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
        try {
            // Создаем стиль метки с белым шрифтом
            BitmapFont font = new BitmapFont();
            Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

            // Победное сообщение
            Label victoryLabel = new Label("🎉 Победа! Все пары найдены!", labelStyle);
            victoryLabel.setFontScale(2);
            victoryLabel.setPosition(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f + 50);
            stage.addActor(victoryLabel);

            // Загружаем текстуру кнопки "Back"
            Texture buttonTexture;
            try {
                buttonTexture = new Texture(Gdx.files.internal("ui/buttons/back.png"));
                System.out.println("Кнопка Back успешно загружена.");
            } catch (Exception e) {
                System.err.println("Ошибка при загрузке кнопки Back: " + e.getMessage());
                buttonTexture = new Texture(Gdx.files.internal("ui/buttons/default_back.png")); // резервная кнопка
            }

            // Создаем стиль кнопки с использованием текстуры
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
            buttonStyle.up = new Image(buttonTexture).getDrawable();
            buttonStyle.font = font;

            // Кнопка "Back"
            TextButton backButton = new TextButton("", buttonStyle); // Пустой текст, чтобы отображалась картинка
            backButton.setSize(150, 50);
            backButton.setPosition(Gdx.graphics.getWidth() / 2f - 75, Gdx.graphics.getHeight() / 2f - 50);

            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game, null));
                    System.out.println("Возврат на главный экран.");
                }
            });

            stage.addActor(backButton);
        } catch (Exception e) {
            System.err.println("Ошибка при создании кнопки: " + e.getMessage());
        }
    }




    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        if (background != null) {
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (background != null) background.dispose();
        if (cardBack != null) cardBack.dispose();
        for (Texture card : cardFaces) {
            if (card != null) card.dispose();
        }
        stage.dispose();
    }
}
