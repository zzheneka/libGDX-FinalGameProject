package com.zhaniya.finalproject.ui.minigames;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class ObstacleRunGameScreen extends ScreenAdapter {
    private final Game game;
    private SpriteBatch batch;
    private Texture background;
    private Texture playerTexture;
    private Texture obstacleTexture;
    private float playerX, playerY;
    private float obstacleX, obstacleY;
    private float speed = 300;
    private float gravity = -800;
    private float jumpVelocity = 500;
    private float playerVelocityY = 0;
    private boolean isJumping = false;
    private int score = 0;

    private BitmapFont font;
    private GlyphLayout layout;

    public ObstacleRunGameScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("minigames/obstaclerun/background.png");
        playerTexture = new Texture("minigames/obstaclerun/player.png");
        obstacleTexture = new Texture("minigames/obstaclerun/obstacle.png");

        playerX = 100;
        playerY = 50;
        obstacleX = Gdx.graphics.getWidth();
        obstacleY = 50;

        font = new BitmapFont();
        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Отображение персонажа и препятствия
        batch.draw(playerTexture, playerX, playerY, 64, 64);
        batch.draw(obstacleTexture, obstacleX, obstacleY, 64, 64);

        // Отображение очков
        layout.setText(font, "Score: " + score);
        font.draw(batch, layout, 20, Gdx.graphics.getHeight() - 20);

        batch.end();
    }

    private void update(float delta) {
        handleInput();
        updatePlayer(delta);
        updateObstacle(delta);
        checkCollision();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && playerY <= 50) {
            isJumping = true;
            playerVelocityY = jumpVelocity;
        }
    }

    private void updatePlayer(float delta) {
        if (isJumping) {
            playerVelocityY += gravity * delta;
            playerY += playerVelocityY * delta;

            // Если приземлился
            if (playerY <= 50) {
                playerY = 50;
                playerVelocityY = 0;
                isJumping = false;
            }
        }
    }

    private void updateObstacle(float delta) {
        obstacleX -= speed * delta;

        // Если препятствие ушло за экран
        if (obstacleX < -64) {
            obstacleX = Gdx.graphics.getWidth();
            score++;  // Увеличиваем очки за пройденное препятствие
            speed += 10;  // Увеличиваем скорость постепенно
        }
    }

    private void checkCollision() {
        Rectangle playerRect = new Rectangle(playerX, playerY, 64, 64);
        Rectangle obstacleRect = new Rectangle(obstacleX, obstacleY, 64, 64);

        if (playerRect.overlaps(obstacleRect)) {
            System.out.println("Столкновение! Игра начнётся заново.");
            restartGame();
        }
    }

    private void restartGame() {
        playerX = 100;
        playerY = 50;
        obstacleX = Gdx.graphics.getWidth();
        score = 0;
        speed = 300;
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        playerTexture.dispose();
        obstacleTexture.dispose();
        font.dispose();
    }
}
