package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Positions;
import com.cab.network.ClientJoiner;

public class JoinServer {
    GamePanel gp;

    int selectedIdx = 0;
    public int currentState = 0;

    public int serverBrowserState = 0;
    public int serverJoinedState = 1;

    public JoinServer(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        gp.connection = new ClientJoiner(gp);
        gp.connection.start();	
        selectedIdx = 0;
        currentState = serverBrowserState;
        gp.gameState = gp.joinServerState;
    }

    public void update() {
        if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
                if (gp.keyH.downPressed) {
                    if (currentState == serverBrowserState) {
                        if (selectedIdx < gp.connection.idsOfRunningServers.size() - 1) {
                            selectedIdx ++;
                        }
                    }
                } else if (gp.keyH.upPressed) {
                    if (currentState == serverBrowserState) {
                        if (selectedIdx  > 0) {
                            selectedIdx--;
                        }
                    }
                } else if (gp.keyH.qPressed) {
                    if (currentState == serverBrowserState) {
                        gp.gameState = gp.hauptmenuState;
                    } else if (currentState == serverJoinedState) {
                        gp.connection.close();
                        gp.hauptmenu.start();
                    }
                } else if (gp.keyH.fPressed) {
                    if (currentState == serverBrowserState) {
                        gp.connection.joinToServer(gp.connection.idsOfRunningServers.get(selectedIdx));
                    }
                }
            }
        }
    }
    

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
        g2.setFont(Main.v.brushedFont20);

        if (currentState == serverBrowserState) {
			if (gp.connection.idsOfRunningServers.size() > 0) {
				for (int i = 0; i < gp.connection.idsOfRunningServers.size(); i++) {
					if (selectedIdx == i) {
						g2.setColor(Color.red);
					} else {
						g2.setColor(Color.white);
					}
					g2.drawString(gp.connection.idsOfRunningServers.get(i).toString(), Positions.screenHalfWidth, Positions.tileSize * i + Positions.tileSize);
				}
			} else {
                g2.drawString("Es wurden keine Server gefunden", Positions.tileSize, Positions.tileSize19);
			}
		} else if (currentState == serverJoinedState) {
            g2.setColor(Color.RED);
			g2.drawString("Verbunden mit dem Spieler, mit der ID " + gp.connection.idOponent, Positions.tileSize, Positions.tileSize19);
            g2.setColor(Color.YELLOW);
			g2.drawString("Warten auf start vom Server", Positions.tileSize, Positions.tileSize21);
		}
    }
}
