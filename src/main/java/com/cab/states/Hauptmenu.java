package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;

public class Hauptmenu {
	GamePanel gp;
	public int selectedIdx;

	int midScreenX;
	int arrowIconX;
	String[] menuItems = new String[8];
	
	public boolean getBonus = false;

	public int currentState = 0;
	
	public int titleState = 0;
	public int winState = 1;
	public int looseState = 2;

	public Hauptmenu(GamePanel gp) {
		this.gp = gp;
		
		midScreenX =  Main.screenWidth / 2 - gp.tileSize * 3;
		arrowIconX = midScreenX - gp.tileSize * 2;

		menuItems[0] = "deckBearbeiten";
		menuItems[1] = "singlePlayer";
		menuItems[2] = "serverErstellen";
		menuItems[3] = "serverBeitreten";
		menuItems[4] = "shop";
		menuItems[5] = "lexikon";
		menuItems[6] = "optionen";
		menuItems[7] = "beenden";
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
							gp.cardMenu.showStapelEditor();
						} else if (selectedIdx == 1) {
							
							List<Integer> stapelOponent = new ArrayList<>();
							for (int i = 0; i < 21; i++) {
								stapelOponent.add(i);
							}
							gp.cardGame.createGame(stapelOponent, true, false);
							gp.gameState = gp.cardGameState; 
							 

						} else if (selectedIdx == 2) {
							gp.createServer.start();
						} else if (selectedIdx == 3) {
							gp.joinServer.start();
						} else if (selectedIdx == 4) {
							gp.shop.start();
						} else if (selectedIdx == 5) {
							gp.lexikon.start();
						} else if (selectedIdx == 7) {
							System.exit(0);
						}
					} else if (currentState == winState || currentState == looseState) {
						getBonus = false;
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
				int offsetY = Positions.tileSize14 + (Positions.tileSize * i);
				g2.setColor(Color.WHITE);
				if (selectedIdx == i) {
					g2.drawImage(gp.imageLoader.navigationArrowRight, Positions.tileSize0Point5, (int) (offsetY - Positions.tileSize1Point15), Positions.tileSize2, Positions.tileSize2, null);
					g2.setColor(Color.red);
				}
				g2.drawString(gp.t(menuItems[i]), Positions.tileSize2Point5, offsetY);
			}
		} else if (currentState == winState) {
			g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
			g2.setColor(Color.YELLOW);
            g2.drawString(gp.t("belohnungSieg"), Positions.tileSize, Positions.tileSize20);
			if (getBonus) {
				g2.drawString(gp.t("bonus"), Positions.tileSize, Positions.tileSize21);
			}
			g2.setColor(Color.ORANGE);
			g2.drawString(gp.t("punkteStand"), Positions.tileSize21, Positions.tileSize21);
			g2.setFont(Main.v.brushedFont36);
			g2.drawString("" + gp.player.punkte, Positions.tileSize28, Positions.tileSize21);
			g2.drawString(gp.t("gewonnen"), Positions.tileSize, Positions.tileSizeBottom3Point5);
		} else if (currentState == looseState) {
			g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
			g2.setColor(Color.YELLOW);
            g2.drawString(gp.t("belohnungNiedrerlage"), Positions.tileSize, Positions.tileSize21);
			g2.setColor(Color.ORANGE);
			g2.drawString(gp.t("punkteStand"), Positions.tileSize21, Positions.tileSize21);
			g2.setFont(Main.v.brushedFont36);
			g2.drawString("" + gp.player.punkte, Positions.tileSize28, Positions.tileSize21);
			g2.setColor(Color.RED);
			g2.drawString(gp.t("verloren"), Positions.tileSize, Positions.tileSizeBottom3Point5);
		}
	}
}
