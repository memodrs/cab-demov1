package com.cab.states;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.configs.Colors;
import com.cab.configs.Positions;
import com.cab.network.ClientCreater;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class CreateServer {
    GamePanel gp;

    int selectedIdx = 0;
    public int currentState = 0;

    public int askPrivateOrPublicState = 0;
    public int serverCreatedState = 1;
    public int clientJoinedState = 2;

    public CreateServer(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        switchState(askPrivateOrPublicState);
        gp.gameState = gp.createServerState;
        
    }

    public void switchState(int state) {
        this.selectedIdx = 0;
        this.currentState = state;
    }

    public void update() {
        if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
                if (gp.keyH.downPressed) {
                    if (currentState == askPrivateOrPublicState) {
                        if (selectedIdx < 1) {
                            selectedIdx++;
                        }
                    }
                } else if (gp.keyH.upPressed) {
                    if (currentState == askPrivateOrPublicState) {
                        if (selectedIdx > 0) {
                            selectedIdx--;
                        }
                    }
                } else if (gp.keyH.qPressed) {
                    if (currentState == askPrivateOrPublicState) {
                        gp.mainMenu.start();
                    } else if (currentState == serverCreatedState || currentState == clientJoinedState) {
                        gp.connection.close();
                        switchState(askPrivateOrPublicState);
                    } 
                } else if (gp.keyH.fPressed) {
                    if (currentState == askPrivateOrPublicState) {
                        if (selectedIdx == 0) {
                            gp.connection = new ClientCreater(gp);
                            gp.connection.start();	
                            switchState(serverCreatedState);
                        } else if (selectedIdx == 1) {
                            //TODO Privater raume PSW festlegen
                        }
                    } else if (currentState == clientJoinedState) {
                        gp.connection.acceptClientForGame();
                    } 
                }
                gp.playSE(1);
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
        g2.setFont(Main.v.brushedFont20);

        if (currentState == askPrivateOrPublicState) {
            g2.setColor(Colors.transparentBlack);
            g2.fillRoundRect(Positions.tileSize4, Positions.tileSize10, Positions.tileSize6, Positions.tileSize5, 35, 35);
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(5)); 
            g2.drawRoundRect(Positions.tileSize4, Positions.tileSize10, Positions.tileSize6, Positions.tileSize5, 25, 25);
    
            g2.setColor(gp.getColorSelection(0, selectedIdx));
            g2.drawString(gp.t("oeffentlich"), Positions.tileSize5, Positions.tileSize11Point4);
            g2.setColor(gp.getColorSelection(1, selectedIdx));
            g2.drawString(gp.t("privat"), Positions.tileSize5, Positions.tileSize13);
            
            g2.setColor(Color.RED);
            if (selectedIdx == 0) {
                g2.drawString(gp.t("serverOeffentlichB"), Positions.tileSize, Positions.tileSize19);
            } else if (selectedIdx == 1) {
                g2.drawString(gp.t("ServerPrivatBeschr"), Positions.tileSize, Positions.tileSize19);
            }

        } else if(currentState == serverCreatedState) {
            g2.setColor(Color.RED);
            g2.drawString(gp.t("server") + " " + gp.connection.id + " " + gp.t("serverGestarted"), Positions.tileSize, Positions.tileSize19);
        } else if(currentState == clientJoinedState) {
            g2.setColor(Color.RED);
			g2.drawString(gp.t("spielerMitID") + " " + gp.connection.idOponent + " " + gp.t("beigetreten"), Positions.tileSize, Positions.tileSize19);
            g2.setColor(Color.YELLOW);
			g2.drawString(gp.t("auswahltasteBestaetigen"), Positions.tileSize, Positions.tileSize21);
        }
    }
}
