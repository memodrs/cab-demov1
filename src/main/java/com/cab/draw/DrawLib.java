package com.cab.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.card.Card;
import com.cab.configs.Colors;

public class DrawLib {
    GamePanel gp;

    public DrawLib(GamePanel gp) {
        this.gp = gp;
    }

    public void drawArrowOnState(Graphics2D g2, int x, int y, boolean isRightstate, boolean isRightIdx) {
        if (isRightstate && isRightIdx) {
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

    public void drawDialog(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(Colors.transparentBlack);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5)); 
        g2.drawRoundRect(x, y, width, height, 25, 25);
    }
}
