package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

  @Override
  public void create() {
    this.setScreen(new GameScreen());
  }

}
