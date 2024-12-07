package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;

public class SaveGameCorrupt {
    GamePanel gp;


    public SaveGameCorrupt(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        gp.gameState = gp.savegameCorruptState;
    }
    

    public void update() {
        if (gp.keyH.fPressed || gp.keyH.qPressed) {
			System.exit(0);
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.setFont(Main.v.brushedFont25);
        g2.drawString("Savegame Corrupt", Positions.tileSize, Positions.tileSize);
    }
}
