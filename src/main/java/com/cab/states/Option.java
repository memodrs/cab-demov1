package com.cab.states;

import java.awt.Graphics2D;
import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Colors;
import com.cab.configs.Positions;
import com.cab.configs.Sprache;

public class Option extends GameState {
    GamePanel gp;
    int selectedIdx;
    int selectedOptionIdx;

    public Option(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        selectedIdx = 0;
        selectedOptionIdx = 0;
        gp.switchState(gp.optionState);
    }

    @Override
    public void update() {
        if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
            if (!gp.keyH.blockBtn) {
                gp.keyH.blockBtn = true;

                if (gp.keyH.fPressed) {
                    // Sprache ändern
                    if (selectedIdx == 0) {
                        if (selectedOptionIdx == 0) {
                            gp.selectedLanguage = Sprache.Deutsch;
                        } else if (selectedOptionIdx == 1) {
                            gp.selectedLanguage = Sprache.Englisch;
                        }
                    }
                } else if (gp.keyH.upPressed) {
                    // Nach oben wechseln
                    if (selectedIdx > 0) {
                        selectedIdx--;
                    }
                } else if (gp.keyH.downPressed) {
                    // Nach unten wechseln
                    if (selectedIdx < 1) {  // Maximal zwei Optionen: Sprache und Lautstärke
                        selectedIdx++;
                    }
                } else if (gp.keyH.leftPressed) {
                    // Option nach links verringern
                    if (selectedIdx == 0 && selectedOptionIdx > 0) {
                        selectedOptionIdx--;
                    } else if (selectedIdx == 1) {
                        gp.decreaseSound();
                    }
                } else if (gp.keyH.rightPressed) {
                    // Option nach rechts erhöhen
                    if (selectedIdx == 0 && selectedOptionIdx < 1) {
                        selectedOptionIdx++;
                    } else if (selectedIdx == 1) {
                        gp.increaseSound();
                    }
                } else if (gp.keyH.qPressed) {
                    gp.save();
                    gp.mainMenu.start();
                }
                gp.playSE(1);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
        g2.setFont(Main.v.brushedFont25);

        // Spracheinstellungen zeichnen
        g2.setColor(Colors.getColorSelection(0, selectedIdx));
        g2.drawString(gp.t("sprache"), Positions.tileSize4, Positions.tileSize4);
        int idx = 0;
        for (Sprache sprache : Sprache.values()) {
            if (selectedIdx == 0 && selectedOptionIdx == idx) {
                g2.drawImage(gp.imageLoader.getFlagForLand(sprache), Positions.tileSize8 + idx * Positions.tileSize3, Positions.tileSize2, Positions.tileSize2Point3, Positions.tileSize3Point3, null);
                g2.drawImage(gp.imageLoader.selectedCardHover.get(), Positions.tileSize8 + idx * Positions.tileSize3, Positions.tileSize2, Positions.tileSize2Point3, Positions.tileSize3Point3, null);
            } else {
                g2.drawImage(gp.imageLoader.getFlagForLand(sprache), Positions.tileSize8 + idx * Positions.tileSize3, Positions.tileSize2, Positions.tileSize2, Positions.tileSize3, null);
            }
            if (gp.selectedLanguage == sprache) {
                g2.drawImage(gp.imageLoader.boosterHover, Positions.tileSize7Point5 + idx * Positions.tileSize3, Positions.tileSize2, Positions.tileSize3, Positions.tileSize3, null);
            }
            idx++;
        }

        // Lautstärkeregelung zeichnen
        g2.setColor(Colors.getColorSelection(1, selectedIdx));
        g2.drawString(gp.t("lautstaerke") + ": " + gp.soundLevel, Positions.tileSize4, Positions.tileSize8);
    }
}

