package com.cab.singleplayer.nodes;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.draw.MovingKoordinaten;
import com.cab.singleplayer.Singleplayer;

public class Coin extends Node {
    GamePanel gp;
    MovingKoordinaten coinKoordinaten;

    public Coin(Node left, Node right) {
        super(left, right);
    }

    @Override
    public void init(Singleplayer singleplayer) {
        super.init(singleplayer);
        gp = singleplayer.gp;
        singleplayer.p.setCoins(singleplayer.p.getCoins() + 1);
        coinKoordinaten = new MovingKoordinaten(0, gp.p(10));
    }



    @Override
    public void update() {
        if (gp.keyH.fPressed) {
            singleplayer.quitNode();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.drawImage(gp.imageLoader.singleplayerCoinBG.get(), 0, 0, gp.screenWidth, gp.screenHeight, null);
        gp.drawLib.drawDialog(g2, 0, gp.p(20.5), gp.p(35), gp.p(1.5));

        g2.setColor(Color.ORANGE);
        g2.setFont(gp.font(30));
        g2.drawString("Eine begehrte Goldmünze für dich, möge Sie dir deinen Weg erleichtern.", gp.p(9), gp.p(21.5));

        gp.drawLib.drawDialog(g2, gp.p(21.2), gp.p(9.8), gp.p(5), gp.p(2));
        gp.drawLib.drawHover(g2, gp.p(21), gp.p(9.8), gp.p(5), gp.p(2), true);
        gp.drawLib.drawArrowOnState(g2, gp.p(21), gp.p(10), true, true);

        gp.drawLib.drawMovingImage(g2, gp.imageLoader.iconCoind, gp.p(1.5), gp.p(1.5), coinKoordinaten, gp.p(23), gp.p(10), 50);
    }
}

