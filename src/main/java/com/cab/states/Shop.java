package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.configs.Positions;

public class Shop {
    GamePanel gp;  
    List<Art> booster = new ArrayList<Art>();
    Art artWantedToBuy;
    int idBoughtCard;
    Card boughtCard;
    
    int selectedIdx;
   
    int currentState;
    int shopState = 0;
    int askToBuyState = 1;
    int showBoughtCardState = 2;

    boolean showMsgBesitztAlleKartenAusPack = false;
    boolean showMsgZuWenigPunkte = false;

    List<Integer> xPositionsBooster = new ArrayList<>();

    public Shop(GamePanel gp) {
        this.gp = gp;

        booster.add(Art.Mensch);
        booster.add(Art.Tier);
        booster.add(Art.Fabelwesen);
        booster.add(Art.Nachtgestalt);
        booster.add(Art.Segen);
        booster.add(Art.Fluch);

        xPositionsBooster.add(Positions.tileSize2);
        xPositionsBooster.add(Positions.tileSize8);
        xPositionsBooster.add(Positions.tileSize13);
        xPositionsBooster.add(Positions.tileSize18);
        xPositionsBooster.add(Positions.tileSize23);
        xPositionsBooster.add(Positions.tileSize28);
    }           

    public void start() {
        artWantedToBuy = null;
        switchState(shopState);
        gp.gameState = gp.shopState;
    }

    private void switchState(int state) {
        if (state == shopState && artWantedToBuy != null) {
            selectedIdx = booster.indexOf(artWantedToBuy);
        } else {
            selectedIdx = 0;
        }
        currentState = state;
        showMsgZuWenigPunkte = false;
        showMsgBesitztAlleKartenAusPack = false;
    }

    private void buy() {
        List<Integer> cardPool = new ArrayList<>();
        for (int id : gp.cardLoader.allCardIds) {
            if (gp.cardLoader.getCard(id).art == artWantedToBuy && !gp.player.stapel.contains(id) && !gp.player.truhe.contains(id)) {
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
            showMsgBesitztAlleKartenAusPack = true;
        }
    }

    private int getPreisForArt(Art art) {
        if (art == Art.Segen || art == Art.Fluch) {
            return 60;
        } else {
            return 40;
        }
    }

    public void update() {
        if (gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
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
                        showMsgBesitztAlleKartenAusPack = false;
                        showMsgZuWenigPunkte = false;
                        artWantedToBuy = booster.get(selectedIdx);
                        if (gp.player.punkte >= getPreisForArt(artWantedToBuy)) {
                            switchState(askToBuyState);
                        } else {
                            showMsgZuWenigPunkte = true;
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
                        gp.gameState = gp.hauptmenuState;
                    } else if (currentState == askToBuyState) {
                        switchState(shopState);
                    }  else if (currentState == showBoughtCardState) {
                        switchState(shopState);
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.shopBackgroundImage, 0, 0, Positions.screenWidth, Positions.tileSize16, null);

        g2.setFont(Main.v.brushedFont36);
        g2.setColor(Color.RED);
        g2.drawString("Punkte", Positions.tileSize26, Positions.tileSizeBottom3Point5);
        g2.drawString("" + gp.player.punkte, Positions.tileSize30, Positions.tileSizeBottom3Point5);
        g2.setFont(Main.v.brushedFont36);
        g2.setColor(Color.red);
        g2.drawString("Shop", Positions.tileSize, Positions.tileSizeBottom3Point5);

        if (currentState == shopState) {
            for (int i = 0; i < booster.size(); i++) {
                if (selectedIdx == i) {
                    g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(i)), xPositionsBooster.get(i), Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
                    g2.drawImage(gp.imageLoader.boosterHover, xPositionsBooster.get(i), Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
                } else {
                    g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(i)), xPositionsBooster.get(i), Positions.tileSize3Point6, Positions.tileSize4, Positions.tileSize6, null);
                }
            }

            g2.setColor(Color.WHITE);
            g2.drawString(booster.get(selectedIdx) + "-Pack Preis: " + getPreisForArt(booster.get(selectedIdx)), Positions.tileSize, Positions.tileSizeBottom2);
    
            g2.setColor(Color.RED);
            if (showMsgBesitztAlleKartenAusPack) {
                g2.drawString("Du Besitzt bereits alle Karten aus diesem Pack", Positions.tileSize, Positions.tileSize2);
            } else if (showMsgZuWenigPunkte) {
                g2.drawString("Dein Punktestand ist zu gering, Punktestand: " + gp.player.punkte, Positions.tileSize, Positions.tileSize2);
            }
        } else if (currentState == askToBuyState) {
            g2.drawImage(gp.imageLoader.getBoosterForArt(artWantedToBuy), Positions.tileSize, Positions.tileSize2Point5, Positions.tileSize7, Positions.tileSize12, null);

            g2.drawString(artWantedToBuy + "-Pack", Positions.tileSize4, Positions.tileSize19);
            g2.setColor(Color.WHITE);
            g2.drawString("Bist du dir sicher dass du diese Pack für " + getPreisForArt(artWantedToBuy) + " kaufen willst?", Positions.tileSize, Positions.tileSize21);

            if (selectedIdx == 0) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString("Ja", Positions.tileSize26, Positions.tileSize21);

            if (selectedIdx == 1) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString("Nein", Positions.tileSize28, Positions.tileSize21);
        } else if (currentState == showBoughtCardState) {
            g2.setColor(Color.ORANGE);
            g2.drawString("Neue Karte erhalten", Positions.tileSize4, Positions.tileSize19);
            g2.drawImage(boughtCard.image, Positions.tileSize, Positions.tileSize3, Positions.tileSize9, Positions.tileSize14, null);
            g2.drawImage(gp.imageLoader.animHolo.get(), Positions.tileSize, Positions.tileSize3, Positions.tileSize9, Positions.tileSize14, null);
            g2.setFont(Main.v.brushedFont36);
            g2.setColor(Color.WHITE);
            g2.drawString(boughtCard.name, Positions.tileSize4, Positions.tileSize21);
            g2.drawImage(gp.imageLoader.getArtIconForArt(boughtCard.art, true), Positions.tileSize15, Positions.tileSize19, Positions.tileSize2, Positions.tileSize2, null);
            g2.drawString(boughtCard.art.toString(), Positions.tileSize17Point5, Positions.tileSize20);

        }
    }
}
