package com.cab.singleplayer.nodes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cab.GamePanel;
import com.cab.card.Card;
import com.cab.draw.MovingKoordinaten;
import com.cab.singleplayer.Singleplayer;

public class Opfer extends Node {
    private GamePanel gp;
    
    private int selectedIdx;
    
    Random random ;
    List<Integer> opferList;
    List<MovingKoordinaten> opferKoordinaten;

    private int state;
    private final int welcomeState = 0;
    private final int opferState = 1;
    private final int giftState = 2;

    private int giftCardId;


    public Opfer(Node left, Node right) {
        super(left, right);
    }

    @Override
    public void init(Singleplayer singleplayer) {
        super.init(singleplayer);
        gp = singleplayer.gp;

        random = new Random();
        opferList = new ArrayList<>();
        opferKoordinaten = new ArrayList<>();
        List<Integer> playerCards = new ArrayList<>(singleplayer.p.getCards());
        Random random = new Random();
        while (opferList.size() < 6 && !playerCards.isEmpty()) {
            int randomIndex = random.nextInt(playerCards.size());
            opferList.add(playerCards.get(randomIndex));
            opferKoordinaten.add(new MovingKoordinaten(0, gp.p(13)));
            playerCards.remove(randomIndex);
        }
        switchState(welcomeState);
    }



    private void switchState(int newState) {
        state = newState;
        selectedIdx = 0;
    }

    private boolean isState(int state) {
        return this.state == state;
    }

    private void opfern() {
        int opferId = opferList.get(selectedIdx);
        singleplayer.p.getCards().remove(Integer.valueOf(opferId));  

        while (true) {
            int idx = random.nextInt(gp.cardLoader.allCardIds.size());
            int randomCardId = gp.cardLoader.allCardIds.get(idx);
            if (!singleplayer.p.getCards().contains(randomCardId)) {
                singleplayer.p.getCards().add(randomCardId);
                giftCardId = randomCardId;
                break;
            }
        }
        switchState(giftState);
    }


    @Override
    public void update() {
        if (gp.keyH.fPressed) {
            if (isState(welcomeState)) {
                switchState(opferState);
            } else if (isState(opferState)) {
                opfern();
            } else if (isState(giftState)) {
                singleplayer.quitNode();
            }
        } else if (gp.keyH.leftPressed) {
            if (isState(opferState)) {
                if (selectedIdx > 0) {

                    selectedIdx--;
                }
            } 
        } else if (gp.keyH.rightPressed) {
            if (isState(opferState)) {
                if (selectedIdx < singleplayer.p.getCards().size() - 1) {
                    selectedIdx++;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.drawImage(gp.imageLoader.singleplayerOpferBG.get(), 0, 0, gp.screenWidth, gp.screenHeight, null);
        gp.drawLib.drawDialog(g2, 0, gp.p(20.5), gp.p(35), gp.p(1.5));

        g2.setColor(Color.RED);
        g2.setFont(gp.font(30));
        g2.drawString("Zeit für eine kleine Opfergabe, es lohnt sich", gp.p(12), gp.p(21.5));

        if (isState(welcomeState)) {
            gp.drawLib.drawDialog(g2, gp.p(16), gp.p(18.4), gp.p(5), gp.p(1));
            gp.drawLib.drawArrowOnState(g2, gp.p(16), gp.p(18), true, true);
            g2.setColor(Color.ORANGE);
            g2.setFont(gp.font(30));
            g2.drawString("Opfern", gp.p(18), gp.p(19));
        } else if (isState(opferState)) {
            gp.drawLib.drawDialog(g2, gp.p(12), gp.p(12), gp.p(14), gp.p(5));
            g2.setColor(Color.RED);
            g2.setFont(gp.font(30));
            g2.drawString("Wähle eine Opfergabe aus", gp.p(14.8), gp.p(13));
           
        
            for (int i = 0; i < opferList.size(); i++) {
                int x = gp.p(12.5) + i * gp.p(2.2);
                Card card = gp.cardLoader.getCard(opferList.get(i));
                MovingKoordinaten movingKoordinaten = opferKoordinaten.get(i);
                if (movingKoordinaten.isOnZielX(x) && movingKoordinaten.isOnZielY(gp.p(14))) {
                    gp.drawLib.drawCardStandardSize(g2, card, x, gp.p(14), i == selectedIdx, false);
                } else {
                    gp.drawLib.drawMovingImage(g2, gp.imageLoader.cardBackgroundImage, gp.p(1.9), gp.p(2.9), movingKoordinaten, x, gp.p(14), 50);
                }
            }
        } else if (isState(giftState)) {
            gp.drawLib.drawBoughtNewCard(g2, gp.cardLoader.getCard(giftCardId));
        }
    }
}

