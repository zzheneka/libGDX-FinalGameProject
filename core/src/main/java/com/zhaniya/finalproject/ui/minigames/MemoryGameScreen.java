package com.zhaniya.finalproject.ui.minigames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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

        // Загрузка фона и рубашки карт
        background = new Texture("minigames/memorygame/background.png");
        cardBack = new Texture("minigames/memorygame/card_back.png");

        // Загрузка лицевых сторон карт
        cardFaces = new Array<>();
        for (int i = 1; i <= 5; i++) {
            Texture card = new Texture("minigames/memorygame/card" + i + ".png");
            cardFaces.add(card);
            cardFaces.add(card); // Две одинаковые для пары
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

            // Слушатель на клик по карте
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

            // Проверка совпадения
            if (cardPairs.get(firstIndex) / 2 == cardPairs.get(secondIndex) / 2) {
                System.out.println("Пара найдена!");
                flippedCount += 2;

                // Удаляем найденные карты с задержкой
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        firstCard.remove();
                        secondCard.remove();
                    }
                }, 0.5f);

                // Проверка на победу
                if (flippedCount == 10) {
                    System.out.println("Победа! Все пары найдены!");
                    showVictoryMessage();
                }
            } else {
                // Переворот обратно через 1 секунду
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
        Label victoryLabel = new Label("🎉 Победа! Все пары найдены!", new Skin(Gdx.files.internal("uiskin.json")));
        victoryLabel.setFontScale(2);
        victoryLabel.setPosition(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f);
        stage.addActor(victoryLabel);
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
        for (Texture card : cardFaces) card.dispose();
        stage.dispose();
    }
}
