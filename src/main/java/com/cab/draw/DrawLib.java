package com.cab.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.RoundRectangle2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
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
        int yBerechnet = isSelected ? y - gp.p(0.1) : y;
        g2.drawImage(card.getImage(), x, yBerechnet, width, height, null); 

        if (isSelected) {
            int arcWidth = 20;  // Rundung horizontal
            int arcHeight = 20; // Rundung vertikal
            RoundRectangle2D roundedRect = new RoundRectangle2D.Float(x, yBerechnet, width, height, arcWidth, arcHeight);
            g2.setClip(roundedRect);
            g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, yBerechnet, width, height, null);
            g2.setClip(null);
        }
        if (isHolo) {
            g2.drawImage(card.getHoloEffekt().get(), x, yBerechnet, width, height, null); 
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

    public void drawStringCenter(Graphics2D g2, String msg, int y) {
            FontMetrics fm = g2.getFontMetrics();
			int textWidth = fm.stringWidth(gp.t(msg)); 
			int x = gp.p(Main.v.halfWidthTile) - (textWidth / 2);
            g2.drawString(gp.t(msg), x, y);
    }

    public void drawNavigationLeftArrow(Graphics2D g2, int x, int y, boolean isDisabled) {
        Image leftArrow = isDisabled? gp.imageLoader.navigationArrowLeftDisabled : gp.imageLoader.navigationArrowLeft;
        g2.drawImage(leftArrow, x, y, gp.p(2), gp.p(2), null);
    }

    public void drawNavigationRightArrow(Graphics2D g2, int x, int y, boolean isDisabled) {
        Image leftArrow = isDisabled? gp.imageLoader.navigationArrowRightDisabled : gp.imageLoader.navigationArrowRight;
        g2.drawImage(leftArrow, x, y, gp.p(2), gp.p(2), null);
    }

    public void drawStringWithNewLines(Graphics2D g2, String str, int x, int y) {
        int yBerechnet = y;
        for (String line : str.split("\n")) {
            g2.drawString(line, x, yBerechnet);
            yBerechnet += gp.p(0.7);
        }
    }

    public void drawMenuInstraction(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawImage(gp.imageLoader.instractionKeyboardGeneric, gp.p(33.5), gp.p(18.2), gp.p(6), gp.p(4), null);
		g2.setFont(gp.font(20));
		g2.drawString(gp.t("navigieren"), gp.p(36), gp.p(19.36));
		g2.drawString(gp.t("auswaehlen"),  gp.p(36), gp.p(20.5));
        g2.drawString(gp.t("abbrechen"),  gp.p(36), gp.p(21.3));
    }

    public void drawCardStats(Graphics2D g2, int x, int y, int life, int atk, int kosten, Art art, boolean isSpell, Status status) {
        g2.drawImage(gp.imageLoader.paperStats, x, y, gp.p(4.6),  gp.p(3.2), null); 
        g2.setColor(Color.BLACK); 
        if (isSpell) {
            g2.setFont(gp.font(25));
            g2.drawImage(gp.imageLoader.getKostenIcon(art), x, y, gp.p(0.75), gp.p(0.75), null);
            g2.setFont(gp.font(36));
            g2.drawString(kosten + "", x, y);
        } else {
            g2.setFont(gp.font(36));
            g2.drawImage(gp.imageLoader.iconHeart, x + gp.p(0.7), y + gp.p(0.5), gp.p(1), gp.p(1), null); 
            g2.drawString(life + "", x + gp.p(1), y + gp.p(2.3));
            g2.drawImage(gp.imageLoader.iconAtk, x + gp.p(1.8), y + gp.p(0.5), gp.p(1), gp.p(1), null); 
            g2.drawString(atk + "", x + gp.p(2), y + gp.p(2.3));

            if (status != Status.Default) {
                g2.drawImage(gp.imageLoader.paper05, x + gp.p(2.8), y, gp.p(1.4), gp.p(1), null); 
                g2.drawImage(gp.imageLoader.getStatusImage(status , false), x + gp.p(3.28), y + gp.p(0.24), gp.p(0.5), gp.p(0.5), null);
            }
        }
        g2.drawImage(gp.imageLoader.getArtIconForArt(art, false), x + gp.p(2.7),  y + gp.p(0.8), gp.p(1.4), gp.p(1.4), null); 
    }

    public void drawEffektBeschreibung(Graphics2D g2, int x, int y, String beschreibung) {
        g2.drawImage(gp.imageLoader.paper09, x, y, gp.p(7.5), gp.p(9), null); 

        g2.setFont(gp.font(36));
        g2.setColor(Color.BLACK); 

        if (beschreibung.length() > 0) {
            g2.setFont(gp.font(20));
            g2.setColor(Color.BLACK); 

            int yEffekt = y + gp.p(1.8);

            g2.setColor(Color.black);
            g2.drawString("EFFEKT", x + gp.p(2.5), y + gp.p(1));

            
            for (String line : beschreibung.split("\n")) {
                g2.drawString(line, x + gp.p(1), yEffekt);
                yEffekt += gp.p(0.7);
            }
        }
    }
}
