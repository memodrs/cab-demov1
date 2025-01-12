package com.cab.states;

import java.awt.BasicStroke;
import java.awt.Color;
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
                    if (selectedIdx > 0) {
                        selectedIdx--;
                    }
                } else if (gp.keyH.downPressed) {
                    if (selectedIdx < 1) {  
                        selectedIdx++;
                    }
                } else if (gp.keyH.leftPressed) {
                    if (selectedIdx == 0 && selectedOptionIdx > 0) {
                        selectedOptionIdx--;
                    } else if (selectedIdx == 1) {
                        gp.decreaseSound();
                    }
                } else if (gp.keyH.rightPressed) {
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
    
        g2.setColor(new Color(0, 0, 0, 150)); // Schwarzer Hintergrund mit 60% Transparenz
        g2.fillRoundRect(Positions.tileSize3, Positions.tileSize3 - 10, Positions.tileSize10, Positions.tileSize2, 20, 20);
    
        g2.setColor(Colors.getColorSelection(0, selectedIdx));
        g2.drawString(gp.t("sprache"), Positions.tileSize4, Positions.tileSize4);
    
        int idx = 0;
        for (Sprache sprache : Sprache.values()) {
            int x = Positions.tileSize8 + idx * Positions.tileSize3;
            int y = Positions.tileSize2;
            int width = Positions.tileSize2;
            int height = Positions.tileSize3;
    
            if (selectedIdx == 0 && selectedOptionIdx == idx) {
                g2.setColor(new Color(255, 215, 0, 120)); 
                g2.fillRoundRect(x - 5, y - 5, width + 10, height + 10, 20, 20);
            }
    
            g2.drawImage(gp.imageLoader.getFlagForLand(sprache), x, y, width, height, null);
    
            if (gp.selectedLanguage == sprache) {
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Colors.gold); 
                g2.drawRoundRect(x, y, width, height, 10, 10);
            }
            idx++;
        }
    
        g2.setColor(Colors.getColorSelection(1, selectedIdx));
        g2.drawString(gp.t("lautstaerke") + ": ", Positions.tileSize4, Positions.tileSize8);
    
        int barX = Positions.tileSize8;
        int barY = Positions.tileSize7Point6;
        int barWidth = Positions.tileSize4;
        int barHeight = Positions.tileSize0Point5;
        g2.setColor(Colors.darkBlueColor);
        g2.fillRect(barX, barY, barWidth, barHeight);
        g2.setColor(Colors.orangeYellow);
        g2.fillRect(barX, barY, (int) (barWidth * (gp.soundLevel / 100.0)), barHeight);
    
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(barX, barY, barWidth, barHeight);
    }
    
}

