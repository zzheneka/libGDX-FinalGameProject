package com.zhaniya.finalproject.ui.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.Game;
import com.zhaniya.finalproject.ui.GameScreen;

// Импортируем классы мини-игр
import com.zhaniya.finalproject.ui.minigames.CatchFoodGameScreen;
import com.zhaniya.finalproject.ui.minigames.MemoryGameScreen;
import com.zhaniya.finalproject.ui.minigames.ObstacleRunGameScreen;

public class MiniGameSelectionScreen extends ScreenAdapter {
    private final Game game;
    private Stage stage;
    private BitmapFont font;

    // Массив с названиями игр
    private final String[] gameNames = {"Game 1", "Game 2", "Game 3"};

    public MiniGameSelectionScreen(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();  // Используем стандартный шрифт
        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // Стиль для меток
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        // Заголовок
        Label title = new Label("Выберите мини-игру", labelStyle);
        title.setFontScale(2);

        // Добавляем заголовок на экран
        table.add(title).padBottom(40).row();

        // Динамическое создание кнопок на основе массива названий игр
        for (String gameName : gameNames) {
            TextButton gameButton = createGameButton(gameName);

            // Обработка нажатия на кнопку
            gameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    startGame(gameName);
                }
            });

            // Добавляем кнопку на экран
            table.add(gameButton).pad(15).width(300).height(80).row();
        }

        // Кнопка "Назад" для возврата на главный экран
        TextButton backButton = createGameButton("Назад");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, null));
                System.out.println("Возврат на главный экран.");
            }
        });

        // Добавляем кнопку "Назад"
        table.add(backButton).padTop(30).width(200).height(50);
    }

    // Метод для запуска игры по названию
    private void startGame(String gameName) {
        switch (gameName) {
            case "Game 1":
                game.setScreen(new CatchFoodGameScreen(game));
                System.out.println("Запуск Catch Food");
                break;
            case "Game 2":
                game.setScreen(new MemoryGameScreen(game));
                System.out.println("Запуск Memory Game");
                break;
            case "Game 3":
                game.setScreen(new ObstacleRunGameScreen(game));
                System.out.println("Запуск Obstacle Run");
                break;
            default:
                System.out.println("Игра не найдена: " + gameName);
        }
    }

    // Метод для создания кнопки с текстом
    private TextButton createGameButton(String text) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton button = new TextButton(text, buttonStyle);
        button.getLabel().setFontScale(1.5f);  // Увеличенный текст на кнопке
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);  // Тёмный фон
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
