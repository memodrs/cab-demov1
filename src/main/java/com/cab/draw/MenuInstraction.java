package com.cab.draw;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;

public class MenuInstraction {
    GamePanel gp;

    public MenuInstraction(GamePanel gp) {
        this.gp = gp;
    }
    
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawImage(gp.imageLoader.instractionKeyboardMenu, Positions.tileSizeRight7, Positions.tileSizeBottom5, Positions.tileSize6, Positions.tileSize4, null);
		g2.setFont(Main.v.brushedFont15);
		g2.drawString("Navigieren", Positions.tileSizeRight4, Positions.tileSizeBottom4);
		g2.drawString("Auswählen",  Positions.tileSizeRight4, Positions.tileSizeBottom2Point8);
        g2.drawString("Abbrechen",  Positions.tileSizeRight4, Positions.tileSizeBottom2);
    }
}
