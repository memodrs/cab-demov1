package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;

public class Language {
    GamePanel gp;
    String[] headers = {"Wähle eine Sprache", "Select a language"};
    String[] langs = {"de", "en"};
    int[] xPositions = {Positions.tileSize10, Positions.tileSize20};
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
                    gp.selectedLanguage = langs[selectIdx];

                    if (gp.saveManager.isSavegameExist()) {
                        gp.load();
                        if (gp.player.stapel.size() < gp.cardMenu.limitMaxStapel) {
                            gp.cardMenu.showStapelEditor();
                        } else {
                            gp.gameState = gp.hauptmenuState;
                        }
                    } else {
                        gp.firstStart.init();
                        gp.gameState = gp.firstState;
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);

        g2.setColor(Color.RED);
        g2.setFont(Main.v.brushedFont36);

        for (int i = 0; i < headers.length; i++) {

            if (i == selectIdx) {
                g2.drawString(headers[i], Positions.tileSize, Positions.tileSizeBottom2Point5);

                double angle = (i % 2 == 0) ? Math.toRadians(-5) : Math.toRadians(5); // Neigung abhängig von Parität

                // Berechne den Mittelpunkt des Bildes (Rotation erfolgt um diesen Punkt)
                int centerX = xPositions[i] + Positions.tileSize4 / 2;
                int centerY = Positions.tileSize4 / 2 + Positions.tileSize6 / 2;

                // Setze den Grafik-Kontext für die Drehung
                g2.rotate(angle, centerX, centerY);

                // Zeichne das Bild (es wird um den Mittelpunkt gedreht)
                g2.drawImage(gp.imageLoader.getFlagForLand(langs[i]), xPositions[i], Positions.tileSize5, Positions.tileSize4, Positions.tileSize6, null);

                // Setze den Grafik-Kontext zurück, um die Rotation zu beenden
                g2.rotate(-angle, centerX, centerY);

                g2.drawImage(gp.imageLoader.boosterHover, xPositions[i], Positions.tileSize5, Positions.tileSize4Point4, Positions.tileSize6, null);

            } else {
                g2.drawImage(gp.imageLoader.getFlagForLand(langs[i]), xPositions[i], Positions.tileSize6, Positions.tileSize4, Positions.tileSize6, null);
            }
        }
    }
}
