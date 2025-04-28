package com.zhaniya.finalproject;

import com.badlogic.gdx.Game;
import com.zhaniya.finalproject.ui.StartScreen;


import com.badlogic.gdx.Game;
import com.zhaniya.finalproject.ui.MenuScreen;

public class Main extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
