package com.cab.cardGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cab.GamePanel;
import com.cab.Main;
import com.cab.card.Status;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.configs.Colors;
import com.cab.configs.Positions;
import com.cab.draw.AnimImage;
import com.cab.draw.ImageLoader;
import com.cab.draw.SelectedCard;

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

	SelectedCard selectedCard;

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
		selectedCard = new SelectedCard(gp, Positions.tileSize, Positions.tileSize);
		//Angriff
		destroyImage = gp.imageLoader.animDestroy;
		destroyImage2 = gp.imageLoader.animDestroy2;
		schadenImage = gp.imageLoader.animSchaden;
	}
	
	public void showMsg(String msg) {
		this.msg = msg;
	}

	public void drawBG(Graphics2D g2) {
		g2.drawImage(il.cardGameBG, 0, 0, Positions.screenWidth, Positions.screenHeight, null);
	}
	
	public void drawSelectedCard(CardState card, Graphics2D g2) {
		drawDialog(Positions.tileSize7Point5, Positions.tileSize5, Positions.tileSize9, Positions.tileSize8, g2);

		g2.setColor(Colors.transparent); 
		g2.drawImage(card.defaultCard.getImage(), Positions.tileSize12, Positions.tileSize6, Positions.tileSize3Point6, Positions.tileSize6, null);
	}
	
	public void drawSelectedCardText(CardState card, Graphics2D g2, String option1, String option2) {
		g2.setFont(Main.v.brushedFont20);
		g2.drawImage(gp.imageLoader.paper04, Positions.tileSize8Point5, Positions.tileSize8Point7, Positions.tileSize3, Positions.tileSize2, null);
		g2.setColor(Colors.getColorSelection(0, cg.selectedIdx));
		g2.drawString(gp.t(option1), Positions.tileSize9, Positions.tileSize10);


		if (option2 != null) {
			g2.drawImage(gp.imageLoader.paper04, Positions.tileSize8Point5, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize2, null);
			g2.setColor(Colors.getColorSelection(1, cg.selectedIdx));
			g2.drawString(gp.t(option2), Positions.tileSize9, Positions.tileSize12);
		}

		if (cg.selectedIdx == 0) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize7, Positions.tileSize9, Positions.tileSize2, Positions.tileSize2, null);
		} else if (cg.selectedIdx == 1) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize7, Positions.tileSize11, Positions.tileSize2, Positions.tileSize2, null);
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
		g2.setFont(Main.v.brushedFont25);

		g2.drawImage(gp.imageLoader.paper11, Positions.tileSize36, Positions.tileSize13, Positions.tileSize3, Positions.tileSize4, null);

	    g2.drawImage(gp.imageLoader.iconHeart, Positions.tileSize36Point5, Positions.tileSize13Point5, Positions.tileSize, Positions.tileSize, null);
	    g2.drawString(String.valueOf(cg.player.lifeCounter), Positions.tileSize38, Positions.tileSize14);

		g2.drawImage(gp.imageLoader.iconArtFluch, Positions.tileSize36Point5, Positions.tileSize14Point5, Positions.tileSize, Positions.tileSize, null);
	    g2.drawString(String.valueOf(cg.player.fluchCounter), Positions.tileSize38, Positions.tileSize15Point2);

		g2.drawImage(gp.imageLoader.iconArtSegen, Positions.tileSize36Point5, Positions.tileSize15Point5, Positions.tileSize, Positions.tileSize, null);
	    g2.drawString(String.valueOf(cg.player.segenCounter), Positions.tileSize38, Positions.tileSize16Point2);

		g2.drawImage(getStapelImage(cg.player.stapel.size()), Positions.tileSize33Point5, Positions.tileSize14, Positions.tileSize2, Positions.tileSize3, null);
		g2.drawImage(gp.imageLoader.paper03, Positions.tileSize33Point4, Positions.tileSize16, Positions.tileSize2, Positions.tileSize, null);
		g2.drawString(cg.player.stapel.size() + "", Positions.tileSize34Point4, Positions.tileSize16Point6);
	}

	public void drawOponentStats(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.setFont(Main.v.brushedFont25);

		g2.drawImage(gp.imageLoader.paper11, Positions.tileSize36, Positions.tileSize, Positions.tileSize3, Positions.tileSize4, null);

	    g2.drawImage(gp.imageLoader.iconHeart, Positions.tileSize36Point5, Positions.tileSize1Point5, Positions.tileSize, Positions.tileSize, null);
	    g2.drawString(String.valueOf(cg.oponent.lifeCounter), Positions.tileSize38, Positions.tileSize2);

		g2.drawImage(gp.imageLoader.iconArtFluch, Positions.tileSize36Point5, Positions.tileSize2Point5, Positions.tileSize, Positions.tileSize, null);
	    g2.drawString(String.valueOf(cg.oponent.fluchCounter), Positions.tileSize38, Positions.tileSize3);

		g2.drawImage(gp.imageLoader.iconArtSegen, Positions.tileSize36Point5, Positions.tileSize3Point6, Positions.tileSize, Positions.tileSize, null);
	    g2.drawString(String.valueOf(cg.oponent.segenCounter), Positions.tileSize38, Positions.tileSize4);

		g2.drawImage(getStapelImage(cg.player.stapel.size()), Positions.tileSize33Point5, Positions.tileSize1Point5, Positions.tileSize2, Positions.tileSize3, null);
		g2.drawImage(gp.imageLoader.paper03, Positions.tileSize33Point4, Positions.tileSize1Point6, Positions.tileSize2, Positions.tileSize, null);
		g2.drawString(cg.oponent.stapel.size() + "", Positions.tileSize34Point4, Positions.tileSize2Point2);
	}

	public void drawAufgben(Graphics2D g2) {
		if (cg.cardGameState.isState(State.onAufgbenState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize29Point6, Positions.tileSize19, Positions.tileSize3, Positions.tileSize3, null);
			g2.drawImage(gp.imageLoader.iconAufgebenHover, Positions.tileSize32, Positions.tileSize20, Positions.tileSize, Positions.tileSize, null);
		} else {
			g2.drawImage(gp.imageLoader.iconAufgeben, Positions.tileSize32, Positions.tileSize20, Positions.tileSize, Positions.tileSize, null);
		}

		if (cg.cardGameState.isState(State.askAufgebenState)) {
			drawDialog(Positions.tileSize17, Positions.tileSize9, Positions.tileSize4, Positions.tileSize4, g2);
			g2.setFont(Main.v.brushedFont36);
			
			g2.setColor(Color.RED);
			g2.setFont(Main.v.brushedFont25);
			g2.drawString(gp.t("aufgebenFrage"), Positions.tileSize17Point5, Positions.tileSize10);

			g2.setFont(Main.v.brushedFont30);
			g2.setColor(Colors.getColorSelection(0, cg.selectedIdx));
			g2.drawString(gp.t("ja"), Positions.tileSize19, Positions.tileSize11Point4);
			g2.setColor(Colors.getColorSelection(1, cg.selectedIdx));
			g2.drawString(gp.t("nein"), Positions.tileSize19, Positions.tileSize12Point5);

			if (cg.selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize16Point6, Positions.tileSize9Point9, Positions.tileSize3, Positions.tileSize3, null);

			} else {
				g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize16Point6, Positions.tileSize11, Positions.tileSize3, Positions.tileSize3, null);
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
			x = (int) (Positions.tileSize16 + Math.sin(angle) * Positions.tileSize6);  // X-Position basierend auf dem Winkel
			y = (int) (Positions.tileSize16 + Math.cos(angle));  // Y-Position basierend auf dem Winkel
		

			if (!(i == cg.selectedHandCardIdx && (cg.cardGameState.isState(State.handCardSelectedState) || cg.cardGameState.isState(State.effektQuestionStateHand)))) {
				CardState card = cg.player.handCards.get(i);
				boolean isEffektManualActivatable = cg.isEffektManualActivatable(cg.player, card, Trigger.triggerManualFromHand);
				// Nur drehen, wenn es nicht die mittlere Karte ist
				if (i != middleIndex) {
					g2.rotate(angle, x + Positions.tileSize4 / 2, y + Positions.tileSize6 / 2);
				}
		
				// Zeichne die Karte an der berechneten Position
				if (cg.cardGameState.isState(State.handCardState) && i == cg.selectedIdx) {
					g2.drawImage(gp.cardLoader.getCard(card.defaultCard.getId()).getImage(), x, y - Positions.tileSize4, Positions.tileSize4, Positions.tileSize6, null);
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y - Positions.tileSize4, Positions.tileSize4, Positions.tileSize6, null);

					if (isEffektManualActivatable && player.isOnTurn && !player.inactiveMode) {
						g2.drawImage(gp.imageLoader.instractionKeyboardG.get(), x, y - Positions.tileSize6, Positions.tileSize4, Positions.tileSize2, null);
						g2.setColor(Color.WHITE);
						g2.setFont(Main.v.brushedFont15);
						g2.drawString(gp.t("effektAktivieren"), x + Positions.tileSize0Point55, y - Positions.tileSize4Point68);
					}
				} else {
					g2.drawImage(gp.cardLoader.getCard(card.defaultCard.getId()).getImage(), x, y, Positions.tileSize4, Positions.tileSize6, null);
					
					if (player.isOnTurn && !player.inactiveMode) {
						if (player.isPlayCreatureAllowed(card)) {
							g2.drawImage(card.defaultCard.getCardIsPlayable().get(), x, y, Positions.tileSize4, Positions.tileSize6, null);
						}
						if (isEffektManualActivatable || player.isPlaySpellAllowed(oponent, card)) {
							g2.drawImage(card.defaultCard.getCardIsEffektIsPossible().get(), x, y, Positions.tileSize4, Positions.tileSize6, null);
						}
					}
				}
		
				// Nach dem Zeichnen Rotation zurücksetzen
				if (i != middleIndex) {
					g2.rotate(-angle, x + Positions.tileSize4 / 2, y + Positions.tileSize6 / 2);
				}
			}
		}
		
		

		if (cg.cardGameState.isState(State.handCardState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize7, Positions.tileSize16, Positions.tileSize3, Positions.tileSize3, null);
		}
	}

	public void drawHandCardSelected(Graphics2D g2) {
		if (cg.cardGameState.isState(State.handCardSelectedState)) {
			CardState card = cg.player.handCards.get(cg.selectedHandCardIdx);
			drawSelectedCard(card, g2);
			drawSelectedCardText(card, g2, "aufrufen", "verdecken");

			if (cg.selectedIdx == 1) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, Positions.tileSize12, Positions.tileSize6, Positions.tileSize3Point6, Positions.tileSize6, null);
			}
		} 
	}
	
	public void drawCardEffektQuestion(Graphics2D g2) {
		if (cg.cardGameState.isState(State.effektQuestionStateBoard) || cg.cardGameState.isState(State.effektQuestionStateGrave) || cg.cardGameState.isState(State.effektQuestionStateHand)) {
			CardState card = null;
			if (cg.cardGameState.isState(State.effektQuestionStateBoard)) {
				card = cg.player.boardCards.get(cg.selectedBoardCardIdx);
			} else if (cg.cardGameState.isState(State.effektQuestionStateGrave)) {
				card = cg.player.graveCards.get(cg.selectGraveCardIdx);
			} else if (cg.cardGameState.isState(State.effektQuestionStateHand)) {
				card = cg.player.handCards.get(cg.selectedHandCardIdx);
			}

			drawSelectedCard(card, g2);
			drawSelectedCardText(card, g2, "aktivieren", null);
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
		int y = Positions.tileSize9Point2;

		for (int i = 0; i < cg.player.boardCards.size(); i++) {
			int offsetX = (int) (Positions.tileSize17 + Positions.cardWidth * i + Positions.tileSize0Point3 * i);

			CardState card = cg.player.boardCards.get(i);

        	if (card.isHide) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
        	} else {
				g2.drawImage(card.defaultCard.getImage(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
				
				//Stats unter der Karte
				g2.setFont(Main.v.brushedFont25);
				setColorForStats(g2, card.life, card.defaultCard.getLife());
				g2.drawImage(il.paper01, offsetX - Positions.tileSize0Point05, Positions.tileSize12Point8, Positions.tileSize2Point2, Positions.tileSize1Point2, null);
				g2.drawImage(il.iconHeart, offsetX + Positions.tileSize0Point7, Positions.tileSize12Point8, Positions.tileSize0Point5, Positions.tileSize0Point5, null);
				g2.drawString(card.life + "", offsetX + Positions.tileSize0Point75, Positions.tileSize13Point7);
				setColorForStats(g2, card.atk, card.defaultCard.getAtk());
				g2.drawImage(il.iconAtk, offsetX + Positions.tileSize1Point4, Positions.tileSize12Point8, Positions.tileSize0Point5, Positions.tileSize0Point5, null);
				g2.drawString(card.atk + "", offsetX + Positions.tileSize1Point45, Positions.tileSize13Point7);
				g2.drawImage(il.getArtIconForArt(card.art, false), offsetX + Positions.tileSize0Point1, Positions.tileSize13, Positions.tileSize0Point7, Positions.tileSize0Point7, null);

				if (cg.isEffektManualActivatable(cg.player, card, Trigger.triggerManualFromBoard)) {
					g2.drawImage(card.defaultCard.getCardIsPlayable().get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);

					if (cg.cardGameState.isState(State.boardState) && cg.selectedIdx == i) {
						g2.drawImage(gp.imageLoader.instractionKeyboardG.get(), offsetX - Positions.tileSize, y - Positions.tileSize2, Positions.tileSize4, Positions.tileSize2, null);
						g2.setColor(Color.WHITE);
						g2.setFont(Main.v.brushedFont15);
						g2.drawString(gp.t("effektAktivieren"), offsetX - Positions.tileSize0Point5, y - Positions.tileSize0Point7);
					}
				}

				if (player.isAttackAlowed(card)) {
					g2.drawImage(gp.imageLoader.iconAttackAvailable, offsetX + Positions.tileSize, y + Positions.tileSize2, Positions.tileSize, Positions.tileSize, null);
				}

				if (card.blockAttackOnTurn) {
					g2.drawImage(gp.imageLoader.iconBlockAtk, offsetX + Positions.tileSize, y + Positions.tileSize2, Positions.tileSize, Positions.tileSize, null);
				}

				if (cg.cardGameState.isState(State.effektSelectOwnBoardState)) {
					if (card == cg.activeEffektCard) {
						g2.drawImage(card.defaultCard.getCardIsPlayable().get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
					}

					if (cg.activeEffektCard.isCardValidForSelection(card)) {
						g2.drawImage(card.defaultCard.getCardSelectGreen().get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
					}
				}
			}

			if (cg.cardGameState.isState(State.boardState) || cg.cardGameState.isState(State.effektSelectOwnBoardState)) {
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

			if (cardsToHeal.contains(card)) {
				drawHeal(g2, offsetX, y, Positions.tileSize2, Positions.tileSize3, gp.imageLoader.animHealPlayerList.get(i), card);;
			}
		}
		if (cg.cardGameState.isState(State.boardState) || cg.cardGameState.isState(State.effektSelectOwnBoardState)) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize14, y, Positions.tileSize3, Positions.tileSize3, null);
		}
	}

	public void drawBoardCardSelected(Graphics2D g2) {
		if (cg.cardGameState.isState(State.boardCardSelectedState)) {
			CardState card = cg.player.boardCards.get(cg.selectedBoardCardIdx);
			drawSelectedCard(card, g2);	
			if (card.isHide) {
				drawSelectedCardText(card, g2, "aufdecken", null);
			} else {
				drawSelectedCardText(card, g2, "angreifen", null);
			}
		}
	}

	public void drawBoardOponent(Graphics2D g2) {
		g2.setColor(Colors.transparent); 
		int y = Positions.tileSize6;

		for (int i = 0; i < cg.oponent.boardCards.size(); i++) {
			int offsetX = (int) (Positions.tileSize17 + Positions.cardWidth * i + Positions.tileSize0Point3 * i);
			CardState card = cg.oponent.boardCards.get(i);

        	if (card.isHide) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
        	} else {
				g2.drawImage(card.defaultCard.getImageReverse(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);

				//Stats unter der Karte
				g2.setFont(Main.v.brushedFont25);
				setColorForStats(g2, card.life, card.defaultCard.getLife());
				g2.drawImage(il.paper01, offsetX - Positions.tileSize0Point05, Positions.tileSize4Point45, Positions.tileSize2Point2, Positions.tileSize1Point2, null);
				g2.drawImage(il.iconHeart, offsetX + Positions.tileSize0Point7, Positions.tileSize4Point5, Positions.tileSize0Point5, Positions.tileSize0Point5, null);
				g2.drawString(card.life + "", offsetX + Positions.tileSize0Point75, Positions.tileSize5Point4);
				setColorForStats(g2, card.atk, card.defaultCard.getAtk());
				g2.drawImage(il.iconAtk, offsetX + Positions.tileSize1Point4, Positions.tileSize4Point5, Positions.tileSize0Point5, Positions.tileSize0Point5, null);
				g2.drawString(card.atk + "", offsetX + Positions.tileSize1Point45, Positions.tileSize5Point4);
				g2.drawImage(il.getArtIconForArt(card.art, false), offsetX + Positions.tileSize0Point1, Positions.tileSize4Point68, Positions.tileSize0Point7, Positions.tileSize0Point7, null);

				if (cg.cardGameState.isState(State.selectCardToAttackState)) {
					g2.drawImage(card.defaultCard.getCardSelectRed().get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
				}
			}

			if (card.blockAttackOnTurn) {
				g2.drawImage(gp.imageLoader.iconBlockAtk, offsetX + Positions.tileSize, y, Positions.tileSize, Positions.tileSize, null);
			}

			if (cg.cardGameState.isState(State.effektSelectOponentBoardState)) {
				if (cg.activeEffektCard.isCardValidForSelection(card)) {
					g2.drawImage(card.defaultCard.getCardSelectRed().get(), offsetX, y, Positions.tileSize2, Positions.tileSize3, null);
				}
			}

			if (cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.effektSelectOponentBoardState) || cg.cardGameState.isState(State.selectCardToAttackState)) {
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
		if (cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.effektSelectOponentBoardState) || cg.cardGameState.isState(State.selectCardToAttackState)) {
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
			g2.setColor(Colors.transparent);
			// Berechne den relativen Winkel zur mittleren Karte
			double angle = (i - middleIndex) * angleStep;
			
			// Berechne die X- und Y-Position auf einem vertikalen Bogen nach unten
			x = (int) (Positions.tileSize25 + Math.sin(angle) * Positions.tileSize6);  // X-Position basierend auf dem Winkel
			y = (int) (Math.cos(angle) - Positions.tileSize2Point5);  // Y-Position nach unten hin basierend auf dem Winkel
			
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
	
	public void drawPlayerGrave(Graphics2D g2) {
		if (cg.player.graveCards.size() > 0) {
			CardState card = cg.player.graveCards.get(cg.player.graveCards.size() - 1);
			g2.drawImage(card.defaultCard.getImage(), Positions.tileSize31, Positions.tileSize9Point5, Positions.tileSize2, Positions.tileSize3, null);
    		g2.setPaint(Colors.gardianSelectFromGrave);
			g2.fillRect(Positions.tileSize31, Positions.tileSize9Point5, Positions.tileSize2, Positions.tileSize3);
		}

		if (cg.cardGameState.isState(State.graveState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), Positions.tileSize31, Positions.tileSize9Point5, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize28, Positions.tileSize9Point5, Positions.tileSize3, Positions.tileSize3, null);
		}
	}

	public void drawOponentGrave(Graphics2D g2) {
		if (cg.oponent.graveCards.size() > 0) {
			CardState card = cg.oponent.graveCards.get(cg.oponent.graveCards.size() - 1);
			g2.drawImage(card.defaultCard.getImageReverse(), Positions.tileSize31, Positions.tileSize6, Positions.tileSize2, Positions.tileSize3, null);
    		g2.setPaint(Colors.gardianSelectFromGrave);
			g2.fillRect(Positions.tileSize31, Positions.tileSize6, Positions.tileSize2, Positions.tileSize3);
		}

		if (cg.cardGameState.isState(State.graveOponentState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), Positions.tileSize31, Positions.tileSize6, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize28, Positions.tileSize6, Positions.tileSize3, Positions.tileSize3, null);
		}
	}
	
	public void drawPlayerSpellGrave(Graphics2D g2) {
		if (cg.player.spellGraveCards.size() > 0) {
			CardState card = cg.player.spellGraveCards.get(cg.player.spellGraveCards.size() - 1);
			g2.drawImage(card.defaultCard.getImage(), Positions.tileSize36, Positions.tileSize9Point5, Positions.tileSize2, Positions.tileSize3, null);
    		g2.setPaint(Colors.gardianSelectFromGrave);
			g2.fillRect(Positions.tileSize36, Positions.tileSize9Point5, Positions.tileSize2, Positions.tileSize3);
		}

		if (cg.cardGameState.isState(State.spellGraveState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), Positions.tileSize36, Positions.tileSize9Point5, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize33, Positions.tileSize9Point5, Positions.tileSize3, Positions.tileSize3, null);
		}
	}

	public void drawOponentSpellGrave(Graphics2D g2) {
		if (cg.oponent.spellGraveCards.size() > 0) {
			CardState card = cg.oponent.spellGraveCards.get(cg.oponent.spellGraveCards.size() - 1);
			g2.drawImage(card.defaultCard.getImageReverse(), Positions.tileSize36, Positions.tileSize6, Positions.tileSize2, Positions.tileSize3, null);
    		g2.setPaint(Colors.gardianSelectFromGrave);
			g2.fillRect(Positions.tileSize36, Positions.tileSize6, Positions.tileSize2, Positions.tileSize3);
		}

		if (cg.cardGameState.isState(State.spellGraveOponentState)) {
			g2.drawImage(gp.imageLoader.selectedCardHover.get(), Positions.tileSize36, Positions.tileSize6, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize33, Positions.tileSize6, Positions.tileSize3, Positions.tileSize3, null);
		}
	}

	public void drawPlayerGraveSelected(Graphics2D g2) {
		if (cg.cardGameState.isState(State.graveSelectedState) || cg.cardGameState.isState(State.effektSelectOwnGraveState)) {
			drawDialog(Positions.tileSize18, Positions.tileSize4Point68, Positions.tileSize5, Positions.tileSize10, g2);
			CardState card = cg.player.graveCards.get(cg.selectedIdx);
			g2.drawImage(card.defaultCard.getImage(), Positions.tileSize19Point5, Positions.tileSize8, Positions.cardWidth, Positions.cardHeight, null);

			if (cg.cardGameState.isState(State.graveSelectedState) && cg.isEffektManualActivatable(cg.player, card, Trigger.triggerManualFromGrave)) {
				g2.drawImage(gp.imageLoader.instractionKeyboardG.get(), Positions.tileSize18Point5, Positions.tileSize5, Positions.tileSize4, Positions.tileSize2, null);
				g2.setColor(Color.WHITE);
				g2.setFont(Main.v.brushedFont15);
				g2.drawString(gp.t("effektAktivieren"), Positions.tileSize19, Positions.tileSize6Point2);
			}
			if (cg.selectedIdx == cg.player.graveCards.size() - 1) {
				g2.drawImage(gp.imageLoader.navigationArrowRightDisabled, Positions.tileSize20 , Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowRight, Positions.tileSize20, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			}

			if (cg.selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.navigationArrowLeftDisabled, Positions.tileSize18, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowLeft, Positions.tileSize18, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			}
		}
	}

	public void drawOponentGraveSelected(Graphics2D g2) {
		if (cg.cardGameState.isState(State.graveSelectedOponentState) || cg.cardGameState.isState(State.effektSelectOponentGraveState)) {
			drawDialog(Positions.tileSize18, Positions.tileSize4Point68, Positions.tileSize5, Positions.tileSize10, g2);
			CardState card = cg.oponent.graveCards.get(cg.selectedIdx);
			g2.drawImage(card.defaultCard.getImage(), Positions.tileSize19Point5, Positions.tileSize8, Positions.cardWidth, Positions.cardHeight, null);

			if (cg.cardGameState.isState(State.graveSelectedState) && cg.isEffektManualActivatable(cg.oponent, card, Trigger.triggerManualFromGrave)) {
				g2.drawImage(gp.imageLoader.instractionKeyboardG.get(), Positions.tileSize18Point5, Positions.tileSize5, Positions.tileSize4, Positions.tileSize2, null);
				g2.setColor(Color.WHITE);
				g2.setFont(Main.v.brushedFont15);
				g2.drawString(gp.t("effektAktivieren"), Positions.tileSize19, Positions.tileSize6Point2);
			}
			if (cg.selectedIdx == cg.oponent.graveCards.size() - 1) {
				g2.drawImage(gp.imageLoader.navigationArrowRightDisabled, Positions.tileSize20 , Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowRight, Positions.tileSize20, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			}

			if (cg.selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.navigationArrowLeftDisabled, Positions.tileSize18, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowLeft, Positions.tileSize18, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			}
		}
	}

	public void drawSelectOptionCards(Graphics2D g2) {
		if (cg.cardGameState.isState(State.selectOptionCardListState)) {
			drawDialog(Positions.tileSize18, Positions.tileSize4Point68, Positions.tileSize5, Positions.tileSize10, g2);
			CardState card = cg.optionsCardsToSelect.get(cg.selectedIdx);
			g2.drawImage(card.defaultCard.getImage(), Positions.tileSize19Point5, Positions.tileSize8, Positions.cardWidth, Positions.cardHeight, null);

			if (cg.selectedIdx == cg.optionsCardsToSelect.size() - 1) {
				g2.drawImage(gp.imageLoader.navigationArrowRightDisabled, Positions.tileSize20 , Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowRight, Positions.tileSize20, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			}

			if (cg.selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.navigationArrowLeftDisabled, Positions.tileSize18, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			} else {
				g2.drawImage(gp.imageLoader.navigationArrowLeft, Positions.tileSize18, Positions.tileSize10Point8, Positions.tileSize3, Positions.tileSize3, null);
			}
		}
	}

	public void drawSelectOption(Graphics2D g2) {
		if (cg.cardGameState.isState(State.selectOptionState)) {
			drawDialog(Positions.tileSize18, Positions.tileSize4Point68, Positions.tileSize6, Positions.tileSize9, g2);

			int i = 0; 
			for (Map.Entry<String, String> entry : cg.optionsToSelect.entrySet()) {
				if (cg.selectedIdx == i) {
					g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize17Point5, Positions.tileSize5 + Positions.tileSize * i, Positions.tileSize2, Positions.tileSize2, null);
				}

				g2.setColor(Colors.getColorSelection(i, cg.selectedIdx));
				g2.drawString(gp.t(entry.getKey()), Positions.tileSize19Point5, Positions.tileSize6 + Positions.tileSize * i);
				i++; 
			}
		}
	}

	public void drawBoardBlocks(Graphics2D g2) {
		if (cg.player.blockAufrufOneTurnMensch) {
			g2.drawImage(gp.imageLoader.iconArtMenschBlock, Positions.tileSize26, Positions.tileSize18, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.oponent.blockAufrufOneTurnMensch) {
			g2.drawImage(gp.imageLoader.iconArtMenschBlock, Positions.tileSize20, Positions.tileSize, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.player.blockAufrufOneTurnTier) {
			g2.drawImage(gp.imageLoader.iconArtTierBlock, Positions.tileSize27, Positions.tileSize18, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.oponent.blockAufrufOneTurnTier) {
			g2.drawImage(gp.imageLoader.iconArtTierBlock, Positions.tileSize19, Positions.tileSize, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.player.blockAufrufOneTurnFabelwesen) {
			g2.drawImage(gp.imageLoader.iconArtFabelwesenBlock, Positions.tileSize28, Positions.tileSize18, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.oponent.blockAufrufOneTurnFabelwesen) {
			g2.drawImage(gp.imageLoader.iconArtFabelwesenBlock, Positions.tileSize18, Positions.tileSize, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.player.blockAufrufOneTurnNachtgestalt) {
			g2.drawImage(gp.imageLoader.iconArtNachtgestaltBlock, Positions.tileSize29, Positions.tileSize18, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.oponent.blockAufrufOneTurnNachtgestalt) {
			g2.drawImage(gp.imageLoader.iconArtNachtgestaltBlock, Positions.tileSize17, Positions.tileSize, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.player.blockAufrufOneTurnSegen) {
			g2.drawImage(gp.imageLoader.iconArtSegenBlock, Positions.tileSize30, Positions.tileSize18, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.oponent.blockAufrufOneTurnSegen) {
			g2.drawImage(gp.imageLoader.iconArtSegenBlock, Positions.tileSize16, Positions.tileSize, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.player.blockAufrufOneTurnFluch) {
			g2.drawImage(gp.imageLoader.iconArtFluchBlock, Positions.tileSize31, Positions.tileSize18, Positions.tileSize, Positions.tileSize, null);
		}
		if (cg.oponent.blockAufrufOneTurnFluch) {
			g2.drawImage(gp.imageLoader.iconArtFluchBlock, Positions.tileSize15, Positions.tileSize, Positions.tileSize, Positions.tileSize, null);
		}

		if (cg.player.blockEffektMenschen) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26, Positions.tileSize9Point2, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconMenschEffektBlock, Positions.tileSize26, Positions.tileSize9Point2, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		} 
		if (cg.player.blockEffektTiere) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26, Positions.tileSize10, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconTierEffektBlock, Positions.tileSize26, Positions.tileSize10, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		}  
		if (cg.player.blockEffektFabelwesen) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26, Positions.tileSize10Point8, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconFabelwesenEffektBlock, Positions.tileSize26, Positions.tileSize10Point8, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		}  
		if (cg.player.blockEffektNachtgestalten) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26, Positions.tileSize11Point6, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconNachtgesteltEffektBlock, Positions.tileSize26, Positions.tileSize11Point6, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		}  

		if (cg.oponent.blockEffektMenschen) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26, Positions.tileSize8Point2, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconMenschEffektBlock, Positions.tileSize26, Positions.tileSize8Point2, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		}  	
		if (cg.oponent.blockEffektTiere) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26, Positions.tileSize7Point4, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconTierEffektBlock, Positions.tileSize26, Positions.tileSize7Point4, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		} 
		if (cg.oponent.blockEffektFabelwesen) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26, Positions.tileSize6Point6, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconFabelwesenEffektBlock, Positions.tileSize26, Positions.tileSize6Point6, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		} 	
		if (cg.oponent.blockEffektNachtgestalten) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26, Positions.tileSize5Point8, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconNachtgesteltEffektBlock, Positions.tileSize26, Positions.tileSize5Point8, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		} 
		

		if (cg.player.blockAngriffMenschen) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26Point8, Positions.tileSize9Point2, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconMenschenAngriffBlock, Positions.tileSize26Point8, Positions.tileSize9Point2, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		} 
		if (cg.player.blockAngriffTiere) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26Point8, Positions.tileSize10, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconTierAngriffBlock, Positions.tileSize26Point8, Positions.tileSize10, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		}  
		if (cg.player.blockAngriffFabelwesen) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26Point8, Positions.tileSize10Point8, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconFabelwesenAngriffBlock, Positions.tileSize26Point8, Positions.tileSize10Point8, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		}  
		if (cg.player.blockAngriffNachtgestalten) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26Point8, Positions.tileSize11Point6, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconNachtgestaltenAngriffBlock, Positions.tileSize26Point8, Positions.tileSize11Point6, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		}  

		if (cg.oponent.blockAngriffMenschen) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26Point8, Positions.tileSize8Point2, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconMenschenAngriffBlock, Positions.tileSize26Point8, Positions.tileSize8Point2, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		}  	
		if (cg.oponent.blockAngriffTiere) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26Point8, Positions.tileSize7Point4, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconTierAngriffBlock, Positions.tileSize26Point8, Positions.tileSize7Point4, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		} 
		if (cg.oponent.blockAngriffFabelwesen) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26Point8, Positions.tileSize6Point6, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconFabelwesenAngriffBlock, Positions.tileSize26Point8, Positions.tileSize6Point6, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		} 	
		if (cg.oponent.blockAngriffNachtgestalten) {
			g2.drawImage(gp.imageLoader.paper01, Positions.tileSize26Point8, Positions.tileSize5Point8, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
			g2.drawImage(gp.imageLoader.iconNachtgestaltenAngriffBlock, Positions.tileSize26Point8, Positions.tileSize5Point8, Positions.tileSize0Point8, Positions.tileSize0Point8, null);
		} 
	}

	public void drawSelectedCard(Graphics2D g2, List<CardState> cards, int idx, boolean isPlayer) {
		if (cards.size() > 0 && cards.size() > idx) {
			CardState card = cards.get(idx);
			if (card.isHide && !isPlayer) {
				g2.drawImage(gp.imageLoader.cardBackgroundImage, Positions.tileSize, Positions.tileSize, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
			} else {
				selectedCard.drawCardState(g2, card);
			}
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
			int x = Positions.tileSize10;
			g2.drawImage(card.defaultCard.getImage(), x, Positions.tileSize7, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(il.iconEffektAvailable, Positions.tileSize10Point5, Positions.tileSize9, Positions.tileSize, Positions.tileSize, null);
			g2.drawImage(card.defaultCard.getCardIsEffektIsPossible().get(), x, Positions.tileSize7, Positions.tileSize2, Positions.tileSize3, null);
			g2.setColor(Color.YELLOW);
			g2.setFont(Main.v.brushedFont15);
			g2.drawString(gp.t("effektAktiviert"), x, Positions.tileSize10Point5);

			g2.setColor(Color.ORANGE);

			String text = card.defaultCard.getBeschreibung();
			String[] lines = text.split("\n");

			int y = Positions.tileSize11;
			int lineHeight = g2.getFontMetrics().getHeight(); // Höhe jeder Zeile basierend auf der aktuellen Schriftart

			for (String line : lines) {
				g2.drawString(line, x, y);
				y += lineHeight; // Y-Position für die nächste Zeile erhöhen
}
			//Draw Select kümmert sich drum dass das nicht mehr angezeigt wird
			if (card.selectState != State.effektSelectOponentBoardState && 
				card.selectState != State.effektSelectOwnBoardState && 
				card.selectState != State.effektSelectOponentGraveState && 
				card.selectState != State.effektSelectOwnGraveState && 
				card.selectState != State.selectOptionState &&				
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
			g2.drawImage(addedCardToHandPlayer.get(0).defaultCard.getImage(), Positions.tileSize30, Positions.tileSize16, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(il.iconArrowLeft, Positions.tileSize28, Positions.tileSize16Point7, Positions.tileSize1Point5, Positions.tileSize1Point5, null);
			g2.drawImage(il.iconHand, Positions.tileSize26Point5, Positions.tileSize16Point7, Positions.tileSize1Point5, Positions.tileSize1Point5, null);
			counterCardToHandPlayer++;
		}
	}

	private void drawAddedCardToHandOponent(Graphics2D g2) {
		if (counterCardToHandOponent >= 120) {
			addedCardToHandOponent.remove(0);
			counterCardToHandOponent = 0;
		} else {
			g2.drawImage(addedCardToHandOponent.get(0).defaultCard.getImage(), Positions.tileSize8, Positions.tileSize2, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(il.iconArrowRight, Positions.tileSize10, Positions.tileSize2Point7, Positions.tileSize1Point5, Positions.tileSize1Point5, null);
			g2.drawImage(il.iconHand, Positions.tileSize11Point5, Positions.tileSize2Point7, Positions.tileSize1Point5, Positions.tileSize1Point5, null);
			counterCardToHandOponent++;
		}
	}

	private void drawAddCardToGraveOponent(Graphics2D g2) {
		if (counterCardToGraveOponent >= 120) {
			addCardToGraveOponent.remove(0);
			counterCardToGraveOponent = 0;
		} else {
			g2.drawImage(addCardToGraveOponent.get(0).defaultCard.getImage(), Positions.tileSize37, Positions.tileSize6, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(il.iconArrowLeft, Positions.tileSize35, Positions.tileSize7Point5, Positions.tileSize1Point5, Positions.tileSize1Point5, null);
			g2.drawImage(il.iconGrave, Positions.tileSize33Point5, Positions.tileSize7Point5, Positions.tileSize1Point5, Positions.tileSize1Point5, null);
			counterCardToGraveOponent++;
		}
	}

	private void drawAddCardToGravePlayer(Graphics2D g2) {
		if (counterCardToGravePlayer >= 120) {
			addCardToGravePlayer.remove(0);
			counterCardToGravePlayer = 0;
		} else {
			g2.drawImage(addCardToGravePlayer.get(0).defaultCard.getImage(), Positions.tileSize37, Positions.tileSize9Point5, Positions.tileSize2, Positions.tileSize3, null);
			g2.drawImage(il.iconArrowLeft, Positions.tileSize35, Positions.tileSize10Point5, Positions.tileSize1Point5, Positions.tileSize1Point5, null);
			g2.drawImage(il.iconGrave, Positions.tileSize33Point5, Positions.tileSize10Point5, Positions.tileSize1Point5, Positions.tileSize1Point5, null);
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
				g2.setFont(Main.v.brushedFont25);
				g2.drawString(gp.t("optionGewaehlt") + " " + this.effektTargetedOption.get(0), Positions.tileSize7, Positions.tileSize12);
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
				g2.drawImage(targetedCard.get(0).defaultCard.getImage(), Positions.tileSize12Point5, Positions.tileSize7Point5, Positions.tileSize1Point5, Positions.tileSize2Point5, null);
				g2.drawImage(il.cardTargeted.get(), Positions.tileSize12Point5, Positions.tileSize7Point5, Positions.tileSize1Point5, Positions.tileSize2Point5, null);
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
			if (cg.cardGameState.isState(State.handCardState)) {
				drawSelectedCard(g2, cg.player.handCards, cg.selectedIdx, true);
			} else if (cg.cardGameState.isState(State.handCardSelectedState) || cg.cardGameState.isState(State.effektQuestionStateHand)) {
				drawSelectedCard(g2, cg.player.handCards, cg.selectedHandCardIdx, true);
			} else if (cg.cardGameState.isState(State.boardState) || cg.cardGameState.isState(State.effektSelectOwnBoardState)) {
				drawSelectedCard(g2, cg.player.boardCards, cg.selectedIdx, true);
			} else if (cg.cardGameState.isState(State.boardCardSelectedState) || cg.cardGameState.isState(State.effektQuestionStateBoard)) {
				drawSelectedCard(g2, cg.player.boardCards, cg.selectedBoardCardIdx, true);
			} else if (cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.selectCardToAttackState) || cg.cardGameState.isState(State.effektSelectOponentBoardState)) {
				drawSelectedCard(g2, cg.oponent.boardCards, cg.selectedIdx, false);
			} else if (cg.cardGameState.isState(State.graveSelectedState) || cg.cardGameState.isState(State.effektSelectOwnGraveState)) {
				drawSelectedCard(g2, cg.player.graveCards, cg.selectedIdx, true);
			}  else if (cg.cardGameState.isState(State.graveSelectedOponentState) || cg.cardGameState.isState(State.effektSelectOponentGraveState)) {
				drawSelectedCard(g2, cg.oponent.graveCards, cg.selectedIdx, false);
			} else if (cg.cardGameState.isState(State.effektQuestionStateGrave)) {
				drawSelectedCard(g2, cg.player.graveCards, cg.selectGraveCardIdx, true);
			} else if (cg.cardGameState.isState(State.graveState)) {
				drawSelectedCard(g2, cg.player.graveCards, cg.player.graveCards.size() - 1, true);
			} else if (cg.cardGameState.isState(State.graveOponentState)) {
				drawSelectedCard(g2, cg.oponent.graveCards, cg.oponent.graveCards.size() - 1, false);
			} else if (cg.cardGameState.isState(State.spellGraveState)) {
				drawSelectedCard(g2, cg.player.spellGraveCards, cg.player.spellGraveCards.size() - 1, true);
			} else if (cg.cardGameState.isState(State.spellGraveOponentState)) {
				drawSelectedCard(g2, cg.oponent.spellGraveCards, cg.oponent.spellGraveCards.size() - 1, false);
			} else if (cg.cardGameState.isState(State.selectOptionCardListState)) {
				drawSelectedCard(g2, cg.optionsCardsToSelect, cg.selectedIdx, true);
			}

			drawSelectOption(g2);
			drawSelectOptionCards(g2);
			drawHandCardSelected(g2);
			drawBoardCardSelected(g2);
			drawCardEffektQuestion(g2);
			drawBoardBlocks(g2);

			g2.setFont(Main.v.brushedFont25);
			
				if (!player.isOnTurn && !player.inactiveMode) {
				g2.setColor(Color.YELLOW);
				g2.drawString(gp.t("waehleZiel"), Positions.tileSize8, Positions.tileSize);
			} else if (player.isOnTurn && player.inactiveMode) {
				g2.setColor(Color.RED);
				g2.drawString(gp.t("gegnerWaehltZiel"), Positions.tileSize8, Positions.tileSize);
			} else if (player.isOnTurn)  {
				g2.setColor(Color.YELLOW);
				g2.drawString(gp.t("duBistDran"), Positions.tileSize8, Positions.tileSize);
				g2.drawString(gp.t("instractionZugBeenden"), Positions.tileSize8, Positions.tileSize2);

			} else if (!player.isOnTurn) {
				g2.setColor(Color.RED);
				g2.drawString(gp.t("gegnerIstDran"), Positions.tileSize8, Positions.tileSize);
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
				g2.fillRoundRect(0, 0, Positions.screenWidth, Positions.screenHeight, 35, 35);
				g2.setColor(Color.RED);
				g2.setFont(Main.v.brushedFont36);
				g2.drawString(gp.t("spielZuEnde"), Positions.tileSize16, Positions.tileSize12);
				g2.setColor(Color.YELLOW);
				g2.drawImage(gp.imageLoader.iconArrowMarker, Positions.tileSize17, Positions.tileSize13, Positions.tileSize2, Positions.tileSize2, null);
				g2.drawString(gp.t("ok"), Positions.tileSize19, Positions.tileSize14);
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
		g2.drawImage(angreifer.defaultCard.getImage(), Positions.tileSize8, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

		if (counterAttack < 15) {
			g2.drawImage(verteidiger.defaultCard.getImage(), Positions.tileSize25, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
		} else {
			g2.drawImage(destroyImage.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		}
		if (!destroyImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardDoppelzerstoerung(Graphics2D g2) {
		if (counterAttack < 15) {
			g2.drawImage(angreifer.defaultCard.getImage(), Positions.tileSize8, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
			g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

			g2.drawImage(verteidiger.defaultCard.getImage(), Positions.tileSize25, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
		} else {
			g2.drawImage(destroyImage.get(), Positions.tileSize8, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
			g2.drawImage(destroyImage2.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		}
		if (!destroyImage.isRunning && !destroyImage2.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardSelbstzerstoerung(Graphics2D g2) {
		if (counterAttack < 15) {
			g2.drawImage(angreifer.defaultCard.getImage(), Positions.tileSize8, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
			g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);
		} else {
			g2.drawImage(destroyImage.get(), Positions.tileSize8, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		}
		g2.drawImage(verteidiger.defaultCard.getImage(), Positions.tileSize25, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
		if (!destroyImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnCardSchaden(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.getImage(), Positions.tileSize8, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

		g2.drawImage(verteidiger.defaultCard.getImage(), Positions.tileSize25, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);

		if (counterAttack > 15) {
			g2.drawImage(gp.imageLoader.blinkRed.get(), Positions.tileSize25, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
			g2.drawImage(schadenImage.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		} 
		if (!schadenImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawDirectAttack(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.getImage(), Positions.tileSize8, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

		if (counterAttack > 15) {
			g2.drawImage(schadenImage.get(), Positions.tileSize25, Positions.tileSize8, Positions.tileSize6, Positions.tileSize6, null);
		} 
		if (!schadenImage.isRunning) {
			switchToGameBoard();
		}
	}

	private void drawAttackOnSchild(Graphics2D g2) {
		g2.drawImage(angreifer.defaultCard.getImage(), Positions.tileSize8, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);
		g2.drawImage(gp.imageLoader.iconAttackAvailable, Positions.tileSize9Point5, Positions.tileSize12, Positions.tileSize3, Positions.tileSize3, null);

		g2.drawImage(verteidiger.defaultCard.getImage(), Positions.tileSize25, Positions.tileSize6, Positions.cardWidth * 3, Positions.cardHeight * 3, null);

		if (counterAttack < 15) {
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

    public void showAnimKarteStatsAenderung(Player p, CardState card, boolean b) {
		return;
    }

    public void showHealCard(CardState card) {
		this.cardsToHeal.add(card);
    }

	private void drawHeal(Graphics2D g2, int x, int y, int width, int height, AnimImage animImage, CardState card) {
		g2.drawImage(animImage.get(), x, y, width, height, null);

		if (!animImage.isRunning) {
			cardsToHeal.remove(card);
			animImage.isRunning = true;
		}
	}
}