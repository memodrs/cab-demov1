package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;



public class SaveGameCorrupt extends GameState {
    GamePanel gp;


    public SaveGameCorrupt(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        gp.switchState(gp.savegameCorruptState);
    }
    

    @Override
    public void update() {
        if (gp.keyH.fPressed || gp.keyH.qPressed) {
			System.exit(0);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.setFont(gp.font(25));
        g2.drawString("Savegame Corrupt", gp.p(1), gp.p(1));
    }
}
