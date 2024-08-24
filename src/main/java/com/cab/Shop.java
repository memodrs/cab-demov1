package com.cab;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;

public class Shop {
    GamePanel gp;
    List<Integer> cardListIds = new ArrayList<Integer>();
    int selectedIdx;
    int currentState;
    int shopState = 0;
    int askToBuyState = 1;
    int showBoughtCardState = 2;
    int noCardsInShopState = 3;

    //Draw
    int iconArtSize;
    int iconArtMenschX, iconArtTierX, iconArtFabelwesenX, iconArtNachtgestaltX, iconArtSegenX, iconArtFluchX;

    public Shop(GamePanel gp) {
        this.gp = gp;

        iconArtSize = gp.tileSize * 2;
        iconArtMenschX = gp.tileSize;
        iconArtTierX = gp.tileSize + iconArtSize;
        iconArtFabelwesenX = gp.tileSize + iconArtSize * 2;
        iconArtNachtgestaltX = gp.tileSize + iconArtSize * 3;
        iconArtSegenX = gp.tileSize + iconArtSize * 4;
        iconArtFluchX = gp.tileSize + iconArtSize * 5;
    }

    public void start() {
        selectedIdx = 0;
        for (int i = 0; i < 30; i++) {
            addCardToListIfPlayerHasNot(i);
        }
        if (cardListIds.size() > 0) {
            currentState = shopState;
        } else {
            currentState = noCardsInShopState;
        }
        gp.gameState = gp.shopState;
    }

    private void addCardToListIfPlayerHasNot(int id) {
        if (gp.player.stapel.contains(id) || gp.player.truhe.contains(id)) {
            return;
        } else {
            cardListIds.add(id);
        }
    }

    public void update() {
        if (gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.blockBtn) {
				gp.blockBtn = true;
                if (gp.keyH.leftPressed) {
                    if (currentState == shopState) {
                        if (selectedIdx > 0) {
                            selectedIdx--;
                        }
                    }
                } else if (gp.keyH.rightPressed) {
                    if (currentState == shopState) {
                        if (selectedIdx < 5) {
                            selectedIdx++;
                        }
                    }
                } else if (gp.keyH.qPressed) {
                    if (currentState == shopState) {
                        gp.gameState = gp.hauptmenuState;
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (currentState == noCardsInShopState) {
            g2.setFont(Main.v.brushedFont20);
            g2.setColor(Color.white);
            g2.drawString("Es sind keine Karten im Shop erhältlich", Main.screenHalfWidth, Main.screenHalfHeight);
        } else {
            g2.setFont(Main.v.brushedFont36);
            g2.setColor(Color.red);
            g2.drawString("Shop", gp.tileSize * 2, gp.tileSize * 2);
            g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Mensch, selectedIdx == 0), iconArtMenschX, Main.screenHalfHeight, iconArtSize, iconArtSize, null);
            g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Tier, selectedIdx == 1), iconArtTierX, Main.screenHalfHeight, iconArtSize, iconArtSize, null);
            g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Fabelwesen, selectedIdx == 2), iconArtFabelwesenX, Main.screenHalfHeight, iconArtSize, iconArtSize, null);
            g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Nachtgestalt, selectedIdx == 3), iconArtNachtgestaltX, Main.screenHalfHeight, iconArtSize, iconArtSize, null);
            g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Segen, selectedIdx == 4), iconArtSegenX, Main.screenHalfHeight, iconArtSize, iconArtSize, null);
            g2.drawImage(gp.imageLoader.getArtIconForArt(Art.Fluch, selectedIdx == 5), iconArtFluchX, Main.screenHalfHeight, iconArtSize, iconArtSize, null);
        }
    }
}
