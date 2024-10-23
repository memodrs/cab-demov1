package com.cab.cardGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Art;
import com.cab.card.Status;
import com.cab.configs.Positions;
import com.cab.draw.AnimImage;
import com.cab.draw.SelectedCard;

public class CardGameDrawer {
	CardGame cg;
	GamePanel gp;
	String msg = ""; // Anzeige was ist auf dem Board passiert 
	int counter = 0;

	Color colorSelectionOponentCardAnimOne = new Color(200, 0, 0, 100);
	Color colorSelectionOponentCardAnimTwo = new Color(200, 0, 0, 20);
	Color colorSelectionOwnCardAnimOne = new Color(0, 200, 0, 100);
	Color colorSelectionOwnCardAnimTwo = new Color(0, 200, 0, 20);
	Color colorActiveEffektCard = new Color(255, 255, 255, 120);

	AnimImage destroyImage;
	AnimImage destroyImage2;
	AnimImage schadenImage;
	boolean showGameBoard = true;
	boolean showAttackOnCardSelbstzerstoerung = false;
	boolean showAttackOnCardDoppelzerstoerung = false;
	boolean showAttackOnCardZersteorung = false;
	boolean showAttackOnCardSchaden = false;
	boolean showDirectAttack = false;
	boolean showAttackOnSchild = false;


	CardState angreifer;
	CardState verteidiger;

	boolean showEffektCard = false;
	List<CardState> effektCards = new ArrayList<>();
	
	public CardGameDrawer(CardGame cg) {
		this.cg = cg;
		this.gp = cg.gp;
		init();
	}

	public void init() {
		//Angriff
		destroyImage = gp.imageLoader.animDestroy;
		destroyImage2 = gp.imageLoader.animDestroy2;
		schadenImage = gp.imageLoader.animSchaden;
	}
	
	public void showMsg(String msg) {
		this.msg = msg;
	}
	
	public void drawHandPlayer(Graphics2D g2) {
		int numCards = cg.player.handCards.size();
		int middleIndex = numCards / 2;  // Index der mittleren Karte
		double angleStep = Math.toRadians(10);  // Winkel zwischen den Karten (10°)
						
		int x;
		int y;

		for (int i = 0; i < numCards; i++) {
			g2.setColor(Main.v.colorTransparent);
			CardState card = cg.player.handCards.get(i);
			boolean isEffektManualActivatable = cg.isEffektManualActivatable(cg.player, card, cg.effekteMangaer.triggerManualFromHand);

			// Berechne den relativen Winkel zur mittleren Karte
			double angle = (i - middleIndex) * angleStep;
		
			// Berechne die X- und Y-Position auf einem vertikalen Bogen
			x = (int) (Positions.tileSize7 + Math.sin(angle) * Positions.tileSize6);  // X-Position basierend auf dem Winkel
			y = (int) (Positions.tileSize8 + (Positions.tileSize12 - Math.cos(angle) * Positions.tileSize6));  // Y-Position basierend auf dem Winkel
		

			if (!(i == cg.selectedHandCardIdx && (cg.isState(cg.handCardSelectedState) || cg.isState(cg.effektQuestionStateHand)))) {
				// Nur drehen, wenn es nicht die mittlere Karte ist
				if (i != middleIndex) {
					g2.rotate(angle, x + Positions.tileSize4 / 2, y + Positions.tileSize6 / 2);
				}
		
				// Zeichne die Karte an der berechneten Position
				if (cg.isState(cg.handCardState) && i == cg.selectedIdx) {
					g2.drawImage(gp.cardLoader.getCard(card.defaultCard.id).image, x, y - Positions.tileSize4, Positions.tileSize4, Positions.tileSize6, null);
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y - Positions.tileSize4, Positions.tileSize4, Positions.tileSize6, null);

					if (isEffektManualActivatable) {
						g2.drawImage(gp.imageLoader.instractionKeyboardG, x, y - Positions.tileSize6, Positions.tileSize4, Positions.tileSize2, null);
						g2.setColor(Color.WHITE);
						g2.drawString("Effekt aktivieren", x + Positions.tileSize, y - Positions.tileSize4Point68);
					}
				} else {
					g2.drawImage(gp.cardLoader.getCard(card.defaultCard.id).image, x, y, Positions.tileSize4, Positions.tileSize6, null);
					if (isEffektManualActivatable || cg.isSpellActivatable(cg.player, card)) {
						g2.drawImage(card.defaultCard.cardIsPlayable.get(), x, y, Positions.tileSize4, Positions.tileSize6, null);
					}
				}
		
				// Nach dem Zeichnen Rotation zurücksetzen
				if (i != middleIndex) {
					g2.rotate(-angle, x + Positions.tileSize4 / 2, y + Positions.tileSize6 / 2);
				}
			}
		}
		
		

		if (cg.isState(cg.handCardState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize0Point5, Positions.tileSize17, Positions.tileSize3, Positions.tileSize3, null);
		}
	}

	public void drawSelectedCard(CardState card, Graphics2D g2) {
		g2.setColor(Main.v.colorTransparent); 
		g2.drawImage(card.defaultCard.image, Positions.tileSize9, Positions.tileSize7, Positions.tileSize4, Positions.tileSize6, null);
	}
	
	public void drawSelectedCardText(CardState card, Graphics2D g2, String option1, String option2) {
		g2.setFont(Main.v.brushedFont20);
		g2.drawImage(gp.imageLoader.paper04, Positions.tileSize6, Positions.tileSize8Point7, Positions.tileSize3, Positions.tileSize2, null);
		g2.setColor(gp.getColorSelection(0, cg.selectedIdx));
		g2.drawString(option1, Positions.tileSize6Point6, Positions.tileSize10);


		if (option2 != null) {
			g2.drawImage(gp.imageLoader.paper04, Positions.tileSize6, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize2, null);
			g2.setColor(gp.getColorSelection(1, cg.selectedIdx));
			g2.drawString(option2, Positions.tileSize6Point6, Positions.tileSize12);
		}

		if (cg.selectedIdx == 0) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize4, Positions.tileSize9, Positions.tileSize2, Positions.tileSize2, null);
		} else if (cg.selectedIdx == 1) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize4, Positions.tileSize11, Positions.tileSize2, Positions.tileSize2, null);
		}
	}

	public void drawHandCardSelected(Graphics2D g2) {
		if (cg.isState(cg.handCardSelectedState)) {
			CardState card = cg.player.handCards.get(cg.selectedHandCardIdx);
			drawSelectedCard(card, g2);
			drawSelectedCardText(card, g2, "Aufrufen", "Verdecken");

			if (cg.selectedIdx == 1) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, Positions.tileSize9, Positions.tileSize7, Positions.tileSize4, Positions.tileSize6, null);
			}
		} 
	}
	
	public void drawCardEffektQuestion(Graphics2D g2) {
		if (cg.isState(cg.effektQuestionStateBoard) || cg.isState(cg.effektQuestionStateGrave) || cg.isState(cg.effektQuestionStateHand)) {
			CardState card = null;
			if (cg.isState(cg.effektQuestionStateBoard)) {
				card = cg.player.boardCards.get(cg.selectedBoardCardIdx);
			} else if (cg.isState(cg.effektQuestionStateGrave)) {
				card = cg.player.graveCards.get(cg.selectGraveCardIdx);
			} else if (cg.isState(cg.effektQuestionStateHand)) {
				card = cg.player.handCards.get(cg.selectedHandCardIdx);
			}

			drawSelectedCard(card, g2);
			drawSelectedCardText(card, g2, "Aktivieren", null);
		}
	}

	public void drawBoardPlayer(Graphics2D g2) {
		g2.setColor(Main.v.colorTransparent); 
		int y = Positions.tileSize10;

		for (int i = 0; i < cg.player.boardCards.size(); i++) {
			int offsetX = (int) (Positions.tileSize17 + gp.cardWidth * i + Positions.tileSize0Point5 * i);
			CardState card = cg.player.boardCards.get(i);

        	if (card.isHide) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
        	} else {
				g2.drawImage(card.defaultCard.image, offsetX, y, Positions.tileSize2, Positions.tileSize3, null);

				if (cg.isEffektManualActivatable(cg.player, card, cg.effekteMangaer.triggerManualFromBoard)) {
					g2.drawImage(card.defaultCard.cardIsPlayable.get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);

					if (cg.isState(cg.boardState) && cg.selectedIdx == i) {
						g2.drawImage(gp.imageLoader.instractionKeyboardG, offsetX - Positions.tileSize, y - Positions.tileSize2, Positions.tileSize4, Positions.tileSize2, null);
						g2.setColor(Color.WHITE);
						g2.setFont(Main.v.brushedFont15);
						g2.drawString("Effekt aktivieren", offsetX - Positions.tileSize0Point5, y - Positions.tileSize0Point7);
					}
				}

				if (cg.checkIsAttackAlowed(cg.player, i)) {
					g2.drawImage(gp.imageLoader.iconAttackAvailable, offsetX + Positions.tileSize, y + Positions.tileSize2, gp.tileSize, gp.tileSize, null);
				}

				if (cg.isState(cg.effektSelectOwnBoardState)) {
					if (card == cg.activeEffektCard) {
						g2.drawImage(card.defaultCard.cardIsPlayable.get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
					}

					if (cg.activeEffektCard.isCardValidForSelection(card)) {
						g2.drawImage(card.defaultCard.cardSelectGreen.get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
					}
				}
			}

			if (cg.isState(cg.boardState) || cg.isState(cg.effektSelectOwnBoardState)) {
				if (i == cg.selectedIdx) {
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
				}
			}

			int j = 0;
			for (Status s : card.statusSet) {
				g2.drawImage(gp.imageLoader.paper05,  offsetX, y + j * Positions.tileSize0Point52, Positions.tileSize0Point8, Positions.tileSize0Point7, null); 
				g2.drawImage(gp.imageLoader.getStatusImage(s, false), offsetX + Positions.tileSize0Point25, y + Positions.tileSize0Point25 + j * Positions.tileSize0Point5, Positions.tileSize0Point3, Positions.tileSize0Point3, null);
				j++;
			}
		}
		if (cg.isState(cg.boardState) || cg.isState(cg.effektSelectOwnBoardState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize14, y, Positions.tileSize3, Positions.tileSize3, null);
		}
	}

	public void drawBoardCardSelected(Graphics2D g2) {
		if (cg.isState(cg.boardCardSelectedState)) {
			CardState card = cg.player.boardCards.get(cg.selectedBoardCardIdx);
			drawSelectedCard(card, g2);	
			if (card.isHide) {
				drawSelectedCardText(card, g2, "Aufdecken", null);
			} else {
				drawSelectedCardText(card, g2, "Angreifen", null);
			}
		}
	}

	public void drawBoardOponent(Graphics2D g2) {
		g2.setColor(Main.v.colorTransparent); 
		int y = Positions.tileSize6;

		for (int i = 0; i < cg.oponent.boardCards.size(); i++) {
			int offsetX = (int) (Positions.tileSize17 + gp.cardWidth * i + Positions.tileSize0Point5 * i);
			CardState card = cg.oponent.boardCards.get(i);

        	if (card.isHide) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
        	} else {
				g2.drawImage(card.defaultCard.imageReverse, offsetX, y, Positions.tileSize2, Positions.tileSize3, null);

				if (cg.isState(cg.effektSelectOponentBoardState)) {
					if (cg.activeEffektCard.isCardValidForSelection(card)) {
						g2.drawImage(card.defaultCard.cardSelectRed.get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
					}
				}

				if (cg.isState(cg.selectCardToAttackState)) {
					g2.drawImage(card.defaultCard.cardSelectRed.get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
				}
			}

			if (cg.isState(cg.boardOponentState) || cg.isState(cg.effektSelectOponentBoardState) || cg.isState(cg.selectCardToAttackState)) {
				if (i == cg.selectedIdx) {
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
				}
			}

			int j = 0;
			for (Status s : card.statusSet) {
				g2.drawImage(gp.imageLoader.paper05,  offsetX, y + j * Positions.tileSize0Point52, Positions.tileSize0Point8, Positions.tileSize0Point7, null); 
				g2.drawImage(gp.imageLoader.getStatusImage(s, false), offsetX + Positions.tileSize0Point25, y + Positions.tileSize0Point25 + j * Positions.tileSize0Point5, Positions.tileSize0Point3, Positions.tileSize0Point3, null);
				j++;
			}
		}
		if (cg.isState(cg.boardOponentState) || cg.isState(cg.effektSelectOponentBoardState) || cg.isState(cg.selectCardToAttackState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize14, y, Positions.tileSize3, Positions.tileSize3, null);
		}
	}

	public void drawHandOponent(Graphics2D g2) {
		int numCards = cg.oponent.handCards.size();
		int middleIndex = numCards / 2;  // Index der mittleren Karte
		double angleStep = Math.toRadians(10);  // Winkel zwischen den Karten (10°)
							
		int x;
		int y;
	
		for (int i = 0; i < numCards; i++) {
			g2.setColor(Main.v.colorTransparent);
			// Berechne den relativen Winkel zur mittleren Karte
			double angle = (i - middleIndex) * angleStep;
			
			// Berechne die X- und Y-Position auf einem vertikalen Bogen nach unten
			x = (int) (Positions.tileSize30 + Math.sin(angle) * Positions.tileSize6);  // X-Position basierend auf dem Winkel
			y = (int) (Math.cos(angle) - Positions.tileSize2);  // Y-Position nach unten hin basierend auf dem Winkel
			
			// Nur drehen, wenn es nicht die mittlere Karte ist
			if (i != middleIndex) {
				// Umkehre den Winkel, damit die Karten nach unten rotieren
				g2.rotate(-angle, x + Positions.tileSize4 / 2, y + Positions.tileSize6 / 2);
			}
	
			g2.drawImage(gp.imageLoader.cardBackgroundImage, x, y, Positions.tileSize4, Positions.tileSize6, null);
	
			if (i != middleIndex) {
				// Setze die Rotation zurück
				g2.rotate(angle, x + Positions.tileSize4 / 2, y + Positions.tileSize6 / 2);
			}
		}
	}
	
	public void drawGrave(Graphics2D g2, List<CardState> cards, int y) {
		if (cards.size() > 0) {
			CardState card = cards.get(cards.size() - 1);
			g2.drawImage(card.defaultCard.image, Positions.tileSize31, y, Positions.tileSize2, Positions.tileSize3, null);
    		g2.setPaint(Main.v.colorGardianSelectFromGrave);
			g2.fillRect(Positions.tileSize31, y, Positions.tileSize2, Positions.tileSize3);
		}

		if (cg.isState(cg.graveState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), Positions.tileSize31, y, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize28, y, Positions.tileSize3, Positions.tileSize3, null);
		}
	}
	

	public void draw(Graphics2D g2) {
		if (gp.gameState == gp.cardGameState) {
			if (showGameBoard) {
				drawHandOponent(g2);
				drawBoardOponent(g2);
				drawGrave(g2, cg.player.graveCards, Positions.tileSize10);
				drawBoardPlayer(g2);
				drawGrave(g2, cg.oponent.graveCards, Positions.tileSize6);
				drawHandPlayer(g2);
				
				drawHandCardSelected(g2);
				drawBoardCardSelected(g2);
				drawCardEffektQuestion(g2);
			} 
			
			else {
				if (showAttackOnCardSelbstzerstoerung) {
					drawAttackOnCardSelbstzerstoerung(g2);
				} else if (showAttackOnCardDoppelzerstoerung) {
					drawAttackOnCardDoppelzerstoerung(g2);
				} else if (showAttackOnCardZersteorung) {
					drawAttackOnCardZersteorung(g2);
				} else if (showAttackOnCardSchaden) {
					drawAttackOnCardSchaden(g2);
				} else if (showDirectAttack) {
					drawDirectAttack(g2);
				} else if (showAttackOnSchild) {
					drawAttackOnSchild(g2);
				}
				counter++;
			}


		}
	}

	private void switchToGameBoard() {
		counter = 0;
		showAttackOnCardSelbstzerstoerung = false;
		showAttackOnCardDoppelzerstoerung = false;
		showAttackOnCardZersteorung = false;
		showAttackOnCardSchaden = false;
		showDirectAttack = false;
		showGameBoard = true;
	}

	public void showEffektCard(CardState card) {
		effektCards.add(card);
	}
	
	private void drawAttackOnCardZersteorung(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.image, Positions.tileSize8, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

		if (counter < 15) {
			g2.drawImage(verteidiger.defaultCard.image, Positions.tileSize25, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
		} else {
			g2.drawImage(destroyImage.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		}
		if (!destroyImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardDoppelzerstoerung(Graphics2D g2) {
		if (counter < 15) {
			g2.drawImage(angreifer.defaultCard.image, Positions.tileSize8, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
			g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

			g2.drawImage(verteidiger.defaultCard.image, Positions.tileSize25, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
		} else {
			g2.drawImage(destroyImage.get(), Positions.tileSize8, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
			g2.drawImage(destroyImage2.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		}
		if (!destroyImage.isRunning && !destroyImage2.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardSelbstzerstoerung(Graphics2D g2) {
		if (counter < 15) {
			g2.drawImage(angreifer.defaultCard.image, Positions.tileSize8, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
			g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);
		} else {
			g2.drawImage(destroyImage.get(), Positions.tileSize8, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		}
		g2.drawImage(verteidiger.defaultCard.image, Positions.tileSize25, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
		if (!destroyImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardSchaden(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.image, Positions.tileSize8, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

		g2.drawImage(verteidiger.defaultCard.image, Positions.tileSize25, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);

		if (counter > 15) {
			g2.drawImage(schadenImage.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		} 
		if (!schadenImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawDirectAttack(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.image, Positions.tileSize8, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

		if (counter > 15) {
			g2.drawImage(schadenImage.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		} 
		if (!schadenImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnSchild(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.image, Positions.tileSize8, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

		g2.drawImage(verteidiger.defaultCard.image, Positions.tileSize25, Positions.tileSize6, gp.cardWidth * 3, gp.cardHeight * 3, null);

		if (counter < 15) {
			g2.drawImage(gp.imageLoader.statusSchild, Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		} else {
			g2.drawImage(destroyImage.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		} 
		if (!destroyImage.isRunning) {
			switchToGameBoard();
		}
	}

	public void showAttackOnSchild(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		destroyImage.isRunning = true;
		showGameBoard = false;
		showAttackOnSchild = true;
	}

	public void showDirectAttack(CardState angreifer) {
		this.angreifer = angreifer;
		schadenImage.isRunning = true;
		showGameBoard = false;
		showDirectAttack = true;
	}

	public void showAttackOnCardSchaden(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		schadenImage.isRunning = true;
		showGameBoard = false;
		showAttackOnCardSchaden = true;
	}

	public void showAttackOnCardZersteorung(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		destroyImage.isRunning = true;
		showGameBoard = false;
		showAttackOnCardZersteorung = true;
    }

    public void showAttackOnCardSelbstzerstoerung(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		destroyImage.isRunning = true;
		showGameBoard = false;
		showAttackOnCardSelbstzerstoerung = true;
    }

	public void showAttackOnCardDoppelZerstoerung(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		destroyImage.isRunning = true;
		destroyImage2.isRunning = true;
		showGameBoard = false;
		showAttackOnCardDoppelzerstoerung = true;
    }

    public void showAnimKarteStatsAenderung(Player p, CardState card, boolean b) {
		return;
    }
}