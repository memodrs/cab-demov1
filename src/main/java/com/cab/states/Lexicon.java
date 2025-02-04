package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.cab.GamePanel;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.configs.Colors;


public class Lexicon extends GameState {
    int selectedIdx;
    int state;

    int currentPage;
    int start; 
    int end;

    int totalCards;
    int totalPages;

    List<Integer> cardsInBesitz;
    List<Integer> allCardIds;

    final int numberOfPages = 20;
    final int onPageState = 0;

    GamePanel gp;

    public Lexicon(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        currentPage = 0;
        start = 0;
        end = numberOfPages;
        selectedIdx = 0;
        state = onPageState;

        cardsInBesitz = new ArrayList<>();
        cardsInBesitz.addAll(gp.player.stapel);
        cardsInBesitz.addAll(gp.player.truhe);
        
        allCardIds = gp.cardLoader.allCardIds;
        totalCards =  gp.cardLoader.allCardIds.size();
        totalPages = (int) Math.ceil((double) totalCards / numberOfPages);

        gp.switchState(gp.lexikonState);
        gp.playMusic(4);
    }
    
    @Override
    public void update() {
        if (gp.keyH.upPressed) {
            if (selectedIdx > currentPage * numberOfPages) {
                selectedIdx--;
            }
        } else if (gp.keyH.downPressed) {
            if (selectedIdx < end - 1) {
                selectedIdx++;
            } 
        } else if (gp.keyH.leftPressed) {
            if (currentPage > 0) {
                currentPage--;
                start = currentPage * numberOfPages;
                end = numberOfPages * currentPage + numberOfPages;
                selectedIdx = currentPage * numberOfPages;
            }  
        } else if (gp.keyH.rightPressed) {
            if (currentPage < totalPages - 1) {
                currentPage++;
                start = start + numberOfPages;
                if (currentPage == totalPages - 1) {
                    int remainder = totalCards % numberOfPages;
                    end = (remainder == 0) ? end + numberOfPages : end + remainder;
                } else {
                    end = end + numberOfPages;
                }
                selectedIdx = currentPage * numberOfPages;
            }
        } else if (gp.keyH.qPressed) {
            gp.mainMenu.start();
        }
        gp.playSE(1);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, gp.p(2), 0, gp.screenWidth, gp.screenHeight, null);
        gp.drawLib.drawNavigationLeftArrow(g2, 0, gp.p(20.5), currentPage == 0);
        gp.drawLib.drawNavigationRightArrow(g2, gp.p(1), gp.p(20.5), currentPage == totalPages -1);
        g2.setColor(Color.BLACK);
        g2.setFont(gp.font(15));
        int idx = 0;
        for (int i = start; i < end; i++) {
            gp.drawLib.drawArrowOnState(g2,  -gp.p(0.5), gp.p(1) * idx + gp.p(0.6), true, selectedIdx == i);
            Image image = selectedIdx == i? gp.imageLoader.iconPageSelected : gp.imageLoader.iconPage;
            int x = selectedIdx == i? gp.p(1) : gp.p(0.8);
            g2.drawImage(image, x, idx * gp.p(1) + gp.p(1), gp.p(1), gp.p(1), null);
            g2.drawString(i + 1 + "", gp.p(1.3), idx * gp.p(1) + gp.p(1.5));

            if (!cardsInBesitz.contains(gp.cardLoader.allCardIds.get(i))) {
                g2.setPaint(Colors.gardianSelectFromGrave);
                g2.fillRect(gp.p(1), idx * gp.p(1) + gp.p(1), gp.p(1), gp.p(1));
            }
            idx++;
        }

        if (!gp.keyH.keyPressed) {
            Card card = gp.cardLoader.getCard(allCardIds.get(selectedIdx));
            g2.drawImage(gp.imageLoader.book, gp.p(4), gp.p(1), gp.p(29), gp.p(21), null);
            g2.setColor(Color.BLACK);
            g2.setFont(gp.font(39));
            gp.drawLib.drawHover(g2,  gp.p(9.4), gp.p(6.6), gp.p(4), gp.p(0.24), true);
            g2.drawString(card.getName() + "", gp.p(9.4), gp.p(6.5));
            g2.setColor(Color.GRAY);
            g2.drawImage(card.getImage(), gp.p(20), gp.p(7.2), gp.p(5), gp.p(8), null);
            g2.drawString(selectedIdx + 1 + "", gp.p(9), gp.p(17.5));

            if (!card.isSpell()) {
                g2.drawImage(gp.imageLoader.iconHeart, gp.p(9.5), gp.p(8), gp.p(1), gp.p(1), null);
                g2.drawImage(gp.imageLoader.iconAtk, gp.p(9.5), gp.p(9.1), gp.p(1), gp.p(1), null);
            }

            if (card.getStatus() != Status.Default) {
                g2.setColor(Colors.darkBlueColor);
                g2.setFont(gp.font(20));
                g2.drawImage(gp.imageLoader.getStatusImage(card.getStatus(), false), gp.p(20), gp.p(16.3), gp.p(1.2), gp.p(1.2), null);
                g2.drawString(gp.t(card.getStatus().getTextbaustein()), gp.p(20), gp.p(16));
            }

            g2.setColor(Colors.getColorForArt(card.getArt()));
            g2.setFont(gp.font(30));
            g2.drawString(gp.t(card.getArt().getTextbaustein()), gp.p(12.8), gp.p(9.9));
            g2.drawImage(gp.imageLoader.getArtIconForArt(card.getArt(), true), gp.p(12.6), gp.p(7.5), gp.p(1.7), gp.p(1.7), null);

            g2.setFont(gp.font(20));
            
            if (!cardsInBesitz.contains(allCardIds.get(selectedIdx))) {
                g2.drawImage(gp.imageLoader.cardBackgroundImage, gp.p(20), gp.p(7.2), gp.p(5), gp.p(8), null);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(gp.font(30));

                if (!card.isSpell()) {
                    g2.drawString("? ?",  gp.p(11), gp.p(8.7));
                }
                g2.drawString("? ?",  gp.p(11), gp.p(9.8));

                g2.setColor(Color.BLACK);
                g2.drawString("? ? ? ? ?", gp.p(11), gp.p(13));

            } else {
                g2.setColor(Color.darkGray);
                g2.setFont(gp.font(30));

                if (card.isSpell()) {
                    g2.drawString(gp.t("kosten"), gp.p(10), gp.p(8.7));
                    g2.drawString(card.getKosten() + "",  gp.p(11), gp.p(9.5));
                } else {
                    g2.drawString(card.getLife() + "",  gp.p(11), gp.p(8.7));
                    g2.drawString(card.getAtk() + "",  gp.p(11), gp.p(9.8));
                }

                g2.setFont(gp.font(20));
                g2.setColor(Color.ORANGE);
                g2.drawString(gp.t("effekt"), gp.p(9.4), gp.p(12));
                g2.setColor(Color.BLACK);
                gp.drawLib.drawStringWithNewLines(g2, card.getBeschreibung(), gp.p(11), gp.p(12.6));
            }
        } else {
            g2.drawImage(gp.imageLoader.book, gp.p(4.1), gp.p(1), gp.p(29), gp.p(21), null);
        }
    }
}
