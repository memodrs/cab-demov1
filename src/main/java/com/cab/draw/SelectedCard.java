package com.cab.draw;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Card;
import com.cab.card.Status;

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

        //TODO xxx hier gehts weiter
		iconAtkX = (int) (Main.screenWidth * 0.928);
		iconAtkY = (int) (gp.tileSize * 10);
		iconAtkSize = (int) (gp.tileSize * 0.75);
		atkStringX = (int) (Main.screenWidth * 0.932);
		atkStringY = (int) (gp.tileSize * 11.6);
		kostenStringHeaderX = (int) (Main.screenWidth * 0.909);
		kostenStringHeaderY = (int) (gp.tileSize * 10.5);
		kostenStringX = (int) (Main.screenWidth * 0.93);
		kostenStringY = (int) (gp.tileSize * 11.5);
		iconArtX = (int) (Main.screenWidth * 0.948);
		iconArtY = (int) (gp.tileSize * 10.5);
		nameStringX = (int) (Main.screenWidth * 0.83);
		nameStringY = (int) (gp.tileSize * 0.8);

        paperEffektX = (int) (Main.screenWidth * 0.813);
		paperEffektY = (int) (gp.tileSize * 11.8);
		paperEffektWidth = (int) (gp.tileSize * 7.5);
		paperEffektHeight = (int) (gp.tileSize * 10);

		headerEffektStringX = (int) (Main.screenWidth * 0.87);
		headerEffektStringY = (int) (gp.tileSize * 12.8);

        statusPaperX = (int) (Main.screenWidth * 0.952);
		statusPaperY = (int) (gp.tileSize * 9.5);
		statusPaperSize = (int) (iconArtSize * 0.9);
		statusIconX = (int) (Main.screenWidth * 0.96);
		statusIconY = (int) (gp.tileSize * 9.75);
		statusIconSize = (int) (gp.tileSize * 0.65);
    }

    public void draw(Graphics2D g2, Card card) {
        	g2.drawImage(card.image, x, y, gp.cardWidth * 3, gp.cardHeight * 3, null); 
			g2.drawImage(gp.imageLoader.paperStats, paperStatsX, paperStatsY, paperStatsWidth, paperStatsHeight, null); 
			
			if (card.status != Status.Default) {
				g2.drawImage(gp.imageLoader.paper05, statusPaperX, statusPaperY, statusPaperSize, statusPaperSize, null); 
				g2.drawImage(gp.imageLoader.getStatusImage(card.status), statusIconX, statusIconY, statusIconSize, statusIconSize, null);
			}
			
			g2.setFont(Main.v.brushedFont36);
			g2.setColor(Color.BLACK); 
			if (!card.isSpell) {
				g2.setFont(Main.v.brushedFont36);
				g2.drawImage(gp.imageLoader.iconHeart, iconHeartX, iconHeartY, iconHeartSize, iconHeartSize, null); 
				g2.drawString(card.def + "", stringLifeX, stringLifeY);
				g2.drawImage(gp.imageLoader.iconAtk, iconAtkX, iconAtkY, iconAtkSize, iconAtkSize, null); 
				g2.drawString(card.atk + "", atkStringX, atkStringY);
			} else {
				g2.setFont(Main.v.brushedFont25);
				g2.drawString("Kosten", kostenStringHeaderX, kostenStringHeaderY);
				g2.setFont(Main.v.brushedFont36);
				g2.drawString(card.kosten + "", kostenStringX, kostenStringY);
			}

			g2.drawImage(gp.imageLoader.getArtIconForArt(card.art, false), iconArtX, iconArtY, iconArtSize, iconArtSize, null); 


			if (card.beschreibung.length() > 0) {
				g2.setFont(Main.v.brushedFont20);
				g2.setColor(Color.BLACK); 

				int yEffekt = (int) (gp.tileSize * 13.8);

				g2.drawImage(gp.imageLoader.paper09, paperEffektX, paperEffektY, paperEffektWidth, paperEffektHeight, null); 
				g2.drawString("EFFEKT", headerEffektStringX, headerEffektStringY);

				for (String line : card.beschreibung.split("\n")) {
					g2.drawString(line, (int) (gp.getWidth() * 0.84), yEffekt);
    				yEffekt += (int) (gp.tileSize * 0.7) ;
    			}
			}

			g2.setColor(Color.WHITE);
			g2.setFont(Main.v.brushedFont36);
			g2.drawString(card.name, nameStringX, nameStringY);
    }
    
}
