package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.network.ClientCreater;
import com.cab.network.ClientJoiner;

public class Hauptmenu {
	GamePanel gp;
	int midScreenX;
	int arrowIconX;
	int selectedIdx;
	String[] menuItems = new String[6];
	int abstandY;
	
	Socket masterSocket;
	ObjectOutputStream outputStream;
	ObjectInputStream inputStream;
	
	public int currentState = 0;
	
	public int titleState = 0;
	public int optionState = 1;

	public int serverChoosePrivateOrPublic = 10; // Server auswahl öffentlich oder privat
	public int serverStartedState = 11; // Server starten
	public int serverClientConnected = 12; // Client ist beigetreten

	public int serverBrowserState = 20; // Server beitreten
	public int clientConnectedToServer = 21; // Server ausgewählt

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
							switchState(serverChoosePrivateOrPublic);					
						} else if (selectedIdx == 2) {
							gp.connection = new ClientJoiner(gp);
							gp.connection.start();	
							switchState(serverBrowserState);
						} else if (selectedIdx == 3) {
							gp.shop.start();
						}
					} else if (currentState == serverChoosePrivateOrPublic) {
						gp.connection = new ClientCreater(gp);
						gp.connection.start();	
						switchState(serverStartedState);
					}
					else if (currentState == serverBrowserState) {
						gp.connection.joinToServer(gp.connection.idsOfRunningServers.get(selectedIdx));
					} else if (currentState == serverClientConnected) {
						gp.connection.acceptClientForGame();
					}
				} else if (gp.keyH.upPressed) {
					if (currentState == titleState || currentState == serverBrowserState) {
						if (selectedIdx  > 0) {
							selectedIdx--;
						}
					}
				} else if (gp.keyH.downPressed) {
					if (currentState == titleState) {
						if (selectedIdx < menuItems.length - 1) {
							selectedIdx++;
						}
					} else if (currentState == serverBrowserState) {
						if (selectedIdx < gp.connection.idsOfRunningServers.size() - 1) {
							selectedIdx ++;
						}
					} 
				} else if (gp.keyH.qPressed) {
					if (currentState == serverChoosePrivateOrPublic) {
						switchState(titleState);
					} else if (currentState == serverStartedState || currentState == serverBrowserState) {
						gp.connection.close();
					} else if (currentState == clientConnectedToServer) {
						gp.connection.leaveServerRoom();
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
		} else if (currentState == serverChoosePrivateOrPublic) {
			g2.setColor(Color.red);
			g2.drawString("Öffentlich", Main.screenHalfWidth, Main.screenHalfHeight);
			g2.setColor(Color.gray);
			g2.drawString("Privat (noch nicht unterstützt)", Main.screenHalfWidth, Main.screenHalfHeight + gp.tileSize);
		} else if (currentState == serverStartedState) {
			g2.drawString("Server " + gp.connection.id + " gestartet...", midScreenX, gp.tileSize * 15);
		} else if (currentState == serverBrowserState) {
			if (gp.connection.idsOfRunningServers.size() > 0) {
				for (int i = 0; i < gp.connection.idsOfRunningServers.size(); i++) {
					if (selectedIdx == i) {
						g2.setColor(Color.red);
					} else {
						g2.setColor(Color.white);
					}
					g2.drawString(gp.connection.idsOfRunningServers.get(i).toString(), midScreenX, gp.tileSize * i + gp.tileSize);
				}
			} else {
				g2.drawString("Es wurden keine Server gefunden", midScreenX, Main.screenHeight / 2);

			}
		} else if (currentState == clientConnectedToServer) {
			g2.drawString("Client ID " + gp.connection.id, midScreenX, Main.screenHeight / 2);
			g2.drawString("Verbunden zum Server " + gp.connection.idOponent, midScreenX, Main.screenHeight / 2 + gp.tileSize);
			g2.drawString("Warten auf Start", midScreenX, Main.screenHeight / 2 + gp.tileSize * 3);
		} else if (currentState == serverClientConnected) {
			g2.drawString("Server ID " + gp.connection.id, midScreenX, Main.screenHeight / 2);
			g2.drawString("Client " + gp.connection.idOponent + " beigetreten", midScreenX, Main.screenHeight / 2 + gp.tileSize);
		}

		g2.setColor(Color.white);
		if (currentState == serverStartedState || currentState == serverBrowserState || currentState == clientConnectedToServer) {
			g2.drawString("Q Abbrechen", gp.tileSize, Main.screenHeight - gp.tileSize);
		} else if (currentState == serverClientConnected) {
			g2.drawString("F Spiel Starten", gp.tileSize, Main.screenHeight - gp.tileSize);
		}
	}
}
