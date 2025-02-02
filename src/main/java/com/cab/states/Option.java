package com.cab.states;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.cab.GamePanel;
import com.cab.configs.Colors;

import com.cab.configs.Sprache;

public class Option extends GameState {
    GamePanel gp;
    int selectedIdx;
    int selectedOptionIdx;
    List<String> items = new ArrayList<>();

    public Option(GamePanel gp) {
        this.gp = gp;

        items.add("sprache");
        items.add("lautstaerke");
    }

    public void start() {
        selectedIdx = 0;
        selectedOptionIdx = 0;
        gp.switchState(gp.optionState);
    }

    @Override
    public void update() {
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

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);

        for (int i = 0; i < items.size(); i++) {
            int y = gp.p(4) * i + gp.p(4);
            gp.drawLib.drawArrowOnState(g2, gp.p(1), y - gp.p(1), true, selectedIdx == i);
            gp.drawLib.drawHover(g2, gp.p(2.9), y - gp.p(0.6), gp.p(4), gp.p(1), selectedIdx == i);
            g2.setColor(Colors.getColorSelection(i, selectedIdx));
            g2.setFont(gp.fontSelection(30, 35, selectedIdx == i));
            g2.drawString(gp.t(items.get(i)), gp.p(3), y);
        }
    
        //SPRACHE
        int idx = 0;
        for (Sprache sprache : Sprache.values()) {
            int x = gp.p(8) + idx * gp.p(3);
            x += selectedIdx == 0? 5 : 0;
            int y = gp.p(2);
            int width = gp.p(2);
            int height = gp.p(3);
    
            if (selectedIdx == 0 && selectedOptionIdx == idx) {
                g2.setColor(Color.YELLOW); 
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
    
        //LAUTSTAERKE
        int barX = gp.p(8);
        barX += selectedIdx == 1? 5 : 0;
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

