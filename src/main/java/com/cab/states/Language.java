package com.cab.states;

import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.configs.Colors;
import com.cab.configs.Sprache;

public class Language extends GameState {
    GamePanel gp;
    int selectIdx;

    Sprache[] langs = new Sprache[Sprache.values().length];
    String[] headers = new String[Sprache.values().length];
    int[] xPositions = new int[Sprache.values().length];

    public Language(GamePanel gp) {
        this.gp = gp;

        int idx = 0;
        for (Sprache sprache : Sprache.values()) {
            langs[idx] = sprache;
            idx++;
        }

        xPositions[0] = gp.p(4); 
        xPositions[1] = gp.p(9); 

        headers[0] = "Wähle eine Sprache"; 
        headers[1] = "Select a language"; 
    }

    public void start() {
        selectIdx = 0;
        gp.selectedLanguage = langs[selectIdx];
        gp.switchState(gp.languageState);
        gp.playMusic(0); 
    }
    

    @Override
    public void update() {
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
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.setColor(Colors.gold);
        g2.setFont(gp.font(39));

        for (int i = 0; i < headers.length; i++) {
            if (i == selectIdx) {
                g2.drawString(headers[i], gp.p(1), gp.p(20));
                g2.drawImage(gp.imageLoader.getFlagForLand(langs[i]), xPositions[i], gp.p(7), gp.p(4), gp.p(6), null);
                g2.drawImage(gp.imageLoader.boosterHover, xPositions[i], gp.p(7), gp.p(4.4), gp.p(6), null);
            } else {
                g2.drawImage(gp.imageLoader.getFlagForLand(langs[i]), xPositions[i], gp.p(10), gp.p(4), gp.p(6), null);
            }
        }
    }
}
