/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Squiggly
 */
public class Player {

    GameScreen gameScreen;
    float width = 24 / 16;
    float height = 32 / 16;
    float x = 0;
    float y = 0;
    Texture texture;
    TextureRegion[] regions;

    State state = State.standing;
    float stateTime = 0;
    Animation stand;
    Animation walk;
    boolean faceRight = true;
    Vector2 velocity = new Vector2();

    public enum State {
        standing,
        walking
    }

    public Player(GameScreen gameScreen) {
        texture = new Texture("mario-big-animation.png");
        regions = TextureRegion.split(texture, 24, 32)[0];
        stand = new Animation(0, regions[0]);
        walk = new Animation(8 / 60f, regions[0], regions[1], regions[2]);
        walk.setPlayMode(Animation.PlayMode.LOOP);
        this.gameScreen = gameScreen;

    }

    public void updateCollision() {
        Rectangle boundingBox;
        boundingBox = new Rectangle(x, y, width, height);
        int startX = (int) (x - Math.abs(velocity.x));
        int startY = (int) (y - Math.abs(velocity.y));
        int endX = (int) ((x + width) + Math.abs(velocity.x));
        int endY = (int) ((y + height) + Math.abs(velocity.y));
        List<Rectangle> tiles = getTiles(startX, startY, endX, endY);
        
        for (Rectangle tile : tiles) {
            
        }

    }

    public List<Rectangle> getTiles(int startX, int startY, int endX, int endY) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer layer = (TiledMapTileLayer) gameScreen.map.getLayers().get("level");
                Cell cell = layer.getCell(x, y);

                if (cell != null) {

                    Rectangle rectangle = new Rectangle(x, y, 1, 1);
                    rectangles.add(rectangle);

                }
            }

        }
        
        return rectangles;   
        
    }

    public Animation getCurrentAnimation() {
        switch (state) {
            case standing:
                return stand;
            case walking:
                return walk;
            default:
                throw new IllegalStateException("Unknown Animation State.");
        }
    }

    public void render(SpriteBatch batch) {
        Animation<TextureRegion> currentAnimation = getCurrentAnimation();
        TextureRegion keyFrame = currentAnimation.getKeyFrame(stateTime);
        if (faceRight) {
            batch.draw(keyFrame, x, y, width, height);
        } else {
            batch.draw(keyFrame, x + width, y, -width, height);
        }

    }

    public void update(float delta) {
        stateTime += delta;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            faceRight = true;
            state = State.walking;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                x += 0.5;
            } else {
                x += 0.25;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            faceRight = false;
            state = State.walking;
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                x -= 0.5;
            } else {
                x -= 0.25;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                y += 0.5;
            } else {
                y += 0.25;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                y -= 0.5;
            } else {
                y -= 0.25;
            }
        } else {
            state = State.standing;
        }
    }

}
