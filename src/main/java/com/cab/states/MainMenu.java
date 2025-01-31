package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;



public class MainMenu extends GameState {
	GamePanel gp;
	public int selectedIdx = 0;
	String[] menuItems = new String[7];
	
	public int currentState = 0;
	
	public int titleState = 0;
	public int winState = 1;
	public int looseState = 2;

	public MainMenu(GamePanel gp) {
		this.gp = gp;
		
		menuItems[0] = "deckBearbeiten";
		menuItems[1] = "serverErstellen";
		menuItems[2] = "serverBeitreten";
		menuItems[3] = "shop";
		menuItems[4] = "lexikon";
		menuItems[5] = "optionen";
		menuItems[6] = "beenden";
	}

	public void start() {
		currentState = titleState;
		gp.switchState(gp.mainMenuState);
		gp.playMusic(0);
	}

	private void switchState(int state) {
		selectedIdx = 0;
		currentState = state;
	}

	@Override
	public void update() {
		if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
				if (gp.keyH.fPressed) {
					if (currentState == titleState) {
						if (selectedIdx == 0) {
							gp.cardMenu.start();
						} else if (selectedIdx == 1) {
							gp.createServer.start();
						} else if (selectedIdx == 2) {
							gp.joinServer.start();
						} else if (selectedIdx == 3) {
							gp.shop.start();
						} else if (selectedIdx == 4) {
							gp.lexikon.start();
						} else if (selectedIdx == 5) {
							gp.optionen.start();
						} else if (selectedIdx == 6) {
							System.exit(0);
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
				gp.playSE(1);
			}
		} 
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(gp.imageLoader.animHauptmenuBG.get(), 0, 0, gp.screenWidth, gp.screenHeight, null);
		g2.setFont(gp.font(25));
		if (currentState == titleState) {
			for (int i = 0; i < menuItems.length; i++) {
				int offsetY = gp.p(14) + (gp.p(1) * i);
				g2.setColor(Color.WHITE);
				if (selectedIdx == i) {
					g2.drawImage(gp.imageLoader.navigationArrowRight, gp.p(0.5), (int) (offsetY - gp.p(1.15)), gp.p(2), gp.p(2), null);
					g2.setColor(Color.red);
				}
				g2.drawString(gp.t(menuItems[i]), gp.p(2.5), offsetY);
			}
		} else if (currentState == winState) {
			g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
			g2.setColor(Color.YELLOW);
            g2.drawString(gp.t("belohnungSieg"), gp.p(1), gp.p(20));
			g2.setColor(Color.ORANGE);
			g2.drawString(gp.t("punkteStand"), gp.p(21), gp.p(21));
			g2.setFont(gp.font(36));
			g2.drawString("" + gp.player.punkte, gp.p(28), gp.p(21));
			g2.drawString(gp.t("gewonnen"), gp.p(1), gp.p(3.5));
		} else if (currentState == looseState) {
			g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
			g2.setColor(Color.YELLOW);
            g2.drawString(gp.t("belohnungNiedrerlage"), gp.p(1), gp.p(21));
			g2.setColor(Color.ORANGE);
			g2.drawString(gp.t("punkteStand"), gp.p(21), gp.p(21));
			g2.setFont(gp.font(36));
			g2.drawString("" + gp.player.punkte, gp.p(28), gp.p(21));
			g2.setColor(Color.RED);
			g2.drawString(gp.t("verloren"), gp.p(1), gp.p(3.5));
		}
	}
}
