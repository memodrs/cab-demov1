package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;

public class Hauptmenu {
	GamePanel gp;
	public int selectedIdx;

	int midScreenX;
	int arrowIconX;
	String[] menuItems = new String[6];
	
	public int currentState = 0;
	
	public int titleState = 0;
	public int winState = 1;
	public int looseState = 2;

	public Hauptmenu(GamePanel gp) {
		this.gp = gp;
		
		midScreenX =  Main.screenWidth / 2 - gp.tileSize * 3;
		arrowIconX = midScreenX - gp.tileSize * 2;

		menuItems[0] = "Deck bearbeiten";
		menuItems[1] = "Server erstellen";
		menuItems[2] = "Server beitreten";
		menuItems[3] = "Shop";
		menuItems[4] = "Regeln (in Arbeit)";
		menuItems[5] = "Optionen (in Arbeit)";
	}

	public void start() {
		selectedIdx = 0;
		currentState = titleState;
		gp.gameState = gp.hauptmenuState;
	}

	private void switchState(int state) {
		selectedIdx = 0;
		currentState = state;
	}

	public void update() {
		if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
				if (gp.keyH.fPressed) {
					if (currentState == titleState) {
						if (selectedIdx == 0) {
							gp.cardMenu.showStapelEditor(false);
						} else if (selectedIdx == 1) {
							gp.createServer.start();
						} else if (selectedIdx == 2) {
							gp.joinServer.start();
						} else if (selectedIdx == 3) {
							gp.shop.start();
						}
					} else if (currentState == winState || currentState == looseState) {
						switchState(titleState);
					}
				} else if (gp.keyH.upPressed) {
					if (currentState == titleState) {
						if (selectedIdx  > 0) {
							selectedIdx--;
						}
					}
				} else if (gp.keyH.downPressed) {
					if (currentState == titleState) {
						if (selectedIdx < menuItems.length - 1) {
							selectedIdx++;
						}
					}
				} else if (gp.keyH.qPressed) {
					if (currentState == winState || currentState == looseState) {
						switchState(titleState);
					}
				}
				gp.playSE(4);
			}
		} 
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(gp.imageLoader.animHauptmenuBG.get(), 0, 0, Main.screenWidth, Main.screenHeight, null);
		
		g2.setFont(Main.v.brushedFont25);
		if (currentState == titleState) {
			for (int i = 0; i < menuItems.length; i++) {
				int offsetY = gp.tileSize * 2 + (gp.tileSize * i);
				g2.setColor(Color.WHITE);
				if (selectedIdx == i) {
					g2.drawImage(gp.imageLoader.iconArrowRight, arrowIconX, (int) (offsetY - gp.tileSize * 1.1), gp.tileSize * 2, gp.tileSize * 2, null);
					g2.setColor(Color.red);
				}
				g2.drawString(menuItems[i], midScreenX, offsetY);
			}
		} else if (currentState == winState) {
			g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
			g2.setColor(Color.YELLOW);
            g2.drawString("Belohnung: 20 Punkte", Positions.tileSize, Positions.tileSize21);
			g2.setColor(Color.ORANGE);
			g2.drawString("Neuer Punktestand:", Positions.tileSize21, Positions.tileSize21);
			g2.setFont(Main.v.brushedFont36);
			g2.drawString("" + gp.player.punkte, Positions.tileSize28, Positions.tileSize21);
			g2.drawString("Du hast Gewonnen", Positions.tileSize, Positions.tileSizeBottom3Point5);
		} else if (currentState == looseState) {
			g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
			g2.setColor(Color.YELLOW);
            g2.drawString("Belohnung: 5 Punkte", Positions.tileSize, Positions.tileSize21);
			g2.setColor(Color.ORANGE);
			g2.drawString("Neuer Punktestand:", Positions.tileSize21, Positions.tileSize21);
			g2.setFont(Main.v.brushedFont36);
			g2.drawString("" + gp.player.punkte, Positions.tileSize28, Positions.tileSize21);
			g2.setColor(Color.RED);
			g2.drawString("Du hast Verloren", Positions.tileSize, Positions.tileSizeBottom3Point5);
		}
	}
}
