package com.aerialglide;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MenuScreen extends BaseScreen {
    private final Texture background;

    public MenuScreen(AerialGlideGame game) {
        super(game);
        this.background = game.getAssetManager().get(AerialGlideGame.BACKGROUND, Texture.class);
        game.syncMusicState();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new PlayScreen(game));
            dispose();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            game.setScreen(new OptionsScreen(game));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        font.getData().setScale(2f);
        font.draw(batch, "AERIAL GLIDE", 250, 520);
        font.getData().setScale(1.1f);
        font.draw(batch, "ENTER - Jugar", 310, 280);
        font.draw(batch, "O - Opciones", 310, 240);
        font.draw(batch, "ESPACIO - Saltar durante la partida", 220, 180);
        batch.end();
    }
}
