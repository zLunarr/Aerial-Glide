package com.aerialglide;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class OptionsScreen extends BaseScreen {
    private final Texture background;

    public OptionsScreen(AerialGlideGame game) {
        super(game);
        this.background = game.getAssetManager().get(AerialGlideGame.BACKGROUND, Texture.class);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setMusicEnabled(!game.isMusicEnabled());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.B) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        font.getData().setScale(1.8f);
        font.draw(batch, "OPCIONES", 300, 470);
        font.getData().setScale(1.1f);
        font.draw(batch, "M - Activar/Desactivar musica: " + (game.isMusicEnabled() ? "ON" : "OFF"), 170, 320);
        font.draw(batch, "B o ESC - Volver al menu", 250, 270);
        batch.end();
    }
}
