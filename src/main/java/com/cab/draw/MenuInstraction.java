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
        g2.drawImage(gp.imageLoader.instractionKeyboardMenu, gp.p(6), gp.p(4.5), gp.p(6), gp.p(4), null);
		g2.setFont(Main.v.brushedFont15);
		g2.drawString(gp.t("navigieren"), gp.p(3), gp.p(3.5));
		g2.drawString(gp.t("auswaehlen"),  gp.p(3), gp.p(2.5));
        g2.drawString(gp.t("abbrechen"),  gp.p(3), gp.p(1.5));
    }
}
