package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
        gp.playMusic(4);
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
                        gp.mainMenu.start();
                    } else if (currentState == askToBuyState) {
                        switchState(shopState);
                    }  else if (currentState == showBoughtCardState) {
                        switchState(shopState);
                    }
                }
                gp.playSE(1);
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.shopBackgroundImage, 0, 0, Positions.screenWidth, Positions.tileSize16, null);

        g2.setFont(Main.v.brushedFont36);
        g2.setColor(Color.RED);
        g2.drawString(gp.t("punkte"), Positions.tileSize26, Positions.tileSizeBottom3Point5);
        g2.drawString("" + gp.player.punkte, Positions.tileSize30, Positions.tileSizeBottom3Point5);
        g2.setFont(Main.v.brushedFont36);
        g2.setColor(Color.red);
        g2.drawString(gp.t("shop"), Positions.tileSize, Positions.tileSizeBottom3Point5);

        if (currentState == shopState) {
            for (int i = 0; i < booster.size(); i++) {
                AffineTransform originalTransform = g2.getTransform(); // Original transform sichern
                AffineTransform transform = new AffineTransform();    // Neue Transform-Instanz
            
                int x = xPositionsBooster.get(i); // X-Position bleibt unverändert
                int y = (selectedIdx == i) ? Positions.tileSize3 : Positions.tileSize3Point6;
                y = i % 2 == 0? y + Positions.tileSize0Point5 : y - Positions.tileSize0Point5;
            
                // Falls das Bild geneigt werden soll
                if (selectedIdx == i) {
                    double angle = (i % 2 == 0) ? Math.toRadians(-5) : Math.toRadians(5); // Neigung abhängig von Parität
                    // Mittelpunkt für die Rotation berechnen
                    int centerX = x + Positions.tileSize2;
                    int centerY = y + Positions.tileSize3;
            
                    // Transformation: zuerst verschieben, dann drehen
                    transform.translate(centerX, centerY); // Mittelpunkt auf die Rotation zentrieren
                    transform.rotate(angle); // Neigung anwenden
                    transform.translate(-centerX, -centerY); // Verschiebung zurücksetzen
                }
            
                g2.setTransform(transform); // Transform auf Grafikobjekt anwenden
            
                // Bild zeichnen
                g2.drawImage(gp.imageLoader.getBoosterForArt(booster.get(i)), x, y, Positions.tileSize4, Positions.tileSize6, null);
            
                // Hover-Bild nur wenn `selectedIdx == i`
                if (selectedIdx == i) {
                    g2.drawImage(gp.imageLoader.boosterHover, x, y, Positions.tileSize4, Positions.tileSize6, null);
                }
            
                // Originale Transformation wiederherstellen
                g2.setTransform(originalTransform);
            }
            
            

            g2.setColor(Color.WHITE);
            g2.drawString(booster.get(selectedIdx) + gp.t("packPreis") + getPreisForArt(booster.get(selectedIdx)), Positions.tileSize, Positions.tileSizeBottom2);
    
            g2.setColor(Color.RED);
            if (showMsgBesitztAlleKartenAusPack) {
                g2.drawString(gp.t("alleKartenBesitzt"), Positions.tileSize, Positions.tileSize2);
            } else if (showMsgZuWenigPunkte) {
                g2.drawString(gp.t("zuWenigPunkte") + gp.player.punkte, Positions.tileSize, Positions.tileSize2);
            }
        } else if (currentState == askToBuyState) {
            g2.drawImage(gp.imageLoader.getBoosterForArt(artWantedToBuy), Positions.tileSize16, Positions.tileSize2Point5, Positions.tileSize7, Positions.tileSize12, null);

            g2.drawString(artWantedToBuy + "-Pack", Positions.tileSize4, Positions.tileSize19);
            g2.setColor(Color.WHITE);
            g2.drawString(gp.t("packKaufBestaetigung") + getPreisForArt(artWantedToBuy) + gp.t("kaufenWollen"), Positions.tileSize, Positions.tileSize21);

            if (selectedIdx == 0) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(gp.t("ja"), Positions.tileSize26, Positions.tileSize21);

            if (selectedIdx == 1) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(gp.t("nein"), Positions.tileSize28, Positions.tileSize21);
        } else if (currentState == showBoughtCardState) {
            g2.setColor(Color.ORANGE);
            g2.drawString(gp.t("neueKarteErhalten"), Positions.tileSize4, Positions.tileSize19);
            g2.drawImage(boughtCard.getImage(), Positions.tileSize16, Positions.tileSize, Positions.tileSize7, Positions.tileSize12, null);
            g2.drawImage(gp.imageLoader.animHolo.get(), Positions.tileSize16, Positions.tileSize, Positions.tileSize7, Positions.tileSize12, null);
            g2.setFont(Main.v.brushedFont36);
            g2.setColor(Color.WHITE);
            g2.drawString(boughtCard.getName(), Positions.tileSize4, Positions.tileSize21);
            g2.drawImage(gp.imageLoader.getArtIconForArt(boughtCard.getArt(), true), Positions.tileSize15, Positions.tileSize19, Positions.tileSize2, Positions.tileSize2, null);
            g2.drawString(boughtCard.getArt().getTextbaustein(), Positions.tileSize17Point5, Positions.tileSize20);
        }
    }
}
