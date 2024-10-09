package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;

public class Language {
    GamePanel gp;
    String[] headers = {"Wähle eine Sprache"};
    String[] langs = {"de"};
    int[] xPositions = {Positions.tileSize5};
    String selectedLanguage;
    int selectIdx;

    public Language(GamePanel gp) {
        this.gp = gp;
        selectIdx = 0;
    }
    

    public void update() {
        if (gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.fPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
                if (gp.keyH.leftPressed) {
                    if (selectIdx > 0) {
                        selectIdx--;
                    }
                } else if (gp.keyH.rightPressed) {
                    if (selectIdx < langs.length - 1) {
                        selectIdx++;
                    }
                } else if (gp.keyH.fPressed) {
                    selectedLanguage = langs[selectIdx];
                    gp.gameState = gp.hauptmenuState;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);

        g2.setColor(Color.RED);
        g2.setFont(Main.v.brushedFont36);

        for (int i = 0; i < headers.length; i++) {
            g2.drawString(headers[i], Positions.tileSize, Positions.tileSizeBottom2Point5);
            g2.drawImage(gp.imageLoader.getFlagForLand(langs[i], selectIdx == i), xPositions[i], Positions.screenHalfHeight, Positions.tileSize4, Positions.tileSize3Point6, null);
        }
    }
}
