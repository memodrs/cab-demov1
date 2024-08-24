package com.cab;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cab.card.Art;

public class Shop {
    GamePanel gp;  
    Art artWantedToBuy;
    int idBoughtCard;
    
    int selectedIdx;
   
    int currentState;
    int shopState = 0;
    int askToBuyState = 1;
    int showBoughtCardState = 2;

    boolean showMsgBesitztAlleKartenAusPack = false;
    boolean showMsgZuWenigPunkte = false;

    //Draw
    int playerPunkteX, playerPunkteY;
    int playerPunkteBeschreibungX;
    int boosterWidth, boosterHeigth;
    int boosterY;
    int iconArtMenschX, iconArtTierX, iconArtFabelwesenX, iconArtNachtgestaltX, iconArtSegenX, iconArtFluchX;
    int preisY;
    int msgY;


    public Shop(GamePanel gp) {
        this.gp = gp;

        
        playerPunkteY = gp.tileSize;
        playerPunkteBeschreibungX = Main.screenWidth - gp.tileSize * 4;

        debugSetup();

    }

    private void debugSetup() {
        playerPunkteX = Main.screenWidth - gp.tileSize * 2;
        boosterWidth = gp.cardWidth * 2;
        boosterHeigth = gp.cardHeight * 2;
        boosterY = gp.tileSize * 3;

        iconArtMenschX = gp.tileSize;
        iconArtTierX = gp.tileSize + boosterWidth + gp.tileSize;
        iconArtFabelwesenX = gp.tileSize + boosterWidth * 2 + gp.tileSize * 2;
        iconArtNachtgestaltX = gp.tileSize + boosterWidth * 3 + gp.tileSize * 3;
        iconArtSegenX = gp.tileSize + boosterWidth * 4 + gp.tileSize * 4;
        iconArtFluchX = gp.tileSize + boosterWidth * 5 + gp.tileSize * 5;
        preisY = Main.screenHalfHeight;
        msgY = preisY + gp.tileSize * 2;
    }

    public void start() {
        selectedIdx = 0;
        currentState = shopState;
        gp.gameState = gp.shopState;
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
    
            gp.player.truhe.add(idBoughtCard);
            gp.player.punkte = gp.player.punkte - getPreisForArt(artWantedToBuy);
        } else {
            showMsgBesitztAlleKartenAusPack = true;
        }
    }

    private Art getArtOfSelectedIdx() {
        switch (selectedIdx) {
            case 0: return Art.Mensch;
            case 1: return Art.Tier;
            case 2: return Art.Fabelwesen;
            case 3: return Art.Nachtgestalt;
            case 4: return Art.Segen;
            case 5: return Art.Fluch;
            default: throw new Error("Keine Art zum selectedIdx gefunden");
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
        if (gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed || gp.keyH.enterPressed) {
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
                } else if (gp.keyH.fPressed) {
                    showMsgBesitztAlleKartenAusPack = false;
                    showMsgZuWenigPunkte = false;
                    artWantedToBuy = getArtOfSelectedIdx();
                    if (gp.player.punkte >= getPreisForArt(artWantedToBuy)) {
                        buy();
                    } else {
                        showMsgZuWenigPunkte = true;
                    }
                } else if (gp.keyH.qPressed) {
                    if (currentState == shopState) {
                        gp.gameState = gp.hauptmenuState;
                    }
                }

                else if (gp.keyH.enterPressed) {
                    debugSetup();
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setFont(Main.v.brushedFont20);
        g2.setColor(Color.WHITE);
        g2.drawString("Punkte", playerPunkteBeschreibungX, playerPunkteY);
        g2.drawString("" + gp.player.punkte, playerPunkteX, playerPunkteY);
        g2.setFont(Main.v.brushedFont36);
        g2.setColor(Color.red);
        g2.drawString("Shop", gp.tileSize * 2, gp.tileSize * 2);
        g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Mensch),       iconArtMenschX,       boosterY, boosterWidth, boosterHeigth, null);
        g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Tier),         iconArtTierX,         boosterY, boosterWidth, boosterHeigth, null);
        g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Fabelwesen),   iconArtFabelwesenX,   boosterY, boosterWidth, boosterHeigth, null);
        g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Nachtgestalt), iconArtNachtgestaltX, boosterY, boosterWidth, boosterHeigth, null);
        g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Segen),        iconArtSegenX,        boosterY, boosterWidth, boosterHeigth, null);
        g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Fluch),        iconArtFluchX,        boosterY, boosterWidth, boosterHeigth, null);

        drawHoverOnBooster(g2);

        g2.setColor(Color.WHITE);
        g2.drawString(getArtOfSelectedIdx() + "-Pack Preis: " + getPreisForArt(getArtOfSelectedIdx()), gp.tileSize, preisY);

        g2.setColor(Color.RED);
        if (showMsgBesitztAlleKartenAusPack) {
            g2.drawString("Du Besitzt bereits alle Karten aus diesem Pack", gp.tileSize, msgY);
        } else if (showMsgZuWenigPunkte) {
            g2.drawString("Dein Punktestand ist zu gering, Punktestand: " + gp.player.punkte, gp.tileSize, msgY);
        }
    }

    private void drawHoverOnBooster(Graphics2D g2) {
        if (currentState == shopState) {
            int x = 0;

            switch (selectedIdx) {
                case 0:
                    x = iconArtMenschX;
                    break;
                case 1:
                    x = iconArtTierX;
                    break;
                case 2:
                    x = iconArtFabelwesenX;
                    break;
                case 3:
                    x = iconArtNachtgestaltX;
                    break;
                case 4:
                    x = iconArtSegenX;
                    break;
                case 5:
                    x = iconArtFluchX;
                    break;
                default:
                    break;
            }

            g2.drawImage(gp.imageLoader.boosterHover, x, boosterY, boosterWidth, boosterHeigth, null);
        }

    }
}
