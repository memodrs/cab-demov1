package com.cab;

import java.awt.Graphics2D;

public class Language {
    GamePanel gp;
    String[] langs = {"de", "en"};
    String selectedLanguage;
    int selectIdx;

    public Language(GamePanel gp) {
        this.gp = gp;
        selectIdx = 0;
    }
    

    public void update() {
        if (gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.fPressed) {
			if (!gp.blockBtn) {
				gp.blockBtn = true;
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
                    gp.gameState = gp.hauptmenuState; // TODO irgendwann: TitleSeite titleState
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.getFlagForLand(langs[0], selectIdx == 0), gp.tileSize * 12, Main.screenHalfHeight, gp.tileSize * 4, gp.tileSize * 4, null);
        g2.drawImage(gp.imageLoader.getFlagForLand(langs[1], selectIdx == 1), gp.tileSize * 22, Main.screenHalfHeight, gp.tileSize * 4, gp.tileSize * 4, null);
    }
}
