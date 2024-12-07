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
        g2.drawImage(gp.imageLoader.instractionKeyboardMenu, Positions.tileSizeRight6, Positions.tileSizeBottom4Point5, Positions.tileSize6, Positions.tileSize4, null);
		g2.setFont(Main.v.brushedFont15);
		g2.drawString(gp.t("navigieren"), Positions.tileSizeRight3, Positions.tileSizeBottom3Point5);
		g2.drawString(gp.t("auswaehlen"),  Positions.tileSizeRight3, Positions.tileSizeBottom2Point5);
        g2.drawString(gp.t("abbrechen"),  Positions.tileSizeRight3, Positions.tileSizeBottom1Point5);
    }
}
