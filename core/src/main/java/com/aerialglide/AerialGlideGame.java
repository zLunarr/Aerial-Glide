package com.aerialglide;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class AerialGlideGame extends Game {
    public static final String BACKGROUND = "Resources/background.png";
    public static final String GAME_BACKGROUND = "Resources/fondo juego.jpg";
    public static final String BIRD = "Resources/bird.png";
    public static final String OBSTACLE_TOP = "Resources/obstacle - copia.png";
    public static final String OBSTACLE_BOTTOM = "Resources/obstacle.png";
    public static final String MUSIC = "Resources/Juego 35.wav";

    private AssetManager assetManager;
    private Music backgroundMusic;
    private boolean musicEnabled;

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetManager.load(BACKGROUND, Texture.class);
        assetManager.load(GAME_BACKGROUND, Texture.class);
        assetManager.load(BIRD, Texture.class);
        assetManager.load(OBSTACLE_TOP, Texture.class);
        assetManager.load(OBSTACLE_BOTTOM, Texture.class);
        assetManager.load(MUSIC, Music.class);
        assetManager.finishLoading();

        musicEnabled = getPreferences().getBoolean("musicEnabled", true);
        backgroundMusic = assetManager.get(MUSIC, Music.class);
        backgroundMusic.setLooping(true);
        syncMusicState();

        setScreen(new MenuScreen(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Preferences getPreferences() {
        return Gdx.app.getPreferences("aerial-glide");
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        getPreferences().putBoolean("musicEnabled", enabled).flush();
        syncMusicState();
    }

    public void syncMusicState() {
        if (backgroundMusic == null) {
            return;
        }

        if (musicEnabled && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        } else if (!musicEnabled && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (assetManager != null) {
            assetManager.dispose();
        }
    }
}
