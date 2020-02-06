package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author Christopher J. Weeks
 */
public class GameScreen implements Screen {

  OrthographicCamera camera;
  Viewport viewport;
  SpriteBatch batch;
  Texture texture;
  float x = 0;
  float y = 0;
  TiledMap map;
  OrthogonalTiledMapRenderer renderer;

  @Override
  public void show() {
    camera = new OrthographicCamera();
    viewport = new FitViewport(300, 300, camera);
    viewport.apply(true);
    batch = new SpriteBatch();
    texture = new Texture("badlogic.jpg");
    map = new TmxMapLoader().load("World-1-1.tmx");
    renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
  }

  @Override
  public void render(float delta) {
    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
      Gdx.app.exit();
    }

    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
        x += 2;
      } else {
        x += 1;
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
        x -= 2;
      } else {
        x -= 1;
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
        y += 2;
      } else {
        y += 1;
      }
    }

    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
        y -= 2;
      } else {
        y -= 1;
      }
    }

    // clear the screen
    Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    renderer.setView(camera);
    renderer.render();

    batch.setProjectionMatrix(viewport.getCamera().combined);

    batch.begin();
    batch.draw(texture, x, y, 50, 50);
    batch.end();
  }

  @Override
  public void resize(int i, int i1) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void dispose() {
  }

}
