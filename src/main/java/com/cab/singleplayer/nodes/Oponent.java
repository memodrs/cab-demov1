package com.cab.singleplayer.nodes;

import java.awt.Color;
import java.awt.Graphics2D;

import com.cab.GamePanel;
import com.cab.singleplayer.Singleplayer;


public class Oponent extends Node {
    GamePanel gp;

    public Oponent(Node left, Node right) {
        super(left, right);

    }

    @Override
    public void init(Singleplayer singleplayer) {
        super.init(singleplayer);
        gp = singleplayer.gp;
        singleplayer.p.setCoins(singleplayer.p.getCoins() + 1);
    }
    
    

    @Override
    public void update() {
        if (gp.keyH.fPressed) {
            singleplayer.startCardGame();
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.setFont(gp.font(30));
        gp.drawLib.drawArrowOnState(g2, gp.p(10), gp.p(18), true, true);
        g2.drawString("Start", gp.p(12), gp.p(19));
    }
}
