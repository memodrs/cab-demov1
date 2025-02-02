package com.cab.states;

import com.cab.GamePanel;
import com.cab.configs.Colors;

import com.cab.network.ClientCreater;
import com.cab.network.Connection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

public class CreateServer extends GameState{
    GamePanel gp;

    List<String> menuItems = new ArrayList<>();
    Connection connection;

    int selectedIdx = 0;
    public int currentState = 0;

    public int askPrivateOrPublicState = 0;
    public int serverCreatedState = 1;
    public int clientJoinedState = 2;

    public CreateServer(GamePanel gp) {
        this.gp = gp;

        menuItems.add("oeffentlich");
        menuItems.add("privat");
    }

    public void start() {
        switchState(askPrivateOrPublicState);
        gp.switchState(gp.createServerState);        
    }

    public void switchState(int state) {
        this.selectedIdx = 0;
        this.currentState = state;
    }

    @Override
    public void update() {
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
                connection.close();
                switchState(askPrivateOrPublicState);
            } 
        } else if (gp.keyH.fPressed) {
            if (currentState == askPrivateOrPublicState) {
                if (selectedIdx == 0) {
                    connection = new ClientCreater(gp);
                    connection.start();	
                    switchState(serverCreatedState);
                } else if (selectedIdx == 1) {
                    //TODO Privater raume PSW festlegen
                }
            } else if (currentState == clientJoinedState) {
                connection.acceptClientForGame();
            } 
        }
        gp.playSE(1);
    }
    

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        gp.drawLib.drawDialog(g2, gp.p(1), gp.p(9), gp.p(6.5), gp.p(5));

        if (currentState == askPrivateOrPublicState) {
            for (int i = 0; i < menuItems.size(); i++) {
                g2.setFont(gp.fontSelection(20, 22, i == selectedIdx));
                gp.drawLib.drawHover(g2, gp.p(2.2), gp.p(10.4) + i * gp.p(1.5), gp.p(5), gp.p(1), i == selectedIdx);
                gp.drawLib.drawArrowOnState(g2, gp.p(1), gp.p(10) + i * gp.p(1.5), true, i == selectedIdx);
                g2.setColor(Colors.getColorSelection(selectedIdx, i));
                g2.drawString(gp.t(menuItems.get(i)), gp.p(2.6), gp.p(11) + i * gp.p(1.5));
            }  
            g2.setFont(gp.font(20));
            g2.setColor(Color.RED);
            if (selectedIdx == 0) {
                g2.drawString(gp.t("serverOeffentlichB"), gp.p(1), gp.p(19));
            } else if (selectedIdx == 1) {
                g2.drawString(gp.t("ServerPrivatBeschr"), gp.p(1), gp.p(19));
            }
        } else if(currentState == serverCreatedState) {
            g2.setFont(gp.font(20));
            g2.setColor(Color.YELLOW);
            g2.drawString(gp.t("serverLaeuft"), gp.p(2.6), gp.p(10.5));
            g2.setColor(Color.RED);
            g2.drawString(gp.t("server") + " " + connection.id + " " + gp.t("serverGestarted"), gp.p(1), gp.p(19));
        } else if(currentState == clientJoinedState) {
            g2.setFont(gp.font(20));
            g2.setColor(Color.YELLOW);
            g2.drawString(gp.t("spielerMitID") + " " + connection.idOponent, gp.p(1.9), gp.p(10.5));
            g2.drawString(gp.t("beigetreten"), gp.p(3), gp.p(11.5));           
            g2.setColor(Color.RED);
            g2.drawString(gp.t("auswahltasteBestaetigen"), gp.p(1), gp.p(19));
        }
    }
}
