package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;

import com.cab.GamePanel;
import com.cab.card.Art;

public class FirstStart {
    GamePanel gp;

    public FirstStart(GamePanel gp) {
        this.gp = gp;
    }

    public void init() {
        addCardsToStapel(Art.Mensch, 5);
        addCardsToStapel(Art.Tier, 5);
        addCardsToStapel(Art.Fabelwesen, 3);
        addCardsToStapel(Art.Nachtgestalt, 3);
        addCardsToStapel(Art.Segen, 2);
        addCardsToStapel(Art.Fluch, 2);
        gp.save();
    }

    private void addCardsToStapel(Art art, int limit) {
        List<Integer> cards = this.gp.cardLoader.allCardIds.stream().filter(id -> gp.cardLoader.getCard(id).art == art).toList();
        boolean stop = false;
        Random r = new Random();
        int idx = 0;

        while (!stop) {
            int id = cards.get(r.nextInt(cards.size()));

            if (!gp.player.truhe.contains(id)) {
                gp.player.truhe.add(id);
                gp.player.newCardIds.add(id);
                idx++;
            }

            if (idx == limit) {
                stop = true;
            }            
        }
    }
    
    public void update() {
		if(gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.qPressed || gp.keyH.fPressed || gp.keyH.gPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
				if (gp.keyH.fPressed) {
                    gp.cardMenu.showStapelEditor();
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.drawString("Das ist der erste Screen", 0, 0);
    }
}
