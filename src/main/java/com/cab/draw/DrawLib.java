package com.cab.draw;

import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.card.Card;
import com.cab.configs.Colors;

public class DrawLib {
    GamePanel gp;

    public DrawLib(GamePanel gp) {
        this.gp = gp;
    }

    public void drawArrowOnState(Graphics2D g2, int x, int y, int state, int targetState) {
        if (state == targetState) {
            g2.drawImage(gp.imageLoader.iconArrowMarker, x, y, gp.p(2), gp.p(2), null);
        }
    }

    public void drawCardStandardSize(Graphics2D g2, Card card, int x, int y, boolean isSelected, boolean isHolo) {
        g2.setColor(Colors.transparent);
        int width = isSelected ?  gp.p(1.9) + 10 : gp.p(1.9);
        int height = isSelected ? gp.p(2.9) + 10 : gp.p(2.9);
        g2.drawImage(card.getImage(), x, y, width, height, null); 
        if (isSelected) {
            g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y, width, height, null); 
        }
        if (isHolo) {
            g2.drawImage(card.getHoloEffekt().get(), x, y, width, height, null); 
        }
    }

    public void drawHover(Graphics2D g2, int x, int y, int width, int height, boolean isOn) {
        if (isOn) {
            g2.drawImage(gp.imageLoader.boosterHover, x, y, width, height, null);
        }
    }
}
