package com.aerialglide;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class BaseScreen extends ScreenAdapter {
    protected static final float WORLD_WIDTH = 800f;
    protected static final float WORLD_HEIGHT = 600f;

    protected final AerialGlideGame game;
    protected final SpriteBatch batch;
    protected final BitmapFont font;
    protected final OrthographicCamera camera;
    protected final FitViewport viewport;

    protected BaseScreen(AerialGlideGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.viewport.apply();
        this.camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0f);
        this.camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    protected float mouseWorldX() {
        return Gdx.input.getX() * WORLD_WIDTH / Gdx.graphics.getWidth();
    }

    protected float mouseWorldY() {
        return WORLD_HEIGHT - (Gdx.input.getY() * WORLD_HEIGHT / Gdx.graphics.getHeight());
    }
}
