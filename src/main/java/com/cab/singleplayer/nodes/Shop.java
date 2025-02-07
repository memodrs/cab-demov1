package com.cab.singleplayer.nodes;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.singleplayer.Singleplayer;

public class Shop extends Node {
    GamePanel gp;

    int selectedIdx;
    int currentState;
    final int noCoinsState = 0;
    final int selectCardToBuyState = 1;
    final int showBoughtCardState = 2; 

    public Shop(Node left, Node right) {
        super(left, right);
    }

    @Override
    public void init(Singleplayer singleplayer) {
        super.init(singleplayer);
        if (singleplayer.p.hasCoins()) {
            switchState(selectCardToBuyState);
        } else {
            switchState(noCoinsState);
        }
        gp = singleplayer.gp;
    }

    private void switchState(int state) {
        currentState = state;
        selectedIdx = 0;
    }

    private boolean isCurrentState(int state) {
        return currentState == state;
    }

    @Override
    public void update() {
        if (gp.keyH.fPressed) {
            if (isCurrentState(noCoinsState)) {
                singleplayer.quitNode();
            }

        } else if (gp.keyH.qPressed) {
            singleplayer.quitNode();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.singleplayerShopBG.get(), 0, 0, gp.screenWidth, gp.screenHeight, null);
        gp.drawLib.drawDialog(g2, 0, gp.p(20.5), gp.p(35), gp.p(1.5));
        if (currentState == noCoinsState) {
            g2.setColor(Color.RED);
            g2.setFont(gp.font(30));
            g2.drawString("Du verschwendest meine Zeit, kein Gold keine Karten!", gp.p(11), gp.p(21.5));

            gp.drawLib.drawDialog(g2, gp.p(16.8), gp.p(18.2), gp.p(6), gp.p(1.3));
            g2.setColor(Color.YELLOW);
            g2.drawString("Verschwinden", gp.p(18), gp.p(19));
            gp.drawLib.drawArrowOnState(g2, gp.p(16.4), gp.p(17.85), true, true);
        }
    }
}
