package com.cab.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardState;

public class SelectedCard {
    GamePanel gp;
    int x;
    int y;

    int iconArtSize; 

	int paperStatsX, paperStatsY, paperStatsWidth, paperStatsHeight;
	int iconHeartX, iconHeartY, iconHeartSize;
	int stringLifeX, stringLifeY;
	int iconAtkX, iconAtkY, iconAtkSize;
	int atkStringX, atkStringY;
	int kostenStringHeaderX, kostenStringHeaderY;
	int kostenStringX, kostenStringY;
	int iconArtX, iconArtY;
	int nameStringX, nameStringY;

    int paperEffektX, paperEffektY, paperEffektWidth, paperEffektHeight;
	int headerEffektStringX, headerEffektStringY;
	int xEffektBeschreibung, yEffektBeschreibung;

    int statusPaperX, statusPaperY, statusPaperSize;
	int statusIconX, statusIconY, statusIconSize;


    public SelectedCard(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;

        iconArtSize = (int) (gp.tileSize * 1.4);

		paperStatsX = x + gp.tileSize * 2;
		paperStatsY = y + (int) (gp.tileSize * 7.8);
		paperStatsWidth = (int) (gp.tileSize * 4.6);
		paperStatsHeight = (int) (gp.tileSize * 3.2);

		iconHeartX = x + gp.tileSize * 3;
		iconHeartY = y + (int) (gp.tileSize * 9);
		iconHeartSize = (int) (gp.tileSize * 0.75);
		stringLifeX = x + (int) (gp.tileSize * 3.2);
		stringLifeY = y + (int) (gp.tileSize * 10.5);
		iconAtkX = x + (int) (gp.tileSize * 4);
		iconAtkY = y + (int) (gp.tileSize * 9);
		iconAtkSize = (int) (gp.tileSize * 0.75);
		atkStringX = x + (int) (gp.tileSize * 4.15);
		atkStringY = y + (int) (gp.tileSize * 10.5);
		kostenStringHeaderX = x + (int) (gp.tileSize * 3.15);
		kostenStringHeaderY = y + (int) (gp.tileSize * 9.3);
		kostenStringX = x + (int) (gp.tileSize * 3.5);
		kostenStringY = y + (int) (gp.tileSize * 10.2);
		iconArtX = x + (int) (gp.tileSize * 4.7);
		iconArtY = y + (int) (gp.tileSize * 9.4);

		nameStringX = x;
		nameStringY = y - (int) (gp.tileSize * 0.25);

		paperEffektX = x - (int) (gp.tileSize * 0.6);
		paperEffektY = y + (int) (gp.tileSize * 10.7);
		paperEffektWidth = (int) (gp.tileSize * 7.5);
		paperEffektHeight = (int) (gp.tileSize * 10);

		headerEffektStringX = x + (int) (gp.tileSize * 2);
		headerEffektStringY = y + (int) (gp.tileSize * 11.8);
		xEffektBeschreibung = x + (int) (gp.tileSize * 0.5);
		yEffektBeschreibung = y + (int) (gp.tileSize * 13);

		statusPaperSize = (int) (iconArtSize * 0.9);
        statusPaperX = x + (int) (gp.tileSize * 4.85);
		statusPaperY = y + (int) (gp.tileSize * 8.3);

		statusIconX = x + (int) (gp.tileSize * 5.15);
		statusIconY = y + (int) (gp.tileSize * 8.6);
		statusIconSize = (int) (gp.tileSize * 0.65);
    }

	public void drawCardState(Graphics2D g2, CardState card) {
		if (card != null) {
			draw(g2, card.defaultCard.image, card.defaultCard.status, card.defaultCard.isSpell, card.life, card.atk, card.defaultCard.kosten, card.art, card.defaultCard.beschreibung, card.defaultCard.name);
		}
	}

	public void drawCard(Graphics2D g2, Card card) {
		if (card != null) {
			draw(g2, card.image, card.status, card.isSpell, card.def, card.atk, card.kosten, card.art, card.beschreibung, card.name);
		}
	}

    private void draw(Graphics2D g2, Image image, Status status, boolean isSpell, int def, int atk, int kosten, Art art, String beschreibung, String name) {
        	g2.drawImage(image, x, y, gp.cardWidth * 3, gp.cardHeight * 3, null); 
			g2.drawImage(gp.imageLoader.paperStats, paperStatsX, paperStatsY, paperStatsWidth, paperStatsHeight, null); 
			
			if (status != Status.Default) {
				g2.drawImage(gp.imageLoader.paper05, statusPaperX, statusPaperY, statusPaperSize, statusPaperSize, null); 
				g2.drawImage(gp.imageLoader.getStatusImage(status), statusIconX, statusIconY, statusIconSize, statusIconSize, null);
			}
			
			g2.setFont(Main.v.brushedFont36);
			g2.setColor(Color.BLACK); 
			if (!isSpell) {
				g2.setFont(Main.v.brushedFont36);
				g2.drawImage(gp.imageLoader.iconHeart, iconHeartX, iconHeartY, iconHeartSize, iconHeartSize, null); 
				g2.drawString(def + "", stringLifeX, stringLifeY);
				g2.drawImage(gp.imageLoader.iconAtk, iconAtkX, iconAtkY, iconAtkSize, iconAtkSize, null); 
				g2.drawString(atk + "", atkStringX, atkStringY);
			} else {
				g2.setFont(Main.v.brushedFont25);
				g2.drawString("Kosten", kostenStringHeaderX, kostenStringHeaderY);
				g2.setFont(Main.v.brushedFont36);
				g2.drawString(kosten + "", kostenStringX, kostenStringY);
			}

			g2.drawImage(gp.imageLoader.getArtIconForArt(art, false), iconArtX, iconArtY, iconArtSize, iconArtSize, null); 


			if (beschreibung.length() > 0) {
				g2.setFont(Main.v.brushedFont20);
				g2.setColor(Color.BLACK); 

				int yEffekt = yEffektBeschreibung;

				g2.drawImage(gp.imageLoader.paper09, paperEffektX, paperEffektY, paperEffektWidth, paperEffektHeight, null); 
				g2.setColor(Color.black);
				g2.drawString("EFFEKT", headerEffektStringX, headerEffektStringY);

				for (String line : beschreibung.split("\n")) {
					g2.drawString(line, xEffektBeschreibung, yEffekt);
    				yEffekt += (int) (gp.tileSize * 0.7) ;
    			}
			}

			g2.setColor(Color.WHITE);
			g2.setFont(Main.v.brushedFont36);
			g2.drawString(name, nameStringX, nameStringY);
    }
    
}
