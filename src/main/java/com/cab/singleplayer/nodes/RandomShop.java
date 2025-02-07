package com.cab.singleplayer.nodes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.cab.GamePanel;
import com.cab.card.Card;
import com.cab.configs.Colors;
import com.cab.draw.MovingKoordinaten;
import com.cab.singleplayer.Singleplayer;

public class RandomShop extends Node {
    GamePanel gp;

    private List<MovingKoordinaten> cardKoordinaten;
    private List<Integer> cardOffers;
    private Random random;

    int selectedIdx;
    int currentState;
    final int noCoinsState = 0;
    final int selectCardToBuyState = 1;
    final int showBoughtCardState = 2; 

    public RandomShop(Node left, Node right) {
        super(left, right);
    }

    @Override
    public void init(Singleplayer singleplayer) {
        super.init(singleplayer);
        gp = singleplayer.gp;

        this.cardOffers = generateRandomCardOffers();       
        this.cardKoordinaten = new ArrayList<>();
        for (int i = 0; i < cardOffers.size(); i++) {
            cardKoordinaten.add(new MovingKoordinaten(0, gp.p(9)));
         }

        
         if (singleplayer.p.hasCoins()) {
            switchState(selectCardToBuyState);
        } else {
            switchState(noCoinsState);
        }
    }

    private List<Integer> generateRandomCardOffers() {
        random = new Random();
        List<Integer> offers = new ArrayList<>();
        Set<Integer> playerCards = new HashSet<>(singleplayer.p.getCards());
        
        while (offers.size() < 3) {
            int randomCardId = gp.cardLoader.allCardIds.get(random.nextInt(gp.cardLoader.allCardIds.size()));
            if (!playerCards.contains(randomCardId) && !offers.contains(randomCardId)) {
                offers.add(randomCardId);
            }
        }
        return offers;
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
        } else if (gp.keyH.rightPressed) {
            if (isCurrentState(selectCardToBuyState)) {
                if (selectedIdx < cardOffers.size() - 1) {
                    selectedIdx++;
                }
            }
        } else if (gp.keyH.leftPressed) {
            if (isCurrentState(selectCardToBuyState)) {
                if (selectedIdx > 0) {
                    selectedIdx--;
                }
            }
        } else if (gp.keyH.fPressed) {
            if (isCurrentState(selectCardToBuyState)) {
                singleplayer.p.getCards().add(cardOffers.get(selectedIdx));
                singleplayer.p.setCoins(singleplayer.p.getCoins() - 1);
                switchState(showBoughtCardState);
            }
        }
        

        else if (gp.keyH.enterPressed) {
            this.cardKoordinaten = new ArrayList<>();
            for (int i = 0; i < cardOffers.size(); i++) {
                cardKoordinaten.add(new MovingKoordinaten(0, gp.p(9)));
             }
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
        } else if (isCurrentState(selectCardToBuyState)) {
            for (int i = 0; i < cardOffers.size(); i++) {
                int width = gp.p(2.5);
                int height = gp.p(4);

                if (selectedIdx == i) {
                    width = width + gp.p(0.4);
                    height = height + gp.p(0.8);
                }
                Card card = gp.cardLoader.getCard(cardOffers.get(i));

                gp.drawLib.drawMovingImage(g2, card.getImage(), width, height, cardKoordinaten.get(i), gp.p(14) + i * gp.p(4), gp.p(10), 50);
                gp.drawLib.drawArrowOnState(g2, gp.p(13) + i * gp.p(3.9), gp.p(12), true, selectedIdx == i);


                g2.setColor(Color.RED);
                g2.setFont(gp.font(30));
                g2.drawString("Welche Karte möchtest du kaufen?", gp.p(14), gp.p(21.5));

                if (gp.keyH.fPressed) {
                    g2.setColor(Color.WHITE); 
                    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
                    g2.drawImage(gp.imageLoader.cardBackgroundImage, gp.p(14) + selectedIdx * gp.p(4), gp.p(10), gp.p(2.9), gp.p(4.8), null);
                }
            }
        }
    }

}
