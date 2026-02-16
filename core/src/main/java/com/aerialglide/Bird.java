package com.aerialglide;

import com.badlogic.gdx.math.Rectangle;

public class Bird {
    private float x;
    private float y;
    private float velocity;

    private static final float WIDTH = 90f;
    private static final float HEIGHT = 90f;
    private static final float GRAVITY = 1f;

    public Bird(float x, float y) {
        this.x = x;
        this.y = y;
        this.velocity = 0f;
    }

    public void update(float panelHeight) {
        y += velocity;
        velocity += GRAVITY;

        if (y + HEIGHT >= panelHeight) {
            y = panelHeight - HEIGHT;
            if (velocity > 0) {
                velocity = 0;
            }
        }

        if (y < 0) {
            y = 0;
            if (velocity < 0) {
                velocity = 0;
            }
        }
    }

    public void jump() {
        velocity = -15f;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return WIDTH; }
    public float getHeight() { return HEIGHT; }
}
