package com.cab.states;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.configs.Colors;

import com.cab.network.ClientJoiner;
import com.cab.network.Connection;

public class JoinServer extends GameState {
    GamePanel gp;

    Connection connection;
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
        connection = new ClientJoiner(gp);
        connection.start();	
        selectedIdx = 0;
        currentState = serverBrowserState;
        gp.switchState(gp.joinServerState);
    }

    private boolean serverBrowseHasNextPage() {
        return toIndex < numberOfTotalServer;
    }



    @Override
    public void update() {
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
                gp.mainMenu.start();
            } else if (currentState == serverJoinedState) {
                connection.close();
                gp.mainMenu.start();
            }
        } else if (gp.keyH.fPressed) {
            if (currentState == serverBrowserState) {
                if (selectedIdx < numberOfTotalServer) {
                    connection.joinToServer(connection.idsOfRunningServers.get(selectedIdx));
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
        gp.playSE(1);
    }
    
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.setFont(gp.font(25));

        if (currentState == serverBrowserState) {


            
			if (connection.idsOfRunningServers.size() > 0) {
                g2.setColor(Colors.transparentBlack);
                g2.fillRoundRect(gp.p(4), gp.p(3), gp.p(6), gp.p(12), 35, 35);
                g2.setColor(Color.white);
                g2.setStroke(new BasicStroke(5)); 
                g2.drawRoundRect(gp.p(4), gp.p(3), gp.p(6), gp.p(12), 25, 25);
    
                g2.setColor(Color.RED);
                g2.drawString(gp.t("laufendeServer"), gp.p(5), gp.p(4));
                
                int abstandIdx = 0;
				for (int i = fromIndex; i < toIndex; i++) {
					g2.setColor(Colors.getColorSelection(i, selectedIdx));
                    if (selectedIdx == i) {
                        g2.drawImage(gp.imageLoader.navigationArrowRight, gp.p(4), gp.p(1) * abstandIdx + gp.p(4), gp.p(2), gp.p(2), null);
                    }
					g2.drawString(connection.idsOfRunningServers.get(i).toString(), gp.p(6), gp.p(1) * abstandIdx + gp.p(5));
                    abstandIdx++;
				}
                if (fromIndex > 0) {
                    g2.drawImage(gp.imageLoader.navigationArrowLeft, gp.p(4), gp.p(13.4), gp.p(2), gp.p(2), null);
                } else {
                    g2.drawImage(gp.imageLoader.navigationArrowLeftDisabled, gp.p(4), gp.p(13.4), gp.p(2), gp.p(2), null); 
                }

                if (serverBrowseHasNextPage()) {
                    g2.drawImage(gp.imageLoader.navigationArrowRight, gp.p(8), gp.p(13.4), gp.p(2), gp.p(2), null);
                } else {
                    g2.drawImage(gp.imageLoader.navigationArrowRightDisabled, gp.p(8), gp.p(13.4), gp.p(2), gp.p(2), null);
                }
                g2.setColor(Color.YELLOW);
                g2.drawString(gp.t("serverAuswahlHinweis"), gp.p(1), gp.p(19));
			} else {
                g2.setColor(Color.RED);
                g2.drawString(gp.t("keineServerGefunden"), gp.p(1), gp.p(19));
			}
		} else if (currentState == serverJoinedState) {
            g2.setColor(Color.RED);
            g2.drawString(gp.t("verbundenMitSpieler") + " " + connection.idOponent, gp.p(1), gp.p(19));
            g2.setColor(Color.YELLOW);
            g2.drawString(gp.t("wartenAufServerStart"), gp.p(1), gp.p(21));
		}
    }
}
