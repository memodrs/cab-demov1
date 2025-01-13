package com.cab.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.model.CardState;
import com.cab.configs.Positions;

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

        iconArtSize = Positions.tileSize1Point4;

		paperStatsX = x + Positions.tileSize1Point8;
		paperStatsY = y + Positions.tileSize7Point8;
		paperStatsWidth = Positions.tileSize4Point6;
		paperStatsHeight = Positions.tileSize3Point2;

		iconHeartX = x + Positions.tileSize3;
		iconHeartY = y + Positions.tileSize9;
		iconHeartSize = Positions.tileSize0Point75;
		stringLifeX = x + Positions.tileSize3Point2;
		stringLifeY = y + Positions.tileSize10Point5;
		iconAtkX = x + Positions.tileSize4;
		iconAtkY = y + Positions.tileSize9;
		iconAtkSize = Positions.tileSize0Point75;
		atkStringX = x + Positions.tileSize4Point15;
		atkStringY = y + Positions.tileSize10Point5;
		kostenStringHeaderX = x + Positions.tileSize3Point15;
		kostenStringHeaderY = y + Positions.tileSize9Point3;
		kostenStringX = x + Positions.tileSize3Point5;
		kostenStringY = y + Positions.tileSize10Point2;
		iconArtX = x + Positions.tileSize4Point5;
		iconArtY = y + Positions.tileSize9Point4;
		

		nameStringX = x;
		nameStringY = y - Positions.tileSize0Point25;

		paperEffektX = x - Positions.tileSize0Point6;
		paperEffektY = y + Positions.tileSize10Point7;
		paperEffektWidth = Positions.tileSize7Point5;
		paperEffektHeight = Positions.tileSize10;
		
		headerEffektStringX = x + Positions.tileSize2;
		headerEffektStringY = y + Positions.tileSize11Point8;
		xEffektBeschreibung = x + Positions.tileSize0Point5;
		yEffektBeschreibung = y + Positions.tileSize13;
		
		statusPaperSize = (int) (iconArtSize * 0.9);
		statusPaperX = x + Positions.tileSize4Point85;
		statusPaperY = y + Positions.tileSize8Point3;
		
		statusIconX = x + Positions.tileSize5Point15;
		statusIconY = y + Positions.tileSize8Point6;
		statusIconSize = Positions.tileSize0Point65;
		
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
        	g2.drawImage(image, x, y, Positions.cardWidth * 3, Positions.cardHeight * 3, null); 
			g2.drawImage(gp.imageLoader.paperStats, paperStatsX, paperStatsY, paperStatsWidth, paperStatsHeight, null); 
			
			if (status != Status.Default) {
				g2.drawImage(gp.imageLoader.paper05, statusPaperX, statusPaperY, statusPaperSize, statusPaperSize, null); 
				g2.drawImage(gp.imageLoader.getStatusImage(status , false), statusIconX, statusIconY, statusIconSize, statusIconSize, null);
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
    				yEffekt += Positions.tileSize0Point7;
    			}
			}

			g2.setColor(Color.YELLOW);
			g2.setFont(Main.v.brushedFont30);
			g2.drawString(name, nameStringX, nameStringY);
    }
}
