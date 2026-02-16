package com.aerialglide;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PlayScreen extends BaseScreen {
    private static final int OBSTACLE_WIDTH = 120;
    private static final int OBSTACLE_GAP = 210;
    private static final float OBSTACLE_SPEED = 10f;
    private static final float FIXED_STEP = 1f / 50f;

    private final Texture background;
    private final Texture birdTexture;
    private final Texture obstacleTopTexture;
    private final Texture obstacleBottomTexture;
    private final List<Obstacle> obstacles;
    private final Random random;

    private Bird bird;
    private int tickCounter;
    private int score;
    private int highScore;
    private float accumulator;
    private boolean gameOver;

    public PlayScreen(AerialGlideGame game) {
        super(game);
        this.background = game.getAssetManager().get(AerialGlideGame.GAME_BACKGROUND, Texture.class);
        this.birdTexture = game.getAssetManager().get(AerialGlideGame.BIRD, Texture.class);
        this.obstacleTopTexture = game.getAssetManager().get(AerialGlideGame.OBSTACLE_TOP, Texture.class);
        this.obstacleBottomTexture = game.getAssetManager().get(AerialGlideGame.OBSTACLE_BOTTOM, Texture.class);
        this.obstacles = new ArrayList<>();
        this.random = new Random();
        resetGame();
    }

    @Override
    public void render(float delta) {
        handleInput();

        if (!gameOver) {
            accumulator += delta;
            while (accumulator >= FIXED_STEP) {
                updateTick();
                accumulator -= FIXED_STEP;
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.draw(birdTexture, bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());

        for (Obstacle obstacle : obstacles) {
            Texture texture = obstacle.isTop() ? obstacleTopTexture : obstacleBottomTexture;
            batch.draw(texture, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        }

        font.getData().setScale(1.2f);
        font.draw(batch, "Puntuacion: " + score, 20, 570);
        font.draw(batch, "Record: " + highScore, 20, 540);

        if (gameOver) {
            font.getData().setScale(1.6f);
            String message = score > highScore ? "Nuevo record! Puntuacion: " + score : "Perdiste! Puntuacion: " + score;
            font.draw(batch, message, 140, 340);
            font.getData().setScale(1.1f);
            font.draw(batch, "R - Reiniciar", 330, 290);
            font.draw(batch, "ESC - Volver al menu", 290, 250);
        }
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !gameOver) {
            bird.jump();
            score++;
        }

        if (gameOver && Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            resetGame();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    private void updateTick() {
        bird.update(WORLD_HEIGHT);

        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.move(OBSTACLE_SPEED);
            if (obstacle.isOffscreen()) {
                iterator.remove();
                continue;
            }
            if (obstacle.getBounds().overlaps(bird.getBounds())) {
                onLose();
                return;
            }
        }

        tickCounter++;
        if (tickCounter % 80 == 0) {
            generateObstacles();
        }
    }

    private void generateObstacles() {
        int topHeight = random.nextInt((int) (WORLD_HEIGHT - OBSTACLE_GAP - 100)) + 60;
        obstacles.add(new Obstacle(WORLD_WIDTH, 0, OBSTACLE_WIDTH, topHeight, true));
        obstacles.add(new Obstacle(WORLD_WIDTH, topHeight + OBSTACLE_GAP, OBSTACLE_WIDTH,
                WORLD_HEIGHT - topHeight - OBSTACLE_GAP, false));
    }

    private void onLose() {
        gameOver = true;
        if (score > highScore) {
            highScore = score;
            game.getPreferences().putInteger("highScore", highScore).flush();
        }
    }

    private void resetGame() {
        tickCounter = 0;
        score = 0;
        accumulator = 0f;
        gameOver = false;
        bird = new Bird(200, 300);
        obstacles.clear();
        highScore = game.getPreferences().getInteger("highScore", 0);
    }
}
