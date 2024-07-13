package com.cab;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.cab.network.ClientCreater;
import com.cab.network.ClientJoiner;

public class Hauptmenu {
	GamePanel gp;
	int midScreenX;
	int selectedIdx;
	String[] menuItems = new String[3];
	int abstandY;
	
	Socket masterSocket;
	ObjectOutputStream outputStream;
	ObjectInputStream inputStream;
	
	public int currentState = 0;
	
	public int titleState = 0;
	public int serverStartedState = 1;
	public int serverBrowserState = 2;

	public int serverClientConnected = 3;
	public int clientConnectedToServer = 4;

	public Hauptmenu(GamePanel gp) {
		this.gp = gp;
		
		midScreenX =  Main.screenWidth / 2 - gp.tileSize * 3;

		menuItems[0] = "Deck bearbeiten";
		menuItems[1] = "Server starten";
		menuItems[2] = "Server beitreten";
	}
	private void switchState(int state) {
		selectedIdx = 0;
		currentState = state;
	}

	public void update() {
		if (gp.gameState == gp.hauptmenuState) {
			if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
				if (!gp.blockBtn) {
					gp.blockBtn = true;
					if (gp.keyH.fPressed) {
						if (currentState == titleState) {
							if (selectedIdx == 0) {
								gp.cardMenu.showStapelEditor(false);
							} else if (selectedIdx == 1) {
								gp.connection = new ClientCreater(gp);
								gp.connection.start();	
								switchState(serverStartedState);					
							} else if (selectedIdx == 2) {
								gp.connection = new ClientJoiner(gp);
								gp.connection.start();
								switchState(serverBrowserState);
							}
						} else if (currentState == serverBrowserState) {
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
						if (currentState == serverStartedState || currentState == serverBrowserState) {
							gp.connection.close();
						} else if (currentState == clientConnectedToServer) {
							gp.connection.leaveServerRoom();
						}
					}
					gp.playSE(4);
				}
			} 
		}
	}
	
	public void draw(Graphics2D g2) {
		
		g2.drawImage(gp.imageLoader.animHauptmenuBG.get(), 0, 0, Main.screenWidth, Main.screenHeight, null);

		g2.setFont(Main.v.fontTimesNewRoman36);
		if (currentState == titleState) {
			for (int i = 0; i < menuItems.length; i++) {
				int offsetY = gp.tileSize * 2 + (gp.tileSize * i);
				g2.setColor(Color.WHITE);
				if (selectedIdx == i) {
					g2.setColor(Color.red);
				}
				g2.drawString(menuItems[i], midScreenX, offsetY);
			}
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
