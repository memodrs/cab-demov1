package com.cab.states;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import com.cab.GamePanel;
import com.cab.configs.Colors;

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
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.setFont(gp.font(25));
    
        g2.setColor(new Color(0, 0, 0, 150)); // Schwarzer Hintergrund mit 60% Transparenz
        g2.fillRoundRect(gp.p(3), gp.p(3) - 10, gp.p(10), gp.p(2), 20, 20);
    
        g2.setColor(Colors.getColorSelection(0, selectedIdx));
        g2.drawString(gp.t("sprache"), gp.p(4), gp.p(4));
    
        int idx = 0;
        for (Sprache sprache : Sprache.values()) {
            int x = gp.p(8) + idx * gp.p(3);
            int y = gp.p(2);
            int width = gp.p(2);
            int height = gp.p(3);
    
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
        g2.drawString(gp.t("lautstaerke") + ": ", gp.p(4), gp.p(8));
    
        int barX = gp.p(8);
        int barY = gp.p(7.6);
        int barWidth = gp.p(4);
        int barHeight = gp.p(0.5);
        g2.setColor(Colors.darkBlueColor);
        g2.fillRect(barX, barY, barWidth, barHeight);
        g2.setColor(Colors.orangeYellow);
        g2.fillRect(barX, barY, (int) (barWidth * (gp.soundLevel / 100.0)), barHeight);
    
        g2.setColor(Colors.getColorSelection(1, selectedIdx));
        g2.drawRect(barX, barY, barWidth, barHeight);
    }
    
}

