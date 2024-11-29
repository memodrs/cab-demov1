package com.cab.states;

import java.awt.BasicStroke;
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

    public final int serverPerPage = 9;
    public int fromIndex = 0;
    public int toIndex = 0;
    public int numberOfTotalServer;

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

    private boolean serverBrowseHasNextPage() {
        return toIndex < numberOfTotalServer;
    }



    public void update() {
        if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed || gp.keyH.leftPressed || gp.keyH.rightPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
                if (gp.keyH.downPressed) {
                    if (currentState == serverBrowserState) {
                        if (selectedIdx < toIndex - 1) {
                            selectedIdx ++;
                        }
                    }
                } else if (gp.keyH.upPressed) {
                    if (currentState == serverBrowserState) {
                        if (selectedIdx > fromIndex) {
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
                        if (selectedIdx < numberOfTotalServer) {
                            gp.connection.joinToServer(gp.connection.idsOfRunningServers.get(selectedIdx));
                        }
                    }
                } else if (gp.keyH.rightPressed) {
                    if (currentState == serverBrowserState) {
                        if (serverBrowseHasNextPage()) {
                            fromIndex = toIndex;
                            toIndex = Math.min(fromIndex + serverPerPage, numberOfTotalServer);
                            selectedIdx = fromIndex;
                        }
                    }  
                } else if (gp.keyH.leftPressed) {
                    if (fromIndex > 0) {
                        toIndex = fromIndex;
                        fromIndex = Math.max(toIndex - serverPerPage, 0);
                        selectedIdx = fromIndex;
                    }
                }
            }
        }
    }
    

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
        g2.setFont(Main.v.brushedFont25);

        if (currentState == serverBrowserState) {


            
			if (gp.connection.idsOfRunningServers.size() > 0) {
                g2.setColor(Main.v.colorTransparentBlack);
                g2.fillRoundRect(Positions.tileSize4, Positions.tileSize3, Positions.tileSize6, Positions.tileSize12, 35, 35);
                g2.setColor(Color.white);
                g2.setStroke(new BasicStroke(5)); 
                g2.drawRoundRect(Positions.tileSize4, Positions.tileSize3, Positions.tileSize6, Positions.tileSize12, 25, 25);
    
                g2.setColor(Color.RED);
                g2.drawString(gp.t("laufendeServer"), Positions.tileSize5, Positions.tileSize4);
                
                int abstandIdx = 0;
				for (int i = fromIndex; i < toIndex; i++) {
					g2.setColor(gp.getColorSelection(i, selectedIdx));
                    if (selectedIdx == i) {
                        g2.drawImage(gp.imageLoader.navigationArrowRight, Positions.tileSize4, Positions.tileSize * abstandIdx + Positions.tileSize4, Positions.tileSize2, Positions.tileSize2, null);
                    }
					g2.drawString(gp.connection.idsOfRunningServers.get(i).toString(), Positions.tileSize6, Positions.tileSize * abstandIdx + Positions.tileSize5);
                    abstandIdx++;
				}
                if (fromIndex > 0) {
                    g2.drawImage(gp.imageLoader.navigationArrowLeft, Positions.tileSize4, Positions.tileSize13Point4, Positions.tileSize2, Positions.tileSize2, null);
                } else {
                    g2.drawImage(gp.imageLoader.navigationArrowLeftDisabled, Positions.tileSize4, Positions.tileSize13Point4, Positions.tileSize2, Positions.tileSize2, null); 
                }

                if (serverBrowseHasNextPage()) {
                    g2.drawImage(gp.imageLoader.navigationArrowRight, Positions.tileSize8, Positions.tileSize13Point4, Positions.tileSize2, Positions.tileSize2, null);
                } else {
                    g2.drawImage(gp.imageLoader.navigationArrowRightDisabled, Positions.tileSize8, Positions.tileSize13Point4, Positions.tileSize2, Positions.tileSize2, null);
                }
                g2.setColor(Color.YELLOW);
                g2.drawString(gp.t("serverAuswahlHinweis"), Positions.tileSize, Positions.tileSize19);
			} else {
                g2.setColor(Color.RED);
                g2.drawString(gp.t("keineServerGefunden"), Positions.tileSize, Positions.tileSize19);
			}
		} else if (currentState == serverJoinedState) {
            g2.setColor(Color.RED);
            g2.drawString(gp.t("verbundenMitSpieler") + " " + gp.connection.idOponent, Positions.tileSize, Positions.tileSize19);
            g2.setColor(Color.YELLOW);
            g2.drawString(gp.t("wartenAufServerStart"), Positions.tileSize, Positions.tileSize21);
		}
    }
}
