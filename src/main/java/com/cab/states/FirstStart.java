package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.cab.GamePanel;
import com.cab.card.Art;

public class FirstStart extends GameState {
    GamePanel gp;

    public FirstStart(GamePanel gp)  {
        this.gp = gp;
    }

    public void start() {
        addCardsToStapel(Art.Fluch, 2);
        addCardsToStapel(Art.Segen, 2);

        addCardsToStapel(Art.Mensch, 5);
        addCardsToStapel(Art.Tier, 5);
        addCardsToStapel(Art.Fabelwesen, 3);
        addCardsToStapel(Art.Nachtgestalt, 3);

        gp.player.punkte = 200;
        gp.save();

        gp.switchState(gp.firstState);
    }

    private void addCardsToStapel(Art art, int limit) {
        List<Integer> cards = this.gp.cardLoader.allCardIds.stream().filter(id -> gp.cardLoader.getCard(id).getArt() == art).collect(Collectors.toList());
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
    
    @Override
    public void update() {
		if(gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.qPressed || gp.keyH.fPressed || gp.keyH.gPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
				if (gp.keyH.fPressed) {
                    gp.cardMenu.start();
                }
                gp.playSE(1);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.setFont(gp.font(36));
        g2.setColor(Color.YELLOW);
        g2.drawString(gp.t("firstStateWillkommen"), gp.p(2), 0);
        int abstandX = gp.p(1);
        for (int i = 0; i < gp.player.truhe.size(); i++) {
            g2.drawImage(gp.cardLoader.getCard(gp.player.truhe.get(i)).getImage(), gp.p(2) + abstandX * i, gp.p(13), gp.p(1.9), gp.p(2.9), null);
        }

        g2.drawString(gp.t("fWeiter"), gp.p(2), gp.p(18));
    }
}
