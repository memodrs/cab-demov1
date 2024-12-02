package com.cab.states;

import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;
import com.cab.configs.Sprache;

public class Optionen {
    GamePanel gp;
    int selectedIdx;
    int selectedOptionIdx;

    public Optionen(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        selectedIdx = 0;
        selectedOptionIdx = 0;
        gp.gameState = gp.optionState;
    }


    public void update() {

    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
        g2.setFont(Main.v.brushedFont25);
        g2.setColor(gp.getColorSelection(0, selectedIdx));
        g2.drawString(gp.t("sprache"), Positions.tileSize4, Positions.tileSize4);

        int idx  = 0;
        for (Sprache sprache : Sprache.values()) {
            g2.drawImage(gp.imageLoader.getFlagForLand(sprache), Positions.tileSize8 + idx * Positions.tileSize3, Positions.tileSize2, Positions.tileSize2, Positions.tileSize3, null);

            if (gp.selectedLanguage == sprache) {
                g2.drawImage(gp.imageLoader.boosterHover, Positions.tileSize7Point5 + idx * Positions.tileSize3, Positions.tileSize2, Positions.tileSize3, Positions.tileSize3, null);
            }

            idx++;
        }
    }
    
}
