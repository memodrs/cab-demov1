package com.cab.states;

import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;

public class Loading extends GameState {
    GamePanel gp;

    public Loading(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.loadingScreenBg, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
        g2.drawImage(gp.imageLoader.loadingScreen.get(), 0, 0, Main.screenWidth, Main.screenHeight, null);
    }
}
