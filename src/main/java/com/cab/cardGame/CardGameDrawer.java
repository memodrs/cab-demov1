package com.cab.cardGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cab.GamePanel;

import com.cab.card.Status;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Colors;

import com.cab.draw.AnimImage;
import com.cab.draw.ImageLoader;

public class CardGameDrawer {
	CardGame cg;
	GamePanel gp;
	ImageLoader il;
	Player player;
	Player oponent;

	String msg = ""; // Anzeige was ist auf dem Board passiert 
	int counterAttack = 0;
	int counterEffekt = 0;
	int counterCardToHandPlayer = 0;
	int counterCardToHandOponent = 0;
	int counterSelectTargetCard = 0;
	int counterSelectedOption = 0;
	int counterCardToGravePlayer = 0;
	int counterCardToGraveOponent = 0;

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
	List<CardState> targetedCard = new ArrayList<>();
	List<CardState> effektCardForTarget = new ArrayList<>();
	List<String> effektTargetedOption = new ArrayList<>();
	List<CardState> addedCardToHandPlayer = new ArrayList<>();
	List<CardState> addCardToGravePlayer = new ArrayList<>();
	List<CardState> addCardToGraveOponent = new ArrayList<>();
	List<CardState> addedCardToHandOponent = new ArrayList<>();
	List<CardState> cardsToHeal = new ArrayList<>();

	public CardGameDrawer(CardGame cg) {
		this.cg = cg;
		this.gp = cg.gp;
		this.il = cg.gp.imageLoader;

		this.player = cg.player;
		this.oponent = cg.oponent;
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

	public void drawBG(Graphics2D g2) {
		g2.drawImage(il.cardGameBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
	}
	
	public void drawSelectedCard(CardState card, Graphics2D g2) {
		drawDialog(gp.p(7.5), gp.p(5), gp.p(9), gp.p(8), g2);

		g2.setColor(Colors.transparent); 
		g2.drawImage(card.defaultCard.getImage(), gp.p(12), gp.p(6), gp.p(3.6), gp.p(6), null);
	}
	
	public void drawSelectedCardText(CardState card, Graphics2D g2, String option1, String option2) {
		g2.setFont(gp.font(20));
		g2.drawImage(gp.imageLoader.paper04, gp.p(8.5), gp.p(8.7), gp.p(3), gp.p(2), null);
		g2.setColor(Colors.getColorSelection(0, cg.selectedIdx));
		g2.drawString(gp.t(option1), gp.p(9), gp.p(10));


		if (option2 != null) {
			g2.drawImage(gp.imageLoader.paper04, gp.p(8.5), gp.p(10.8), gp.p(3), gp.p(2), null);
			g2.setColor(Colors.getColorSelection(1, cg.selectedIdx));
			g2.drawString(gp.t(option2), gp.p(9), gp.p(12));
		}

		if (cg.selectedIdx == 0) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(7), gp.p(9), gp.p(2), gp.p(2), null);
		} else if (cg.selectedIdx == 1) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(7), gp.p(11), gp.p(2), gp.p(2), null);
		}
	}

	public void drawDialog(int x, int y, int width, int height, Graphics2D g2) {
		g2.setColor(Colors.transparentDarkBlack);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(5)); 
		g2.drawRoundRect(x, y, width, height, 25, 25);
	}

	private Image getStapelImage(int size) {
		if (size > 0) {
			return il.cardBackgroundImage;
		} else {
			return il.transparent;
		}
	}

	public void drawPlayerStats(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.setFont(gp.font(25));

		g2.drawImage(gp.imageLoader.paper11, gp.p(36), gp.p(13), gp.p(3), gp.p(4), null);

	    g2.drawImage(gp.imageLoader.iconHeart, gp.p(36.5), gp.p(13.5), gp.p(1), gp.p(1), null);
	    g2.drawString(String.valueOf(cg.player.lifeCounter), gp.p(38), gp.p(14));

		g2.drawImage(gp.imageLoader.iconArtFluch, gp.p(36.5), gp.p(14.5), gp.p(1), gp.p(1), null);
	    g2.drawString(String.valueOf(cg.player.fluchCounter), gp.p(38), gp.p(15.2));

		g2.drawImage(gp.imageLoader.iconArtSegen, gp.p(36.5), gp.p(15.5), gp.p(1), gp.p(1), null);
	    g2.drawString(String.valueOf(cg.player.segenCounter), gp.p(38), gp.p(16.2));

		g2.drawImage(getStapelImage(cg.player.stapel.size()), gp.p(33.5), gp.p(14), gp.p(2), gp.p(3), null);
		g2.drawImage(gp.imageLoader.paper03, gp.p(33.4), gp.p(16), gp.p(2), gp.p(1), null);
		g2.drawString(cg.player.stapel.size() + "", gp.p(34.4), gp.p(16.6));
	}

	public void drawOponentStats(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.setFont(gp.font(25));

		g2.drawImage(gp.imageLoader.paper11, gp.p(36), gp.p(1), gp.p(3), gp.p(4), null);

	    g2.drawImage(gp.imageLoader.iconHeart, gp.p(36.5), gp.p(1.5), gp.p(1), gp.p(1), null);
	    g2.drawString(String.valueOf(cg.oponent.lifeCounter), gp.p(38), gp.p(2));

		g2.drawImage(gp.imageLoader.iconArtFluch, gp.p(36.5), gp.p(2.5), gp.p(1), gp.p(1), null);
	    g2.drawString(String.valueOf(cg.oponent.fluchCounter), gp.p(38), gp.p(3));

		g2.drawImage(gp.imageLoader.iconArtSegen, gp.p(36.5), gp.p(3.6), gp.p(1), gp.p(1), null);
	    g2.drawString(String.valueOf(cg.oponent.segenCounter), gp.p(38), gp.p(4));

		g2.drawImage(getStapelImage(cg.oponent.stapel.size()), gp.p(33.5), gp.p(1.5), gp.p(2), gp.p(3), null);
		g2.drawImage(gp.imageLoader.paper03, gp.p(33.4), gp.p(1.6), gp.p(2), gp.p(1), null);
		g2.drawString(cg.oponent.stapel.size() + "", gp.p(34.4), gp.p(2.2));
	}

	public void drawAufgben(Graphics2D g2) {
		if (cg.cardGameState.isState(State.onAufgbenState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(29.6), gp.p(19), gp.p(3), gp.p(3), null);
			g2.drawImage(gp.imageLoader.iconAufgebenHover, gp.p(32), gp.p(20), gp.p(1), gp.p(1), null);
		} else {
			g2.drawImage(gp.imageLoader.iconAufgeben, gp.p(32), gp.p(20), gp.p(1), gp.p(1), null);
		}

		if (cg.cardGameState.isState(State.askAufgebenState)) {
			drawDialog(gp.p(17), gp.p(9), gp.p(4), gp.p(4), g2);
			g2.setFont(gp.font(36));
			
			g2.setColor(Color.RED);
			g2.setFont(gp.font(25));
			g2.drawString(gp.t("aufgebenFrage"), gp.p(17.5), gp.p(10));

			g2.setFont(gp.font(30));
			g2.setColor(Colors.getColorSelection(0, cg.selectedIdx));
			g2.drawString(gp.t("ja"), gp.p(19), gp.p(11.4));
			g2.setColor(Colors.getColorSelection(1, cg.selectedIdx));
			g2.drawString(gp.t("nein"), gp.p(19), gp.p(12.5));

			if (cg.selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(16.6), gp.p(9.9), gp.p(3), gp.p(3), null);

			} else {
				g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(16.6), gp.p(11), gp.p(3), gp.p(3), null);
			}
		}
	}

	public void drawHandPlayer(Graphics2D g2) {
		int numCards = cg.player.handCards.size();
		int middleIndex = numCards / 2;  // Index der mittleren Karte
		double angleStep = Math.toRadians(10);  // Winkel zwischen den Karten (10°)
						
		int x;
		int y;

		for (int i = 0; i < numCards; i++) {
			g2.setColor(Colors.transparent);


			// Berechne den relativen Winkel zur mittleren Karte
			double angle = (i - middleIndex) * angleStep;
		
			// Berechne die X- und Y-Position auf einem vertikalen Bogen
			x = (int) (gp.p(16) + Math.sin(angle) * gp.p(6));  // X-Position basierend auf dem Winkel
			y = (int) (gp.p(16) + Math.cos(angle));  // Y-Position basierend auf dem Winkel
		
			CardState card = cg.player.handCards.get(i);
			if (!(cg.selectedCard == card && (cg.cardGameState.isState(State.handCardSelectedState) || cg.cardGameState.isState(State.effektQuestionState)))) {
				boolean isEffektManualActivatable = cg.isEffektPossible(cg.player, Trigger.triggerManualFromHand, card);
				// Nur drehen, wenn es nicht die mittlere Karte ist
				if (i != middleIndex) {
					g2.rotate(angle, x + gp.p(4) / 2, y + gp.p(6) / 2);
				}
		
				// Zeichne die Karte an der berechneten Position
				if (cg.cardGameState.isState(State.handCardState) && i == cg.selectedIdx) {
					g2.drawImage(gp.cardLoader.getCard(card.defaultCard.getId()).getImage(), x, y - gp.p(4), gp.p(4), gp.p(6), null);
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y - gp.p(4), gp.p(4), gp.p(6), null);

					if (isEffektManualActivatable) {
						g2.drawImage(gp.imageLoader.instractionKeyboardG.get(), x, y - gp.p(6), gp.p(4), gp.p(2), null);
						g2.setColor(Color.WHITE);
						g2.setFont(gp.font(15));
						g2.drawString(gp.t("effektAktivieren"), x + gp.p(0.55), y - gp.p(4.68));
					}
				} else {
					g2.drawImage(gp.cardLoader.getCard(card.defaultCard.getId()).getImage(), x, y, gp.p(4), gp.p(6), null);
					
					if (player.isOnTurn && !player.inactiveMode) {
						if (player.isPlayCreatureAllowed(card)) {
							g2.drawImage(card.defaultCard.getCardIsPlayable().get(), x, y, gp.p(4), gp.p(6), null);
						}
						if (isEffektManualActivatable || cg.isSpellPossible(player,card)) {
							g2.drawImage(card.defaultCard.getCardIsEffektIsPossible().get(), x, y, gp.p(4), gp.p(6), null);
						}
					}
				}
		
				// Nach dem Zeichnen Rotation zurücksetzen
				if (i != middleIndex) {
					g2.rotate(-angle, x + gp.p(4) / 2, y + gp.p(6) / 2);
				}
			}
		}
		
		

		if (cg.cardGameState.isState(State.handCardState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(7), gp.p(16), gp.p(3), gp.p(3), null);
		}
	}

	public void drawHandCardSelected(Graphics2D g2) {
		if (cg.cardGameState.isState(State.handCardSelectedState)) {
			drawSelectedCard(cg.selectedCard, g2);
			drawSelectedCardText(cg.selectedCard, g2, "aufrufen", "verdecken");

			if (cg.selectedIdx == 1) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, gp.p(12), gp.p(6), gp.p(3.6), gp.p(6), null);
			}
		} 
	}
	
	public void drawCardEffektQuestion(Graphics2D g2) {
		if (cg.cardGameState.isState(State.effektQuestionState)) {
			drawSelectedCard(cg.selectedCard, g2);
			drawSelectedCardText(cg.selectedCard, g2, "aktivieren", null);
		}
	}

	private void setColorForStats(Graphics2D g2, int stateState, int defaultCardStat) {
		if (stateState > defaultCardStat) {
			g2.setColor(Color.YELLOW);
		} else if (stateState < defaultCardStat) {
			g2.setColor(Color.RED); 
		} else {
			g2.setColor(Color.BLACK);
		}
	}

	public void drawBoardPlayer(Graphics2D g2) {
		g2.setColor(Colors.transparent); 
		int y = gp.p(9.2);

		for (int i = 0; i < cg.player.boardCards.size(); i++) {
			int offsetX = (int) (gp.p(17) + gp.p(1.9) * i + gp.p(0.3) * i);

			CardState card = cg.player.boardCards.get(i);

        	if (card.isHide) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, y, gp.p(2), gp.p(3), null);
        	} else {
				g2.drawImage(card.defaultCard.getImage(), offsetX, y, gp.p(2), gp.p(3), null);
				
				//Stats unter der Karte
				g2.setFont(gp.font(25));
				setColorForStats(g2, card.life, card.defaultCard.getLife());
				g2.drawImage(il.paper01, offsetX - gp.p(0.05), gp.p(12.8), gp.p(2.2), gp.p(1.2), null);
				g2.drawImage(il.iconHeart, offsetX + gp.p(0.7), gp.p(12.8), gp.p(0.5), gp.p(0.5), null);
				g2.drawString(card.life + "", offsetX + gp.p(0.75), gp.p(13.7));
				setColorForStats(g2, card.atk, card.defaultCard.getAtk());
				g2.drawImage(il.iconAtk, offsetX + gp.p(1.4), gp.p(12.8), gp.p(0.5), gp.p(0.5), null);
				g2.drawString(card.atk + "", offsetX + gp.p(1.45), gp.p(13.7));
				g2.drawImage(il.getArtIconForArt(card.art, false), offsetX + gp.p(0.1), gp.p(13), gp.p(0.7), gp.p(0.7), null);

				if (cg.isEffektPossible(oponent, Trigger.triggerManualFromBoard, card)) {
					g2.drawImage(card.defaultCard.getCardIsPlayable().get(), offsetX, y, gp.p(2), gp.p(3), null);

					if (cg.cardGameState.isState(State.boardState) && cg.selectedIdx == i) {
						g2.drawImage(gp.imageLoader.instractionKeyboardG.get(), offsetX - gp.p(1), y - gp.p(2), gp.p(4), gp.p(2), null);
						g2.setColor(Color.WHITE);
						g2.setFont(gp.font(15));
						g2.drawString(gp.t("effektAktivieren"), offsetX - gp.p(0.5), y - gp.p(0.7));
					}
				}

				if (player.isAttackAlowed(card)) {
					g2.drawImage(gp.imageLoader.iconAttackAvailable, offsetX + gp.p(1), y + gp.p(2), gp.p(1), gp.p(1), null);
				}

				if (card.blockAttackOnTurn) {
					g2.drawImage(gp.imageLoader.iconBlockAtk, offsetX + gp.p(1), y + gp.p(2), gp.p(1), gp.p(1), null);
				}
			}

			if (cg.cardGameState.isState(State.boardState)) {
				if (i == cg.selectedIdx) {
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), offsetX, y, gp.p(2), gp.p(3), null);
				}
			}

			int j = 0;
			for (Status s : card.statusSet) {
				g2.drawImage(gp.imageLoader.paper05,  offsetX, y + j * gp.p(0.52), gp.p(0.8), gp.p(0.7), null); 
				g2.drawImage(gp.imageLoader.getStatusImage(s, false), offsetX + gp.p(0.25), y + gp.p(0.25) + j * gp.p(0.5), gp.p(0.3), gp.p(0.3), null);
				j++;
			}

			if (cardsToHeal.contains(card)) {
				drawHeal(g2, offsetX, y, gp.p(2), gp.p(3), gp.imageLoader.animHealPlayerList.get(i), card);;
			}
		}
		if (cg.cardGameState.isState(State.boardState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(14), y, gp.p(3), gp.p(3), null);
		}
	}

	public void drawBoardCardSelected(Graphics2D g2) {
		if (cg.cardGameState.isState(State.boardCardSelectedState)) {
			drawSelectedCard(cg.selectedCard, g2);	
			if (cg.selectedCard.isHide) {
				drawSelectedCardText(cg.selectedCard, g2, "aufdecken", null);
			} else {
				drawSelectedCardText(cg.selectedCard, g2, "angreifen", null);
			}
		}
	}

	public void drawBoardOponent(Graphics2D g2) {
		g2.setColor(Colors.transparent); 
		int y = gp.p(6);

		for (int i = 0; i < cg.oponent.boardCards.size(); i++) {
			int offsetX = (int) (gp.p(17) + gp.p(1.9) * i + gp.p(0.3) * i);
			CardState card = cg.oponent.boardCards.get(i);

        	if (card.isHide) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, y, gp.p(2), gp.p(3), null);
        	} else {
				g2.drawImage(card.defaultCard.getImageReverse(), offsetX, y, gp.p(2), gp.p(3), null);

				//Stats unter der Karte
				g2.setFont(gp.font(25));
				setColorForStats(g2, card.life, card.defaultCard.getLife());
				g2.drawImage(il.paper01, offsetX - gp.p(0.05), gp.p(4.45), gp.p(2.2), gp.p(1.2), null);
				g2.drawImage(il.iconHeart, offsetX + gp.p(0.7), gp.p(4.5), gp.p(0.5), gp.p(0.5), null);
				g2.drawString(card.life + "", offsetX + gp.p(0.75), gp.p(5.4));
				setColorForStats(g2, card.atk, card.defaultCard.getAtk());
				g2.drawImage(il.iconAtk, offsetX + gp.p(1.4), gp.p(4.5), gp.p(0.5), gp.p(0.5), null);
				g2.drawString(card.atk + "", offsetX + gp.p(1.45), gp.p(5.4));
				g2.drawImage(il.getArtIconForArt(card.art, false), offsetX + gp.p(0.1), gp.p(4.68), gp.p(0.7), gp.p(0.7), null);

				if (cg.cardGameState.isState(State.selectCardToAttackState)) {
					g2.drawImage(card.defaultCard.getCardSelectRed().get(), offsetX, y, gp.p(2), gp.p(3), null);
				}
			}

			if (card.blockAttackOnTurn) {
				g2.drawImage(gp.imageLoader.iconBlockAtk, offsetX + gp.p(1), y, gp.p(1), gp.p(1), null);
			}

			if (cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.selectCardToAttackState)) {
				if (i == cg.selectedIdx) {
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), offsetX, y, gp.p(2), gp.p(3), null);
				}
			}

			int j = 0;
			for (Status s : card.statusSet) {
				g2.drawImage(gp.imageLoader.paper05,  offsetX, y + j * gp.p(0.52), gp.p(0.8), gp.p(0.7), null); 
				g2.drawImage(gp.imageLoader.getStatusImage(s, false), offsetX + gp.p(0.25), y + gp.p(0.25) + j * gp.p(0.5), gp.p(0.3), gp.p(0.3), null);
				j++;
			}
		}
		if (cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.selectCardToAttackState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(14), y, gp.p(3), gp.p(3), null);
		}
	}

	public void drawHandOponent(Graphics2D g2) {
		int numCards = cg.oponent.handCards.size();
		int middleIndex = numCards / 2;  // Index der mittleren Karte
		double angleStep = Math.toRadians(10);  // Winkel zwischen den Karten (10°)
							
		int x;
		int y;
	
		for (int i = 0; i < numCards; i++) {
			g2.setColor(Colors.transparent);
			// Berechne den relativen Winkel zur mittleren Karte
			double angle = (i - middleIndex) * angleStep;
			
			// Berechne die X- und Y-Position auf einem vertikalen Bogen nach unten
			x = (int) (gp.p(25) + Math.sin(angle) * gp.p(6));  // X-Position basierend auf dem Winkel
			y = (int) (Math.cos(angle) - gp.p(2.5));  // Y-Position nach unten hin basierend auf dem Winkel
			
			// Nur drehen, wenn es nicht die mittlere Karte ist
			if (i != middleIndex) {
				// Umkehre den Winkel, damit die Karten nach unten rotieren
				g2.rotate(-angle, x + gp.p(4) / 2, y + gp.p(6) / 2);
			}
	
			g2.drawImage(gp.imageLoader.cardBackgroundImage, x, y, gp.p(4), gp.p(6), null);
	
			if (i != middleIndex) {
				// Setze die Rotation zurück
				g2.rotate(angle, x + gp.p(4) / 2, y + gp.p(6) / 2);
			}
		}
	}
	
	public void drawPlayerGrave(Graphics2D g2) {
		if (cg.player.graveCards.size() > 0) {
			CardState card = cg.player.graveCards.get(cg.player.graveCards.size() - 1);
			g2.drawImage(card.defaultCard.getImage(), gp.p(31), gp.p(9.5), gp.p(2), gp.p(3), null);
    		g2.setPaint(Colors.gardianSelectFromGrave);
			g2.fillRect(gp.p(31), gp.p(9.5), gp.p(2), gp.p(3));
		}

		if (cg.cardGameState.isState(State.graveState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), gp.p(31), gp.p(9.5), gp.p(2), gp.p(3), null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(28), gp.p(9.5), gp.p(3), gp.p(3), null);
		}
	}

	public void drawOponentGrave(Graphics2D g2) {
		if (cg.oponent.graveCards.size() > 0) {
			CardState card = cg.oponent.graveCards.get(cg.oponent.graveCards.size() - 1);
			g2.drawImage(card.defaultCard.getImageReverse(), gp.p(31), gp.p(6), gp.p(2), gp.p(3), null);
    		g2.setPaint(Colors.gardianSelectFromGrave);
			g2.fillRect(gp.p(31), gp.p(6), gp.p(2), gp.p(3));
		}

		if (cg.cardGameState.isState(State.graveOponentState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), gp.p(31), gp.p(6), gp.p(2), gp.p(3), null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(28), gp.p(6), gp.p(3), gp.p(3), null);
		}
	}
	
	public void drawPlayerSpellGrave(Graphics2D g2) {
		if (cg.player.spellGraveCards.size() > 0) {
			CardState card = cg.player.spellGraveCards.get(cg.player.spellGraveCards.size() - 1);
			g2.drawImage(card.defaultCard.getImage(), gp.p(36), gp.p(9.5), gp.p(2), gp.p(3), null);
    		g2.setPaint(Colors.gardianSelectFromGrave);
			g2.fillRect(gp.p(36), gp.p(9.5), gp.p(2), gp.p(3));
		}

		if (cg.cardGameState.isState(State.spellGraveState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), gp.p(36), gp.p(9.5), gp.p(2), gp.p(3), null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(33), gp.p(9.5), gp.p(3), gp.p(3), null);
		}
	}

	public void drawOponentSpellGrave(Graphics2D g2) {
		if (cg.oponent.spellGraveCards.size() > 0) {
			CardState card = cg.oponent.spellGraveCards.get(cg.oponent.spellGraveCards.size() - 1);
			g2.drawImage(card.defaultCard.getImageReverse(), gp.p(36), gp.p(6), gp.p(2), gp.p(3), null);
    		g2.setPaint(Colors.gardianSelectFromGrave);
			g2.fillRect(gp.p(36), gp.p(6), gp.p(2), gp.p(3));
		}

		if (cg.cardGameState.isState(State.spellGraveOponentState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), gp.p(36), gp.p(6), gp.p(2), gp.p(3), null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(33), gp.p(6), gp.p(3), gp.p(3), null);
		}
	}

	public void drawPlayerGraveSelected(Graphics2D g2) {
		if (cg.cardGameState.isState(State.graveSelectedState)) {
			drawDialog(gp.p(18), gp.p(4.68), gp.p(5), gp.p(10), g2);
			CardState card = cg.player.graveCards.get(cg.selectedIdx);
			g2.drawImage(card.defaultCard.getImage(), gp.p(19.5), gp.p(8), gp.p(1.9), gp.p(2.9), null);

			if (cg.cardGameState.isState(State.graveSelectedState) && cg.isEffektPossible(cg.player, Trigger.triggerManualFromGrave, card)) {
				g2.drawImage(gp.imageLoader.instractionKeyboardG.get(), gp.p(18.5), gp.p(5), gp.p(4), gp.p(2), null);
				g2.setColor(Color.WHITE);
				g2.setFont(gp.font(15));
				g2.drawString(gp.t("effektAktivieren"), gp.p(19), gp.p(6.2));
			}
			if (cg.selectedIdx == cg.player.graveCards.size() - 1) {
				g2.drawImage(gp.imageLoader.navigationArrowRightDisabled, gp.p(20) , gp.p(10.8), gp.p(3), gp.p(3), null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowRight, gp.p(20), gp.p(10.8), gp.p(3), gp.p(3), null);
			}

			if (cg.selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.navigationArrowLeftDisabled, gp.p(18), gp.p(10.8), gp.p(3), gp.p(3), null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowLeft, gp.p(18), gp.p(10.8), gp.p(3), gp.p(3), null);
			}
		}
	}

	public void drawOponentGraveSelected(Graphics2D g2) {
		if (cg.cardGameState.isState(State.graveSelectedOponentState)) {
			drawDialog(gp.p(18), gp.p(4.68), gp.p(5), gp.p(10), g2);
			CardState card = cg.oponent.graveCards.get(cg.selectedIdx);
			g2.drawImage(card.defaultCard.getImage(), gp.p(19.5), gp.p(8), gp.p(1.9), gp.p(2.9), null);

			if (cg.cardGameState.isState(State.graveSelectedState) && cg.isEffektPossible(cg.oponent, Trigger.triggerManualFromGrave, card)) {
				g2.drawImage(gp.imageLoader.instractionKeyboardG.get(), gp.p(18.5), gp.p(5), gp.p(4), gp.p(2), null);
				g2.setColor(Color.WHITE);
				g2.setFont(gp.font(15));
				g2.drawString(gp.t("effektAktivieren"), gp.p(19), gp.p(6.2));
			}
			if (cg.selectedIdx == cg.oponent.graveCards.size() - 1) {
				g2.drawImage(gp.imageLoader.navigationArrowRightDisabled, gp.p(20) , gp.p(10.8), gp.p(3), gp.p(3), null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowRight, gp.p(20), gp.p(10.8), gp.p(3), gp.p(3), null);
			}

			if (cg.selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.navigationArrowLeftDisabled, gp.p(18), gp.p(10.8), gp.p(3), gp.p(3), null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowLeft, gp.p(18), gp.p(10.8), gp.p(3), gp.p(3), null);
			}
		}
	}

	public void drawSelectOptionCards(Graphics2D g2) {
		if (cg.cardGameState.isState(State.selectOptionCardListState)) {
			drawDialog(gp.p(18), gp.p(4.68), gp.p(5), gp.p(10), g2);
			CardState card = cg.optionsCardsToSelect.get(cg.selectedIdx);
			g2.drawImage(card.defaultCard.getImage(), gp.p(19.5), gp.p(8), gp.p(1.9), gp.p(2.9), null);

			if (cg.selectedIdx == cg.optionsCardsToSelect.size() - 1) {
				g2.drawImage(gp.imageLoader.navigationArrowRightDisabled, gp.p(20) , gp.p(10.8), gp.p(3), gp.p(3), null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowRight, gp.p(20), gp.p(10.8), gp.p(3), gp.p(3), null);
			}

			if (cg.selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.navigationArrowLeftDisabled, gp.p(18), gp.p(10.8), gp.p(3), gp.p(3), null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowLeft, gp.p(18), gp.p(10.8), gp.p(3), gp.p(3), null);
			}
		}
	}

	public void drawSelectOption(Graphics2D g2) {
		if (cg.cardGameState.isState(State.selectOptionState)) {
			drawDialog(gp.p(18), gp.p(4.68), gp.p(6), gp.p(9), g2);

			int i = 0; 
			for (Map.Entry<String, String> entry : cg.optionsToSelect.entrySet()) {
				if (cg.selectedIdx == i) {
					g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(17.5), gp.p(5) + gp.p(1) * i, gp.p(2), gp.p(2), null);
				}

				g2.setColor(Colors.getColorSelection(i, cg.selectedIdx));
				g2.drawString(gp.t(entry.getKey()), gp.p(19.5), gp.p(6) + gp.p(1) * i);
				i++; 
			}
		}
	}

	public void drawBoardBlocks(Graphics2D g2) {
		//TODO Blocks zeichnen, vielleicht direkt auf die karte auf dem board
	}

	public void drawSelectedCard(Graphics2D g2, CardState card, boolean isPlayer) {
		if (card.isHide && !isPlayer) {
			g2.drawImage(gp.imageLoader.cardBackgroundImage, gp.p(1), gp.p(1), gp.p(5.7), gp.p(8.7), null);
		} else {
			g2.drawImage(card.defaultCard.getImage(), gp.p(0.5), gp.p(0.5), gp.p(5.4), gp.p(8.5), null);
		}
	}

	private void drawEffektCard(Graphics2D g2) {
		CardState card = effektCards.get(0);
		if (counterEffekt == 0) {
			counterEffekt++;
			cg.gp.playSE(8);
		}
		if (counterEffekt >= 150) {
			effektCards.remove(0);
			counterEffekt = 0;
		} else {
			int x = gp.p(10);
			g2.drawImage(card.defaultCard.getImage(), x, gp.p(7), gp.p(2), gp.p(3), null);
			g2.drawImage(il.iconEffektAvailable, gp.p(10.5), gp.p(9), gp.p(1), gp.p(1), null);
			g2.drawImage(card.defaultCard.getCardIsEffektIsPossible().get(), x, gp.p(7), gp.p(2), gp.p(3), null);
			g2.setColor(Color.YELLOW);
			g2.setFont(gp.font(15));
			g2.drawString(gp.t("effektAktiviert"), x, gp.p(10.5));

			g2.setColor(Color.ORANGE);

			String text = card.defaultCard.getBeschreibung();
			String[] lines = text.split("\n");

			int y = gp.p(11);
			int lineHeight = g2.getFontMetrics().getHeight(); // Höhe jeder Zeile basierend auf der aktuellen Schriftart

			for (String line : lines) {
				g2.drawString(line, x, y);
				y += lineHeight; // Y-Position für die nächste Zeile erhöhen
}
			//Draw Select kümmert sich drum dass das nicht mehr angezeigt wird
			if (card.selectState != State.selectOptionState &&				
				card.selectState != State.selectOptionCardListState) {
					counterEffekt++;
			}
		}
	}

	private void drawAddedCardToHandPlayer(Graphics2D g2) {
		if (counterCardToHandPlayer >= 120) {
			addedCardToHandPlayer.remove(0);
			counterCardToHandPlayer = 0;
		} else {
			g2.drawImage(addedCardToHandPlayer.get(0).defaultCard.getImage(), gp.p(30), gp.p(16), gp.p(2), gp.p(3), null);
			g2.drawImage(il.iconArrowLeft, gp.p(28), gp.p(16.7), gp.p(1.5), gp.p(1.5), null);
			g2.drawImage(il.iconHand, gp.p(26.5), gp.p(16.7), gp.p(1.5), gp.p(1.5), null);
			counterCardToHandPlayer++;
		}
	}

	private void drawAddedCardToHandOponent(Graphics2D g2) {
		if (counterCardToHandOponent >= 120) {
			addedCardToHandOponent.remove(0);
			counterCardToHandOponent = 0;
		} else {
			g2.drawImage(addedCardToHandOponent.get(0).defaultCard.getImage(), gp.p(8), gp.p(2), gp.p(2), gp.p(3), null);
			g2.drawImage(il.iconArrowRight, gp.p(10), gp.p(2.7), gp.p(1.5), gp.p(1.5), null);
			g2.drawImage(il.iconHand, gp.p(11.5), gp.p(2.7), gp.p(1.5), gp.p(1.5), null);
			counterCardToHandOponent++;
		}
	}

	private void drawAddCardToGraveOponent(Graphics2D g2) {
		if (counterCardToGraveOponent >= 120) {
			addCardToGraveOponent.remove(0);
			counterCardToGraveOponent = 0;
		} else {
			g2.drawImage(addCardToGraveOponent.get(0).defaultCard.getImage(), gp.p(37), gp.p(6), gp.p(2), gp.p(3), null);
			g2.drawImage(il.iconArrowLeft, gp.p(35), gp.p(7.5), gp.p(1.5), gp.p(1.5), null);
			g2.drawImage(il.iconGrave, gp.p(33.5), gp.p(7.5), gp.p(1.5), gp.p(1.5), null);
			counterCardToGraveOponent++;
		}
	}

	private void drawAddCardToGravePlayer(Graphics2D g2) {
		if (counterCardToGravePlayer >= 120) {
			addCardToGravePlayer.remove(0);
			counterCardToGravePlayer = 0;
		} else {
			g2.drawImage(addCardToGravePlayer.get(0).defaultCard.getImage(), gp.p(37), gp.p(9.5), gp.p(2), gp.p(3), null);
			g2.drawImage(il.iconArrowLeft, gp.p(35), gp.p(10.5), gp.p(1.5), gp.p(1.5), null);
			g2.drawImage(il.iconGrave, gp.p(33.5), gp.p(10.5), gp.p(1.5), gp.p(1.5), null);
			counterCardToGravePlayer++;
		}
	}

	private void drawEffektSelectedOption(Graphics2D g2) {
		if (effektCards.get(0) == effektCardForTarget.get(0)) {
			if (counterSelectedOption >= 120) {
				this.effektTargetedOption.remove(0);
				this.effektCardForTarget.remove(0);
				this.effektCards.remove(0);
				counterEffekt = 0;
				counterSelectedOption = 0;
			} else {
				g2.setColor(Color.YELLOW);
				g2.setFont(gp.font(25));
				g2.drawString(gp.t("optionGewaehlt") + " " + this.effektTargetedOption.get(0), gp.p(7), gp.p(12));
				counterSelectedOption++;
			}
		}
	}

	private void drawTargetedCard(Graphics2D g2) {
		if (effektCards.get(0) == effektCardForTarget.get(0)) {
			if (counterSelectTargetCard >= 90) {
				this.targetedCard.remove(0);
				this.effektCardForTarget.remove(0);
				this.effektCards.remove(0);
				counterEffekt = 0;
				counterSelectTargetCard = 0;
			} else {
				g2.drawImage(targetedCard.get(0).defaultCard.getImage(), gp.p(12.5), gp.p(7.5), gp.p(1.5), gp.p(2.5), null);
				g2.drawImage(il.cardTargeted.get(), gp.p(12.5), gp.p(7.5), gp.p(1.5), gp.p(2.5), null);
				counterSelectTargetCard++;
			}
		}
	}

	public void showSpecialAddCardToHand(Player p, CardState card) {
		if (p.isPlayer) {
			this.addedCardToHandPlayer.add(card);
		} else {
			this.addedCardToHandOponent.add(card);
		}
	}

	public void showAddToGrave(Player p, CardState card) {
		if (p.isPlayer) {
			this.addCardToGravePlayer.add(card);
		} else {
			this.addCardToGraveOponent.add(card);
		}
	}

	public void showSelectedOption(String selectedOption) {
		this.effektTargetedOption.add(selectedOption);
		this.effektCardForTarget.add(cg.activeEffektCard);
	}

	public void showCardTargeted(CardState card) {
		this.targetedCard.add(card);
		this.effektCardForTarget.add(cg.activeEffektCard);
	}
	
	public void draw(Graphics2D g2) {
		if (showGameBoard) {
			drawBG(g2);
			drawHandOponent(g2);
			drawBoardOponent(g2);
			drawPlayerSpellGrave(g2);
			drawOponentSpellGrave(g2);
			drawPlayerGrave(g2);
			drawBoardPlayer(g2);
			drawOponentGrave(g2);
			drawHandPlayer(g2);
			drawOponentStats(g2);
			drawPlayerStats(g2);
			drawPlayerGraveSelected(g2);
			drawOponentGraveSelected(g2);
			
			//Live Selected Panel
			if (cg.cardGameState.isState(State.handCardSelectedState) || cg.cardGameState.isState(State.effektQuestionState) || cg.cardGameState.isState(State.boardCardSelectedState)) {
				drawSelectedCard(g2, cg.selectedCard, true);
			} else if (cg.cardGameState.isState(State.handCardState) && cg.player.handCards.size() > 0) {
				drawSelectedCard(g2, cg.player.handCards.get(cg.selectedIdx), true);
			} else if (cg.cardGameState.isState(State.boardState) && cg.player.boardCards.size() > 0) {
				drawSelectedCard(g2, cg.player.boardCards.get(cg.selectedIdx), true);
			} else if ((cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.selectCardToAttackState)) && cg.oponent.boardCards.size() > 0) {
				drawSelectedCard(g2, cg.oponent.boardCards.get(cg.selectedIdx), false);
			} else if (cg.cardGameState.isState(State.graveSelectedState) && cg.player.graveCards.size() > 0) {
				drawSelectedCard(g2, cg.player.graveCards.get(cg.selectedIdx), true);
			}  else if (cg.cardGameState.isState(State.graveSelectedOponentState) && cg.oponent.graveCards.size() > 0) {
				drawSelectedCard(g2, cg.oponent.graveCards.get(cg.selectedIdx), false);
			} else if (cg.cardGameState.isState(State.graveState) && cg.player.graveCards.size() > 0) {
				drawSelectedCard(g2, cg.player.graveCards.get(cg.player.graveCards.size() - 1), true);
			} else if (cg.cardGameState.isState(State.graveOponentState) && cg.oponent.graveCards.size() > 0) {
				drawSelectedCard(g2, cg.oponent.graveCards.get(cg.oponent.graveCards.size() - 1), false);
			} else if (cg.cardGameState.isState(State.spellGraveState) && cg.player.spellGraveCards.size() > 0) {
				drawSelectedCard(g2, cg.player.spellGraveCards.get(cg.player.spellGraveCards.size() - 1), true);
			} else if (cg.cardGameState.isState(State.spellGraveOponentState) && cg.oponent.spellGraveCards.size() > 0) {
				drawSelectedCard(g2, cg.oponent.spellGraveCards.get(cg.oponent.spellGraveCards.size() - 1), false);
			} else if (cg.cardGameState.isState(State.selectOptionCardListState)) {
				drawSelectedCard(g2, cg.optionsCardsToSelect.get(cg.selectedIdx), true);
			}

			drawSelectOption(g2);
			drawSelectOptionCards(g2);
			drawHandCardSelected(g2);
			drawBoardCardSelected(g2);
			drawCardEffektQuestion(g2);
			drawBoardBlocks(g2);

			g2.setFont(gp.font(25));
			
				if (!player.isOnTurn && !player.inactiveMode) {
				g2.setColor(Color.YELLOW);
				g2.drawString(gp.t("waehleZiel"), gp.p(8), gp.p(1));
			} else if (player.isOnTurn && player.inactiveMode) {
				g2.setColor(Color.RED);
				g2.drawString(gp.t("gegnerWaehltZiel"), gp.p(8), gp.p(1));
			} else if (player.isOnTurn)  {
				g2.setColor(Color.YELLOW);
				g2.drawString(gp.t("duBistDran"), gp.p(8), gp.p(1));
				g2.drawString(gp.t("instractionZugBeenden"), gp.p(8), gp.p(2));

			} else if (!player.isOnTurn) {
				g2.setColor(Color.RED);
				g2.drawString(gp.t("gegnerIstDran"), gp.p(8), gp.p(1));
			}

			if (effektCards.size() > 0) {
				drawEffektCard(g2);
			}

			if (targetedCard.size() > 0) {
				drawTargetedCard(g2);
			}

			if (addedCardToHandPlayer.size() > 0) {
				drawAddedCardToHandPlayer(g2);
			}

			if (addedCardToHandOponent.size() > 0) {
				drawAddedCardToHandOponent(g2);
			}			
			
			if (addCardToGraveOponent.size() > 0) {
				drawAddCardToGraveOponent(g2);
			}

			if (addCardToGravePlayer.size() > 0) {
				drawAddCardToGravePlayer(g2);
			}

			if (effektTargetedOption.size() > 0) {
				drawEffektSelectedOption(g2);
			}

			drawAufgben(g2);

			if (cg.cardGameState.isState(State.gameFinishedState)) {
				g2.setColor(Colors.transparentBlack);
				g2.fillRoundRect(0, 0, gp.screenWidth, gp.screenHeight, 35, 35);
				g2.setColor(Color.RED);
				g2.setFont(gp.font(36));
				g2.drawString(gp.t("spielZuEnde"), gp.p(16), gp.p(12));
				g2.setColor(Color.YELLOW);
				g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(17), gp.p(13), gp.p(2), gp.p(2), null);
				g2.drawString(gp.t("ok"), gp.p(19), gp.p(14));
			}
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
			counterAttack++;
		}


		
	}

	private void switchToGameBoard() {
		counterAttack = 0;
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
		g2.drawImage(angreifer.defaultCard.getImage(), gp.p(8), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, gp.p(9.5), gp.p(12), gp.p(3), gp.p(3), null);

		if (counterAttack < 15) {
			g2.drawImage(verteidiger.defaultCard.getImage(), gp.p(25), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
		} else {
			g2.drawImage(destroyImage.get(), gp.p(25), gp.p(8), gp.p(6), gp.p(6), null);
		}
		if (!destroyImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardDoppelzerstoerung(Graphics2D g2) {
		if (counterAttack < 15) {
			g2.drawImage(angreifer.defaultCard.getImage(), gp.p(8), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
			g2.drawImage(gp.imageLoader.iconAttackAvailable, gp.p(9.5), gp.p(12), gp.p(3), gp.p(3), null);

			g2.drawImage(verteidiger.defaultCard.getImage(), gp.p(25), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
		} else {
			g2.drawImage(destroyImage.get(), gp.p(8), gp.p(8), gp.p(6), gp.p(6), null);
			g2.drawImage(destroyImage2.get(), gp.p(25), gp.p(8), gp.p(6), gp.p(6), null);
		}
		if (!destroyImage.isRunning && !destroyImage2.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardSelbstzerstoerung(Graphics2D g2) {
		if (counterAttack < 15) {
			g2.drawImage(angreifer.defaultCard.getImage(), gp.p(8), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
			g2.drawImage(gp.imageLoader.iconAttackAvailable, gp.p(9.5), gp.p(12), gp.p(3), gp.p(3), null);
		} else {
			g2.drawImage(destroyImage.get(), gp.p(8), gp.p(8), gp.p(6), gp.p(6), null);
		}
		g2.drawImage(verteidiger.defaultCard.getImage(), gp.p(25), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
		if (!destroyImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardSchaden(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.getImage(), gp.p(8), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, gp.p(9.5), gp.p(12), gp.p(3), gp.p(3), null);

		g2.drawImage(verteidiger.defaultCard.getImage(), gp.p(25), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);

		if (counterAttack > 15) {
			g2.drawImage(gp.imageLoader.blinkRed.get(), gp.p(25), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
			g2.drawImage(schadenImage.get(), gp.p(25), gp.p(8), gp.p(6), gp.p(6), null);
		} 
		if (!schadenImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawDirectAttack(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.getImage(), gp.p(8), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, gp.p(9.5), gp.p(12), gp.p(3), gp.p(3), null);

		if (counterAttack > 15) {
			g2.drawImage(schadenImage.get(), gp.p(25), gp.p(8), gp.p(6), gp.p(6), null);
		} 
		if (!schadenImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnSchild(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.getImage(), gp.p(8), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, gp.p(9.5), gp.p(12), gp.p(3), gp.p(3), null);

		g2.drawImage(verteidiger.defaultCard.getImage(), gp.p(25), gp.p(6), gp.p(1.9) * 3, gp.p(2.9) * 3, null);

		if (counterAttack < 15) {
			g2.drawImage(gp.imageLoader.statusSchild, gp.p(25), gp.p(8), gp.p(6), gp.p(6), null);
		} else {
			g2.drawImage(destroyImage.get(), gp.p(25), gp.p(8), gp.p(6), gp.p(6), null);
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
		cg.gp.playSE(7);
	}

	public void showDirectAttack(CardState angreifer) {
		this.angreifer = angreifer;
		schadenImage.isRunning = true;
		showGameBoard = false;
		showDirectAttack = true;
		cg.gp.playSE(6);
	}

	public void showAttackOnCardSchaden(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		schadenImage.isRunning = true;
		showGameBoard = false;
		showAttackOnCardSchaden = true;
		cg.gp.playSE(6);
	}

	public void showAttackOnCardZersteorung(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		destroyImage.isRunning = true;
		showGameBoard = false;
		showAttackOnCardZersteorung = true;
		cg.gp.playSE(7);
    }

    public void showAttackOnCardSelbstzerstoerung(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		destroyImage.isRunning = true;
		showGameBoard = false;
		showAttackOnCardSelbstzerstoerung = true;
		cg.gp.playSE(7);
    }

	public void showAttackOnCardDoppelZerstoerung(CardState angreifer, CardState verteidiger) {
		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
		destroyImage.isRunning = true;
		destroyImage2.isRunning = true;
		showGameBoard = false;
		showAttackOnCardDoppelzerstoerung = true;
		cg.gp.playSE(7);
    }

    public void showHealCard(CardState card) {
		this.cardsToHeal.add(card);
		cg.gp.playSE(9);
    }

	private void drawHeal(Graphics2D g2, int x, int y, int width, int height, AnimImage animImage, CardState card) {
		g2.drawImage(animImage.get(), x, y, width, height, null);

		if (!animImage.isRunning) {
			cardsToHeal.remove(card);
			animImage.isRunning = true;
		}
	}
}