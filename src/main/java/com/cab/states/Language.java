package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;

import com.cab.configs.Sprache;

public class Language extends GameState {
    GamePanel gp;
    String[] headers = {"Wähle eine Sprache", "Select a language"};
    Sprache[] langs = new Sprache[Sprache.values().length];
    int[] xPositions = new int[2];
    int selectIdx;

    public Language(GamePanel gp) {
        this.gp = gp;

        int idx = 0;
        for (Sprache sprache : Sprache.values()) {
            langs[idx] = sprache;
            idx++;
        }

        xPositions[0] = gp.p(10); 
        xPositions[1] = gp.p(20); 
    }

    public void start() {
        selectIdx = 0;
        gp.switchState(gp.languageState);
        gp.selectedLanguage = langs[selectIdx];
        gp.playMusic(0); 
    }
    

    @Override
    public void update() {
        if (gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.fPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
                if (gp.keyH.leftPressed) {
                    if (selectIdx > 0) {
                        selectIdx--;
                        gp.selectedLanguage = langs[selectIdx];
                    }
                } else if (gp.keyH.rightPressed) {
                    if (selectIdx < langs.length - 1) {
                        selectIdx++;
                        gp.selectedLanguage = langs[selectIdx];
                    }
                } else if (gp.keyH.fPressed) {
                    gp.selectedLanguage = langs[selectIdx];
                    gp.firstStart.start();
                }
                gp.playSE(1);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);

        g2.setColor(Color.RED);
        g2.setFont(gp.font(36));

        for (int i = 0; i < headers.length; i++) {

            if (i == selectIdx) {
                g2.drawString(headers[i], gp.p(1), gp.p(2.5));

                double angle = (i % 2 == 0) ? Math.toRadians(-5) : Math.toRadians(5); // Neigung abhängig von Parität

                int centerX = xPositions[i] + gp.p(4) / 2;
                int centerY = gp.p(4) / 2 + gp.p(6) / 2;

                g2.rotate(angle, centerX, centerY);

                g2.drawImage(gp.imageLoader.getFlagForLand(langs[i]), xPositions[i], gp.p(5), gp.p(4), gp.p(6), null);

                g2.rotate(-angle, centerX, centerY);

                g2.drawImage(gp.imageLoader.boosterHover, xPositions[i], gp.p(5), gp.p(4.4), gp.p(6), null);

            } else {
                g2.drawImage(gp.imageLoader.getFlagForLand(langs[i]), xPositions[i], gp.p(6), gp.p(4), gp.p(6), null);
            }
        }
    }
}
