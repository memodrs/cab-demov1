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
import com.cab.draw.AnimImage;
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
    int showAnimState = 2;
    int showBoughtCardState = 3;

    ShakingKoordinaten shakingKoordinatenBooster;
    ShakingKoordinaten shakingKoordinatenBoughtCard;


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

        shakingKoordinatenBooster = new ShakingKoordinaten(gp.p(15), gp.p(2));
        shakingKoordinatenBoughtCard = new ShakingKoordinaten(gp.p(5), gp.p(1));

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
            startOpenAnim();
            gp.playSE(13);
        } else {
            switchState(shopState);
            gp.showMsg("alleKartenBesitzt");
        }
    }

    private int getPreisForArt(Art art) {
        if (art == Art.Segen || art == Art.Fluch) {
            return 40;
        } else {
            return 30;
        }
    }

    private void startOpenAnim() {
        currentState = showAnimState;
        gp.imageLoader.openBooster.start();
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
                if (selectedIdx < booster.size() - 1) {
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

            g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(selectedIdx)), shakingKoordinatenBooster.getX(), shakingKoordinatenBooster.getY(), gp.p(6.5), gp.p(10), null);

            g2.setColor(Color.ORANGE);
            g2.drawString(booster.get(selectedIdx) + gp.t("packPreis") + getPreisForArt(booster.get(selectedIdx)), gp.p(1), gp.p(19));
    
            g2.setColor(Color.RED);
        } else if (currentState == askToBuyState) {
            g2.setColor(Colors.transparentBlack); 
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);  

            g2.drawImage(gp.imageLoader.getBoosterForArt(artWantedToBuy), shakingKoordinatenBooster.getX(), shakingKoordinatenBooster.getY(),  gp.p(8), gp.p(12), null);

            g2.drawString(artWantedToBuy + "-Pack", gp.p(4), gp.p(17));
            g2.setColor(Color.ORANGE);
            g2.drawString(gp.t("packKaufBestaetigung") + getPreisForArt(artWantedToBuy) + gp.t("kaufenWollen"), gp.p(1), gp.p(19));

            g2.setColor(Colors.getColorSelection(0, selectedIdx));
            g2.drawString(gp.t("ja"), gp.p(26), gp.p(19));

            g2.setColor(Colors.getColorSelection(1, selectedIdx));
            g2.drawString(gp.t("nein"), gp.p(28), gp.p(19));

        } else if (currentState == showAnimState) {
            AnimImage openBooster = gp.imageLoader.openBooster;

            if (!openBooster.isRunning) {
                switchState(showBoughtCardState);
                gp.playSE(11);
            } else {
                g2.setColor(Color.BLACK); 
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    
                if (openBooster.animIdx > openBooster.images.length - 4) {
                    g2.setColor(Color.WHITE); 
                    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
                }
                g2.drawImage(gp.imageLoader.getBoosterForArt(artWantedToBuy), gp.p(15), gp.p(1.5),  gp.p(8), gp.p(12), null);
                g2.drawImage(openBooster.get(), gp.p(15.5), gp.p(1.5), gp.p(8), gp.p(12), null);
            }
        } else if (currentState == showBoughtCardState) {
            g2.setColor(Colors.transparentBlack); 
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            
            g2.drawImage(boughtCard.getImage(), shakingKoordinatenBoughtCard.getX(),shakingKoordinatenBoughtCard.getY(), gp.p(7), gp.p(12), null);
            g2.drawImage(gp.imageLoader.animHolo.get(), gp.p(5), gp.p(1), gp.p(7), gp.p(12), null);

            
            g2.setColor(Color.ORANGE);
            g2.drawString(gp.t("neueKarteErhalten"), gp.p(14), gp.p(2));

            g2.setFont(gp.font(38));
            g2.setColor(Colors.gold);
            g2.drawString(boughtCard.getName(), gp.p(14), gp.p(4));
            gp.drawLib.drawHover(g2, gp.p(13.2), gp.p(4.2), gp.p(7), gp.p(0.3), true);

            gp.drawLib.drawHover(g2, gp.p(14), gp.p(5.3), gp.p(8), gp.p(1), true);
            g2.drawImage(gp.imageLoader.getArtIconForArt(boughtCard.getArt(), true), gp.p(14), gp.p(5), gp.p(1.5), gp.p(1.5), null);
            g2.setFont(gp.font(35));
            g2.setColor(Colors.getColorForArt(boughtCard.getArt()));
            g2.drawString(gp.t(boughtCard.getArt().getTextbaustein()), gp.p(16), gp.p(6));

            if (boughtCard.isSpell()) {
                g2.drawImage(gp.imageLoader.getKostenIcon(boughtCard.getArt()), gp.p(14), gp.p(7), gp.p(1.5), gp.p(1.5), null);
                g2.setFont(gp.font(38));
                g2.setColor(Color.RED);
                g2.drawString(boughtCard.getKosten() + "", gp.p(16), gp.p(8));
            } else {
                g2.drawImage(gp.imageLoader.iconAtk, gp.p(14), gp.p(7), gp.p(1.5), gp.p(1.5), null);
                g2.drawImage(gp.imageLoader.iconHeart, gp.p(18), gp.p(7), gp.p(1.5), gp.p(1.5), null);

                g2.setFont(gp.font(38));
                g2.setColor(Colors.orangeYellow);
                g2.drawString(boughtCard.getAtk() + "", gp.p(16), gp.p(8));
                g2.setColor(Color.RED);
                g2.drawString(boughtCard.getLife() + "", gp.p(20), gp.p(8));

                if (boughtCard.getStatus() != null) {
                    g2.drawImage(gp.imageLoader.getStatusImage(boughtCard.getStatus(), false), gp.p(23), gp.p(6), gp.p(1.5), gp.p(1.5), null);
                }
            }
            g2.setFont(gp.font(20));
            g2.setColor(Color.WHITE);
            gp.drawLib.drawStringWithNewLines(g2, boughtCard.getBeschreibung(), gp.p(14), gp.p(9));
        }
    }
}
