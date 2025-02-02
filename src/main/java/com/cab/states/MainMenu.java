package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

import com.cab.GamePanel;
import com.cab.configs.Colors;



public class MainMenu extends GameState {
	GamePanel gp;
	public int selectedIdx = 0;
	List<String> menuItems = new ArrayList<>();
	
	public MainMenu(GamePanel gp) {
		this.gp = gp;
		
		menuItems.add("deckBearbeiten");
		menuItems.add("serverErstellen");
		menuItems.add("serverBeitreten");
		menuItems.add("shop");
		menuItems.add("lexikon");
		menuItems.add("optionen");
		menuItems.add("beenden");
	}

	public void start() {
		selectedIdx = 0;
		gp.switchState(gp.mainMenuState);
		gp.playMusic(0);
	}

	@Override
	public void update() {
		if (gp.keyH.fPressed) {
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
		} else if (gp.keyH.upPressed) {
			if (selectedIdx  > 0) {
				selectedIdx--;
			}
		} else if (gp.keyH.downPressed) {
			if (selectedIdx < menuItems.size() - 1) {
				selectedIdx++;
			}
		} 
		gp.playSE(1);
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(gp.imageLoader.animHauptmenuBG.get(), 0, 0, gp.screenWidth, gp.screenHeight, null);
		g2.setFont(gp.font(25));

		for (int i = 0; i < menuItems.size(); i++) {
			int offsetY = gp.p(14) + (gp.p(1) * i);
			g2.setColor(Colors.getColorSelection(i, selectedIdx));
			gp.drawLib.drawArrowOnState(g2, gp.p(0.5), (int) (offsetY - gp.p(1.15)), true, selectedIdx == i);
			gp.drawLib.drawHover(g2, gp.p(2), offsetY - gp.p(0.65), gp.p(5), gp.p(1), selectedIdx == i);
			g2.drawString(gp.t(menuItems.get(i)), gp.p(2.5), offsetY);
		}
	}
}
