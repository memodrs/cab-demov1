package com.cab;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.configs.Positions;

public class Shop {
    GamePanel gp;  
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

    public Shop(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        switchState(shopState);
        gp.gameState = gp.shopState;
    }

    private void switchState(int state) {
        selectedIdx = 0;
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
            switchState(showBoughtCardState);
        } else {
            switchState(shopState);
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
        if (gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.blockBtn) {
				gp.blockBtn = true;
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
                        artWantedToBuy = getArtOfSelectedIdx();
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
        g2.setFont(Main.v.brushedFont25);
        g2.setColor(Color.WHITE);
        g2.drawString("Punkte", Positions.tileSizeRight4, Positions.tileSize);
        g2.drawString("" + gp.player.punkte, Positions.tileSizeRight2, Positions.tileSize);
        g2.setFont(Main.v.brushedFont36);
        g2.setColor(Color.red);
        g2.drawString("Shop", Positions.tileSize, Positions.tileSize2);

        if (currentState == shopState) {
            g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Mensch),       Positions.tileSize,   Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
            g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Tier),         Positions.tileSize6,  Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
            g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Fabelwesen),   Positions.tileSize11, Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
            g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Nachtgestalt), Positions.tileSize16, Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
            g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Segen),        Positions.tileSize21, Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
            g2.drawImage(gp.imageLoader.getBoosterForArt(Art.Fluch),        Positions.tileSize26, Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
    
            drawHoverOnBooster(g2);
    
            g2.setColor(Color.WHITE);
            g2.drawString(getArtOfSelectedIdx() + "-Pack Preis: " + getPreisForArt(getArtOfSelectedIdx()), Positions.tileSize, Positions.screenHalfHeight);
    
            g2.setColor(Color.RED);
            if (showMsgBesitztAlleKartenAusPack) {
                g2.drawString("Du Besitzt bereits alle Karten aus diesem Pack", Positions.tileSize, Positions.screenHalfHeightTileSize2);
            } else if (showMsgZuWenigPunkte) {
                g2.drawString("Dein Punktestand ist zu gering, Punktestand: " + gp.player.punkte, Positions.tileSize, Positions.screenHalfHeightTileSize2);
            }
        } else if (currentState == askToBuyState) {
            g2.drawImage(gp.imageLoader.getBoosterForArt(artWantedToBuy), Positions.tileSize, Positions.tileSize3, Positions.tileSize7, Positions.tileSize12, null);
            g2.drawString(artWantedToBuy + "-Pack", Positions.tileSize10, Positions.tileSize4);
            g2.setColor(Color.WHITE);
            g2.drawString("Bist du dir sicher dass du diese Pack für " + getPreisForArt(artWantedToBuy) + " kaufen willst?", Positions.tileSize10, Positions.tileSize6);

            if (selectedIdx == 0) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString("Ja", Positions.tileSize10, Positions.tileSize8);

            if (selectedIdx == 1) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString("Nein", Positions.tileSize13, Positions.tileSize8);
        } else if (currentState == showBoughtCardState) {
            g2.setColor(Color.ORANGE);
            g2.drawString("Neue Karte erhalten", Positions.tileSize3, Positions.tileSize4);
            g2.drawImage(boughtCard.image, Positions.tileSize3, Positions.tileSize5, Positions.tileSize9, Positions.tileSize14, null);
            g2.drawImage(gp.imageLoader.animHolo.get(), Positions.tileSize3, Positions.tileSize5, Positions.tileSize9, Positions.tileSize14, null);
            g2.setFont(Main.v.brushedFont36);
            g2.setColor(Color.WHITE);
            g2.drawString(boughtCard.name, Positions.tileSize14, Positions.tileSize6);
            g2.drawImage(gp.imageLoader.getArtIconForArt(boughtCard.art, true), Positions.tileSize14, Positions.tileSize7, Positions.tileSize2, Positions.tileSize2, null);
            g2.drawString(boughtCard.art.toString(), Positions.tileSize17, Positions.tileSize8);

        }
    }

    private void drawHoverOnBooster(Graphics2D g2) {
        if (currentState == shopState) {
            int x = 0;

            switch (selectedIdx) {
                case 0:
                    x = Positions.tileSize;
                    break;
                case 1:
                    x = Positions.tileSize6;
                    break;
                case 2:
                    x = Positions.tileSize11;
                    break;
                case 3:
                    x = Positions.tileSize16;
                    break;
                case 4:
                    x = Positions.tileSize21;
                    break;
                case 5:
                    x = Positions.tileSize26;
                    break;
                default:
                    break;
            }

            g2.drawImage(gp.imageLoader.boosterHover, x, Positions.tileSize3, Positions.tileSize4, Positions.tileSize6, null);
        }

    }
}
