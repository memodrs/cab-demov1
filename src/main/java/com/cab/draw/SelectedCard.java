package com.cab.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import com.cab.GamePanel;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.model.CardState;


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

        iconArtSize = gp.p(1.4);

		paperStatsX = x - gp.p(4.5);
		paperStatsY = y + gp.p(1);
		paperStatsWidth = gp.p(4.6);
		paperStatsHeight = gp.p(3.2);

		iconHeartX = x - gp.p(3.6);
		iconHeartY = y + gp.p(1.5);
		iconHeartSize = gp.p(0.75);
		stringLifeX = x - gp.p(3.45);
		stringLifeY = y + gp.p(3);
		iconAtkX = x - gp.p(2.5);
		iconAtkY = y + gp.p(1.5);
		iconAtkSize = gp.p(0.75);
		atkStringX = x - gp.p(2.35);
		atkStringY = y + gp.p(3);
		
		kostenStringHeaderX = x - gp.p(3.15);
		kostenStringHeaderY = y + gp.p(1.5);
		kostenStringX = x - gp.p(3);
		kostenStringY = y + gp.p(3);
		
		iconArtX = x - gp.p(1.8);
		iconArtY = y + gp.p(1.6);
		
		nameStringX = x - gp.p(6);
		nameStringY = y + gp.p(0.7);

		paperEffektX = x - gp.p(0.6);
		paperEffektY = y + gp.p(8.6);
		paperEffektWidth = gp.p(7.5);
		paperEffektHeight = gp.p(9);
		
		headerEffektStringX = x + gp.p(2);
		headerEffektStringY = y + gp.p(9.5);
		xEffektBeschreibung = x + gp.p(0.5);
		yEffektBeschreibung = y + gp.p(10.3);
		
		statusPaperSize = (int) (iconArtSize * 0.9);
		statusPaperX = x + gp.p(4.7);
		statusPaperY = y + gp.p(7.5);
		
		statusIconX = x + gp.p(5.1);
		statusIconY = y + gp.p(7.8);
		statusIconSize = gp.p(0.65);
		
    }

	public void drawCardState(Graphics2D g2, CardState card) {
		if (card != null) {
			draw(g2, card.defaultCard.getImage(), card.defaultCard.getStatus(), card.defaultCard.isSpell(), card.life, card.atk, card.defaultCard.getKosten(), card.art, card.defaultCard.getBeschreibung(), card.defaultCard.getName());
		}
	}

	public void drawCard(Graphics2D g2, Card card) {
		if (card != null) {
			draw(g2, card.getImage(), card.getStatus(), card.isSpell(), card.getLife(), card.getAtk(), card.getKosten(), card.getArt(), card.getBeschreibung(), card.getName());
		}
	}

    private void draw(Graphics2D g2, Image image, Status status, boolean isSpell, int def, int atk, int kosten, Art art, String beschreibung, String name) {
			g2.drawImage(gp.imageLoader.paper09, paperEffektX, paperEffektY, paperEffektWidth, paperEffektHeight, null); 
			gp.drawLib.drawHover(g2, nameStringX - gp.p(0.5), nameStringY - gp.p(0.6), gp.p(7), gp.p(1), true);
			g2.drawImage(image, x, y, gp.p(1.9) * 3, gp.p(2.9) * 3, null); 
			g2.drawImage(gp.imageLoader.paperStats, paperStatsX, paperStatsY, paperStatsWidth, paperStatsHeight, null); 
			

			if (status != Status.Default) {
				g2.drawImage(gp.imageLoader.paper05, statusPaperX, statusPaperY, statusPaperSize, statusPaperSize, null); 
				g2.drawImage(gp.imageLoader.getStatusImage(status , false), statusIconX, statusIconY, statusIconSize, statusIconSize, null);
			}
			
			g2.setFont(gp.font(36));
			g2.setColor(Color.BLACK); 
			if (!isSpell) {
				g2.setFont(gp.font(36));
				g2.drawImage(gp.imageLoader.iconHeart, iconHeartX, iconHeartY, iconHeartSize, iconHeartSize, null); 
				g2.drawString(def + "", stringLifeX, stringLifeY);
				g2.drawImage(gp.imageLoader.iconAtk, iconAtkX, iconAtkY, iconAtkSize, iconAtkSize, null); 
				g2.drawString(atk + "", atkStringX, atkStringY);
			} else {
				g2.setFont(gp.font(25));
				g2.drawImage(gp.imageLoader.getKostenIcon(art), kostenStringHeaderX, kostenStringHeaderY, gp.p(0.75), gp.p(0.75), null);
				g2.setFont(gp.font(36));
				g2.drawString(kosten + "", kostenStringX, kostenStringY);
			}

			g2.drawImage(gp.imageLoader.getArtIconForArt(art, false), iconArtX, iconArtY, iconArtSize, iconArtSize, null); 


			if (beschreibung.length() > 0) {
				g2.setFont(gp.font(20));
				g2.setColor(Color.BLACK); 

				int yEffekt = yEffektBeschreibung;

				g2.setColor(Color.black);
				g2.drawString("EFFEKT", headerEffektStringX, headerEffektStringY);

				
				for (String line : beschreibung.split("\n")) {
					g2.drawString(line, xEffektBeschreibung, yEffekt);
    				yEffekt += gp.p(0.7);
    			}
			}

			g2.setColor(Color.YELLOW);
			g2.setFont(gp.font(30));
			g2.drawString(name, nameStringX, nameStringY);
    }
}
