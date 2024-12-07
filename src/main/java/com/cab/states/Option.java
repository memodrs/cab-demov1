package com.cab.states;

import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;
import com.cab.configs.Sprache;

public class Option {
    GamePanel gp;
    int selectedIdx;
    int selectedOptionIdx;

    public Option(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        selectedIdx = 0;
        selectedOptionIdx = 0;
        gp.gameState = gp.optionState;
    }


    public void update() {
        if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
            if (!gp.keyH.blockBtn) {
                gp.keyH.blockBtn = true;
                if (gp.keyH.fPressed) {
                    if (selectedIdx == 0) {
                        if (selectedOptionIdx == 0) {
                            gp.selectedLanguage = Sprache.Deutsch;
                        } else if (selectedOptionIdx == 1) {
                            gp.selectedLanguage = Sprache.Englisch;
                        }
                    }
                } else if (gp.keyH.upPressed) {
                    if (selectedIdx > 0) {
                        selectedIdx--;
                    }

                } else if (gp.keyH.downPressed) {
                    if (selectedIdx < 0) {
                        selectedIdx++;
                    }
                } else if (gp.keyH.leftPressed) {
                    if (selectedOptionIdx > 0) {
                        selectedOptionIdx--;
                    }
                } else if (gp.keyH.rightPressed) {
                    if (selectedIdx == 0) {
                        if (selectedOptionIdx < 1) {
                            selectedOptionIdx++;
                        }
                    }

                } else if (gp.keyH.qPressed) {
                    gp.save();
                    gp.mainMenu.start();
                }
                gp.playSE(1);
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
        g2.setFont(Main.v.brushedFont25);
        g2.setColor(gp.getColorSelection(0, selectedIdx));
        g2.drawString(gp.t("sprache"), Positions.tileSize4, Positions.tileSize4);

        int idx  = 0;
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
    }
}
