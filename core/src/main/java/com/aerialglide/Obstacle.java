package com.aerialglide;

import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
    private float x;
    private final float y;
    private final float width;
    private final float height;
    private final boolean top;

    public Obstacle(float x, float y, float width, float height, boolean top) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.top = top;
    }

    public void move(float speed) {
        x -= speed;
    }

    public boolean isOffscreen() {
        return x + width < 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public boolean isTop() { return top; }
}
