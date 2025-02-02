package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cab.GamePanel;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.configs.Colors;
import com.cab.draw.ShakingKoordinaten;


public class Shop extends GameState {
    GamePanel gp;  
    List<Art> booster = new ArrayList<Art>();
    List<Integer> xPositionsBooster = new ArrayList<>();

    Art artWantedToBuy;
    int idBoughtCard;
    Card boughtCard;
    
    int selectedIdx;
   
    int currentState;
    int shopState = 0;
    int askToBuyState = 1;
    int showBoughtCardState = 2;

    ShakingKoordinaten shakingKoordinaten;


    public Shop(GamePanel gp) {
        this.gp = gp;

        booster.add(Art.Mensch);
        booster.add(Art.Tier);
        booster.add(Art.Fabelwesen);
        booster.add(Art.Nachtgestalt);
        booster.add(Art.Segen);
        booster.add(Art.Fluch);

        xPositionsBooster.add(gp.p(2));
        xPositionsBooster.add(gp.p(8));
        xPositionsBooster.add(gp.p(13));
        xPositionsBooster.add(gp.p(18));
        xPositionsBooster.add(gp.p(23));
        xPositionsBooster.add(gp.p(28));

        shakingKoordinaten = new ShakingKoordinaten(gp.p(16), gp.p(2));
    }           

    public void start() {
        artWantedToBuy = null;
        switchState(shopState);
        gp.switchState(gp.shopState);
        gp.playMusic(4);
    }

    private void switchState(int state) {
        if (state == shopState && artWantedToBuy != null) {
            selectedIdx = booster.indexOf(artWantedToBuy);
        } else {
            selectedIdx = 0;
        }
        currentState = state;
    }

    private void buy() {
        List<Integer> cardPool = new ArrayList<>();
        for (int id : gp.cardLoader.allCardIds) {
            if (gp.cardLoader.getCard(id).getArt() == artWantedToBuy && !gp.player.stapel.contains(id) && !gp.player.truhe.contains(id)) {
                cardPool.add(id);
            }
        }

        if (cardPool.size() > 0) {
            Random r = new Random();
            int randomIdx = r.nextInt(cardPool.size());

            idBoughtCard = cardPool.get(randomIdx);
            boughtCard = gp.cardLoader.getCard(idBoughtCard);
            gp.player.truhe.add(idBoughtCard);
            gp.player.newCardIds.add(idBoughtCard);
            gp.player.punkte = gp.player.punkte - getPreisForArt(artWantedToBuy);
            gp.save();
            switchState(showBoughtCardState);
        } else {
            switchState(shopState);
            gp.showMsg("alleKartenBesitzt");
        }
    }

    private int getPreisForArt(Art art) {
        if (art == Art.Segen || art == Art.Fluch) {
            return 60;
        } else {
            return 40;
        }
    }

    @Override
    public void update() {
        if (gp.keyH.leftPressed) {
            if (currentState == shopState || currentState == askToBuyState) {
                if (selectedIdx > 0) {
                    selectedIdx--;
                }
            }
        } else if (gp.keyH.rightPressed) {
            if (currentState == shopState) {
                if (selectedIdx < 5) {
                    selectedIdx++;
                }
            } else if (currentState == askToBuyState) {
                if (selectedIdx < 1) {
                    selectedIdx++;
                }
            }
        } else if (gp.keyH.fPressed) {
            if (currentState == shopState) {
                artWantedToBuy = booster.get(selectedIdx);
                if (gp.player.punkte >= getPreisForArt(artWantedToBuy)) {
                    switchState(askToBuyState);
                } else {
                    gp.showMsg("zuWenigPunkte");
                }
            } else if (currentState == askToBuyState) {
                if (selectedIdx == 0) {
                    buy();
                } else {
                    switchState(shopState);
                }
            } else if (currentState == showBoughtCardState) {
                switchState(shopState);
            }
        } else if (gp.keyH.qPressed) {
            if (currentState == shopState) {
                gp.mainMenu.start();
            } else if (currentState == askToBuyState) {
                switchState(shopState);
            }  else if (currentState == showBoughtCardState) {
                switchState(shopState);
            }
        }
        gp.playSE(1);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.shopBackgroundImage, 0, 0, gp.screenWidth, gp.p(16), null);
        g2.setFont(gp.font(36));
        g2.setColor(Color.RED);
        g2.drawString(gp.t("punkte") + ":", gp.p(1), gp.p(21));
        g2.drawString("" + gp.player.punkte, gp.p(5), gp.p(21));
        g2.setFont(gp.font(36));
        g2.setColor(Color.red);
        g2.drawString(gp.t("shop"), gp.p(1), gp.p(17));
        gp.drawLib.drawHover(g2, gp.p(0.6), gp.p(17.2), gp.p(3), gp.p(0.2), true);

        if (currentState == shopState) {
            for (int i = 0; i < booster.size(); i++) {
                if (selectedIdx > 1) {
                    g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(selectedIdx - 2)), gp.p(1), gp.p(6), gp.p(4), gp.p(7), null);
                    g2.setColor(Colors.transparentDarkBlack); 
                    g2.fillRect(gp.p(1.5), gp.p(6.3), gp.p(3.1), gp.p(6.3));                
                }
                if (selectedIdx > 0) {
                    g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(selectedIdx - 1)), gp.p(7), gp.p(5), gp.p(5), gp.p(8), null);
                    g2.setColor(Colors.transparentBlack); 
                    g2.fillRect(gp.p(7.5), gp.p(5.3), gp.p(4), gp.p(7.3));       
                }
                if (selectedIdx < booster.size() - 1) {
                    g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(selectedIdx + 1)), gp.p(25), gp.p(5), gp.p(5), gp.p(8), null);
                    g2.setColor(Colors.transparentBlack); 
                    g2.fillRect(gp.p(25.5), gp.p(5.3), gp.p(4), gp.p(7.3));  
                }
                if (selectedIdx < booster.size() - 2) {
                    g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(selectedIdx + 2)), gp.p(31), gp.p(6), gp.p(4), gp.p(7), null);
                    g2.setColor(Colors.transparentDarkBlack); 
                    g2.fillRect(gp.p(31.5), gp.p(6.3), gp.p(3.1), gp.p(6.3));     
                }
            }

            g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(selectedIdx)), shakingKoordinaten.getX(), shakingKoordinaten.getY(), gp.p(6.5), gp.p(10), null);

            g2.setColor(Color.ORANGE);
            g2.drawString(booster.get(selectedIdx) + gp.t("packPreis") + getPreisForArt(booster.get(selectedIdx)), gp.p(1), gp.p(19));
    
            g2.setColor(Color.RED);
        } else if (currentState == askToBuyState) {
            g2.setColor(Colors.transparentBlack); 
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);  

            g2.drawImage(gp.imageLoader.getBoosterForArt(artWantedToBuy), shakingKoordinaten.getX(), shakingKoordinaten.getY(),  gp.p(6.5), gp.p(10), null);

            g2.drawString(artWantedToBuy + "-Pack", gp.p(4), gp.p(17));
            g2.setColor(Color.ORANGE);
            g2.drawString(gp.t("packKaufBestaetigung") + getPreisForArt(artWantedToBuy) + gp.t("kaufenWollen"), gp.p(1), gp.p(19));

            g2.setColor(Colors.getColorSelection(0, selectedIdx));
            g2.drawString(gp.t("ja"), gp.p(26), gp.p(19));

            g2.setColor(Colors.getColorSelection(1, selectedIdx));
            g2.drawString(gp.t("nein"), gp.p(28), gp.p(19));

        } else if (currentState == showBoughtCardState) {
            g2.setColor(Color.ORANGE);
            g2.drawString(gp.t("neueKarteErhalten"), gp.p(4), gp.p(19));
            g2.drawImage(boughtCard.getImage(), gp.p(16), gp.p(1), gp.p(7), gp.p(12), null);
            g2.drawImage(gp.imageLoader.animHolo.get(), gp.p(16), gp.p(1), gp.p(7), gp.p(12), null);
            g2.setFont(gp.font(36));
            g2.setColor(Color.WHITE);
            g2.drawString(boughtCard.getName(), gp.p(4), gp.p(21));
            g2.drawImage(gp.imageLoader.getArtIconForArt(boughtCard.getArt(), true), gp.p(15), gp.p(19), gp.p(2), gp.p(2), null);
            g2.drawString(gp.t(boughtCard.getArt().getTextbaustein()), gp.p(17.5), gp.p(20));
        }
    }
}
