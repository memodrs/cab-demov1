package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.configs.Colors;

public class FirstStart extends GameState {
    GamePanel gp;

    private final int START_PUNKTE = 200;
    private final int ANZAHL_FLUCH = 2;
    private final int ANZAHL_SEGEN = 2;
    private final int ANZAHL_MENSCH = 5;
    private final int ANZAHL_TIER = 5;  
    private final int ANZAHL_FABELWESEN = 3;
    private final int ANZAHL_NACHTGESTALT = 3;

    private List<Integer> xPositions;
    private List<Integer> yPositions;


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

        xPositions = new ArrayList<>();
        yPositions = new ArrayList<>();

        for (int i = 0; i < gp.player.truhe.size(); i++) {
            xPositions.add(gp.p(1));
            yPositions.add(gp.p(1));
        }
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
        gp.drawLib.drawDialog(g2, gp.p(2), gp.p(3.5), gp.p(34), gp.p(16));

        g2.setFont(gp.font(36));
        g2.setColor(Colors.orangeYellow);
        gp.drawLib.drawHover(g2, gp.p(1.5), gp.p(1.3), gp.p(39), gp.p(1), true);
        g2.drawString(gp.t("firstStateWillkommen"), gp.p(2), gp.p(2));

        g2.setColor(Color.RED);
        gp.drawLib.drawHover(g2, gp.p(17.8), gp.p(17.4), gp.p(1.5), gp.p(1), true);
        gp.drawLib.drawArrowOnState(g2, gp.p(16), gp.p(17), true, true);
        g2.drawString("OK", gp.p(18), gp.p(18.15));

        int cardSpacing = gp.p(3); 

        for (int i = 0; i < gp.player.truhe.size(); i++) {
            int abstandX = (i > 10) ? cardSpacing * (i - 10) : cardSpacing * i;
            
            int xZiel = cardSpacing + abstandX;
            int yZiel = (i > 10) ? (i % 2 == 0 ? gp.p(10) : gp.p(13)) : (i % 2 == 0 ? gp.p(4) : gp.p(6));   
            
            int x = xPositions.get(i);
            int y = yPositions.get(i);

            Card card = gp.cardLoader.getCard(gp.player.truhe.get(i));        
            gp.drawLib.drawCardStandardSize(g2, card, x, y, false, true);

            if (x < xZiel) {
                xPositions.set(i, x + 20);
            }

            if (y < yZiel) {
                yPositions.set(i, y + 20);
            }

            if (x >= xZiel && y >= yZiel) {
                g2.setColor(Color.WHITE);
                g2.setFont(gp.font(12));
                g2.drawString(card.getName(), x, y + gp.p(3.3));
            }
        }
    }
}
