package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.cab.GamePanel;
import com.cab.card.Art;
import com.cab.card.Card;

public class FirstStart extends GameState {
    GamePanel gp;

    private final int START_PUNKTE = 200;
    private final int ANZAHL_FLUCH = 2;
    private final int ANZAHL_SEGEN = 2;
    private final int ANZAHL_MENSCH = 5;
    private final int ANZAHL_TIER = 5;  
    private final int ANZAHL_FABELWESEN = 3;
    private final int ANZAHL_NACHTGESTALT = 3;

    public FirstStart(GamePanel gp)  {
        this.gp = gp;
    }

    public void start() {
        addCardsToStapel(Art.Fluch, ANZAHL_FLUCH);
        addCardsToStapel(Art.Segen, ANZAHL_SEGEN);
        addCardsToStapel(Art.Mensch, ANZAHL_MENSCH);
        addCardsToStapel(Art.Tier, ANZAHL_TIER);
        addCardsToStapel(Art.Fabelwesen, ANZAHL_FABELWESEN);
        addCardsToStapel(Art.Nachtgestalt, ANZAHL_NACHTGESTALT);
        gp.player.punkte = START_PUNKTE;
        gp.save();

        gp.switchState(gp.firstState);
    }

    private void addCardsToStapel(Art art, int limit) {
        List<Integer> cards = this.gp.cardLoader.allCardIds.stream().filter(id -> gp.cardLoader.getCard(id).getArt() == art).collect(Collectors.toList());
        Set<Integer> existingCards = new HashSet<>(gp.player.truhe);
        Random random = new Random();
    
        for (int idx = 0; idx < limit; idx++) {
            int randomId;
            do {
                randomId = cards.get(random.nextInt(cards.size()));
            } while (existingCards.contains(randomId));
    
            gp.player.truhe.add(randomId);
            gp.player.newCardIds.add(randomId);
            existingCards.add(randomId);
        }
    }
    
    @Override
    public void update() {
        if (gp.keyH.fPressed) {
            gp.cardMenu.start();
        }
        gp.playSE(1);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.setFont(gp.font(36));
        g2.setColor(Color.YELLOW);
        g2.drawString(gp.t("firstStateWillkommen"), gp.p(2), gp.p(2));

        int cardSpacing = gp.p(2); 

        for (int i = 0; i < gp.player.truhe.size(); i++) {
            int abstandX = (i > 10) ? cardSpacing * (i - 10) : cardSpacing * i;
            int y = (i > 10) ? (i % 2 == 0 ? gp.p(10) : gp.p(13)) : (i % 2 == 0 ? gp.p(4) : gp.p(6));   
            Card card = gp.cardLoader.getCard(gp.player.truhe.get(i));        
            gp.drawLib.drawCardStandardSize(g2, card, cardSpacing + abstandX, y, false, true);
        }

        g2.drawString(gp.t("fWeiter"), gp.p(2), gp.p(19));
    }
}
