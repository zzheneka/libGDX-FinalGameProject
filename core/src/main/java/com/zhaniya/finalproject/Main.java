package com.zhaniya.finalproject;

import com.badlogic.gdx.Game;
import com.zhaniya.finalproject.ui.StartScreen;

public class Main extends Game {
    @Override
    public void create() {
        setScreen(new StartScreen(this));
    }
}
