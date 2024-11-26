package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Card;
import com.cab.configs.Positions;

public class Lexikon {
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

    public Lexikon(GamePanel gp) {
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

        gp.gameState = gp.lexikonState; 
    }
    
    public void update() {
		if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.fPressed || gp.keyH.qPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;
				if (gp.keyH.fPressed) {

				} else if (gp.keyH.upPressed) {
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
                    gp.gameState = gp.hauptmenuState; 
				}
				gp.playSE(4);
			}
		} 
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gp.imageLoader.genersichBG, Positions.tileSize2, 0, Positions.screenWidth, Positions.screenHeight, null);
        if (currentPage == 0) {

        }
        Image leftArrow = currentPage == 0? gp.imageLoader.navigationArrowLeftDisabled : gp.imageLoader.navigationArrowLeft;
        Image rightArrow = currentPage == totalPages - 1? gp.imageLoader.navigationArrowRightDisabled : gp.imageLoader.navigationArrowRight;

        g2.drawImage(leftArrow, 0, 0, Positions.tileSize1Point4, Positions.tileSize1Point4, null);
        g2.drawImage(rightArrow, Positions.tileSize, 0, Positions.tileSize1Point4, Positions.tileSize1Point4, null);

        g2.setColor(Color.BLACK);
        g2.setFont(Main.v.brushedFont15);
        int idx = 0;
        for (int i = start; i < end; i++) {
            if (selectedIdx == i) {
                g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize0Point05, Positions.tileSize * idx + Positions.tileSize, Positions.tileSize, Positions.tileSize, null);
            }
            Image image = selectedIdx == i? gp.imageLoader.iconPageSelected : gp.imageLoader.iconPage;
            g2.drawImage(image, Positions.tileSize, idx * Positions.tileSize + Positions.tileSize, Positions.tileSize, Positions.tileSize, null);
            g2.drawString(i + 1 + "", Positions.tileSize1Point4, idx * Positions.tileSize + Positions.tileSize1Point5);

            if (!cardsInBesitz.contains(gp.cardLoader.allCardIds.get(i))) {
                g2.setPaint(Main.v.colorGardianSelectFromGrave);
                g2.fillRect(Positions.tileSize, idx * Positions.tileSize + Positions.tileSize, Positions.tileSize, Positions.tileSize);
            }
            idx++;
        }

        if (!gp.keyH.blockBtn) {
            Card card = gp.cardLoader.getCard(allCardIds.get(selectedIdx));

            g2.drawImage(gp.imageLoader.book, Positions.tileSize4, Positions.tileSize, Positions.tileSize29, Positions.tileSize21, null);
            g2.setColor(Color.BLACK);
            g2.setFont(Main.v.brushedFont36);
            g2.drawString(card.name + "", Positions.tileSize9Point4, Positions.tileSize6Point5);
            g2.setColor(Color.DARK_GRAY);
            g2.drawString("Zusatzinfo", Positions.tileSize20, Positions.tileSize6Point5);

            g2.setPaint(Main.v.colorGardianSelectFromGrave);
            g2.fillRoundRect(Positions.tileSize9Point4, Positions.tileSize7Point1, Positions.tileSize3Point2, Positions.tileSize5Point2, 20, 20);
            g2.drawImage(card.image, Positions.tileSize9Point5, Positions.tileSize7Point2, Positions.tileSize3, Positions.tileSize5, null);
            
            g2.drawImage(gp.imageLoader.iconHeart, Positions.tileSize13, Positions.tileSize8, Positions.tileSize, Positions.tileSize, null);
            g2.drawImage(gp.imageLoader.iconAtk, Positions.tileSize13, Positions.tileSize9Point1, Positions.tileSize, Positions.tileSize, null);

            g2.setColor(gp.getColorForArt(card.art));
            g2.setFont(Main.v.brushedFont30);
            g2.drawString(card.art.toString(), Positions.tileSize13, Positions.tileSize11);
            g2.drawImage(gp.imageLoader.getArtIconForArt(card.art, true), Positions.tileSize15, Positions.tileSize6, Positions.tileSize2, Positions.tileSize2, null);

            g2.setFont(Main.v.brushedFont20);

            g2.setColor(Color.ORANGE);
            g2.drawString("Effekt:",  Positions.tileSize9Point5, Positions.tileSize13Point5);

            if (!cardsInBesitz.contains(allCardIds.get(selectedIdx))) {
                g2.setPaint(Main.v.colorGardianSelectFromGrave);
                g2.fillRect(Positions.tileSize9Point5, Positions.tileSize7Point2, Positions.tileSize3, Positions.tileSize5);

                g2.setColor(Color.RED);
                g2.drawString("? ? ?",  Positions.tileSize14Point55, Positions.tileSize8Point7);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString("? ? ?",  Positions.tileSize14Point55, Positions.tileSize9Point8);
                g2.setColor(Color.BLACK);
                g2.drawString("? ? ? ? ?", Positions.tileSize11, Positions.tileSize12Point8);
                g2.setColor(Color.YELLOW);
                g2.drawString("Zusatzinfo (IN ARBEIT)", Positions.tileSize20, Positions.tileSize15);
            } else {
                g2.setColor(Color.RED);
                g2.drawString(card.def + "",  Positions.tileSize14Point55, Positions.tileSize8Point7);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(card.atk + "",  Positions.tileSize14Point55, Positions.tileSize9Point8);

                g2.setColor(Color.BLACK);
                g2.setFont(Main.v.brushedFont20);
                int y = Positions.tileSize12Point8;
                for (String line : card.beschreibung.split("\n")) {
					g2.drawString(line, Positions.tileSize11, y);
    				y += (int) (gp.tileSize * 0.7) ;
    			}

                g2.setColor(Color.YELLOW);
                g2.drawString("Zusatzinfo (IN ARBEIT)", Positions.tileSize20, Positions.tileSize15);

            }
        }
    }
}