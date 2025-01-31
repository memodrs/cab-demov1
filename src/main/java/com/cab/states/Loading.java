package com.cab.states;

import java.awt.Graphics2D;

import com.cab.GamePanel;

public class Loading extends GameState {
    GamePanel gp;

    public Loading(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.loadingScreenBg, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.drawImage(gp.imageLoader.loadingScreen.get(), 0, 0, gp.screenWidth, gp.screenHeight, null);
    }
}
