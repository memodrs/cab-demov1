package com.cab.cardGame;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.cab.GamePanel;
import com.cab.card.Art;
import com.cab.card.Status;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Effekt;
import com.cab.cardGame.model.Player;

public class CardGame {
	public GamePanel gp;
	public CardGameDrawer cd;
	public CardGameUpdater cu;
	public CardGameState cardGameState;

	//Config
	int limitCardsInHand = 10;

	public boolean isOnline;
	public Player player;
	public Player oponent;
	
	//Selections	
	int selectedIdx;
	int selectedHandCardIdx;
	int selectedBoardCardIdx;
	int selectGraveCardIdx;

	CardState activeEffektCard;
	
	boolean continueToDirectAttack;
	boolean continueToAttackPhaseTwo;
	boolean continueToAttackPhaseThree;

	public int savedIdPlayerAttack;
	public int savedIdOpAttack;

	public Map<String, String> optionsToSelect;
	public List<CardState> optionsCardsToSelect;

	//Duell Logik
	boolean isOnTurn;
	boolean isFirstTurn;
	boolean inactiveMode; 
	public int numberOfCreatureCanPlayInTurn;
	
	List<Effekt> effektList;
	List<CardState> blockCardsOnBoard;

	boolean isResolving;

	public CardGame(GamePanel gp) {
		this.gp = gp;
	}
	
	public void createGame(List<Integer> stapelOponent, boolean isPlayerStart, boolean isOnline) {
		this.cardGameState = new CardGameState();
		this.cd = new CardGameDrawer(this);
		this.cu = new CardGameUpdater(this, gp.keyH);

		// Alles resetten
		switchState(State.handCardState);
		numberOfCreatureCanPlayInTurn = 1;

		// Falls im letzten Duell die Werte wegen verbindungsabbruch nicht zurückgesetzt werden konnten
		isResolving = false;
		continueToDirectAttack = false;
		continueToAttackPhaseTwo = false;
		continueToAttackPhaseThree = false;

		effektList = new ArrayList<>();
		blockCardsOnBoard = new ArrayList<>();

		EffektManager effektManager = new EffektManager(this);
		this.player = new Player(gp.player.stapel, effektManager, true);
		this.oponent = new Player(stapelOponent, effektManager, false);

		int startwertPlayer = isPlayerStart ? 0 : 100;
		int startwertOponent = isPlayerStart ? 100 : 0;
		for (int i = 0; i < player.stapel.size(); i++) {
			player.stapel.get(i).id = i + startwertPlayer;
		}
		for (int i = 0; i < oponent.stapel.size(); i++) {
			oponent.stapel.get(i).id = i + startwertOponent;
		}

		isOnTurn = isPlayerStart;
		isFirstTurn = isPlayerStart;
		inactiveMode = !isPlayerStart;
		this.isOnline = isOnline;

		// Duell Start
		kartenMischen(player, player.stapel, isOnline);
		kartenZiehen(player, 5, true);

		gp.playMusic(5);
	}

	public void switchState(int state) {
		selectedIdx = 0;
		cardGameState.setCurrentState(state);
	}

	public void send(Boolean send, Boolean isPlayer, Integer argIntOne, Integer argIntTwo, Boolean argBoolean, Boolean aBooleanTwo, Art art, int[] array, String argString, String msg) {
		if (send && isOnline) {
			gp.connection.send(isPlayer, argIntOne, argIntTwo, argBoolean, aBooleanTwo, art, array, argString, msg);
		}
	}

	//Board Blocks
	private void addBlockCardToList(CardState card) {
		blockCardsOnBoard.add(card);
		updateBoardBlocks();
	}

	private void removeBlockCardFromList(CardState card) {
		blockCardsOnBoard.remove(card);
		updateBoardBlocks();
	}

	private void updateBoardBlocks() {
		player.resetBlocks();
		oponent.resetBlocks();

		for (CardState card : blockCardsOnBoard) {
			Player p = null; 
			if (player.boardCards.contains(card)) {
				p = player;
			} else if (oponent.boardCards.contains(card)) {
				p = oponent;
			} else {
				throw new Error("Karte in der blockList auf keinem Board gefunden " + card.defaultCard.getName());
			}

			if (!isEffektBlockiert(p, card)) {
				card.setBlock(p);
			}
		}
	}

	//Effekt
	public void selectOptionFromList(String selectedOption, boolean send) {
		send(send, null, null, null, null, null, null, null, selectedOption, "selectOptionFromList");
		cd.showSelectedOption(selectedOption);
	}

	public void selectTargetCard(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "selectTargetCard");
		cd.showCardTargeted(getCardOfId(id));
	}

	public void addEffektToList(int id, int trigger, int idArgForEffekt) {
		CardState effektCard = getCardOfId(id);
		Player p = getOwnerOfCard(effektCard);
		if (isEffektPossible(p, trigger, effektCard)) {
			effektList.add(new Effekt(p, id, trigger, idArgForEffekt));
		}
	}

	public void addEffekteToList(List<CardState> cards, int trigger, int idArgForEffekt) {
		for (CardState card : cards) {
            addEffektToList(card.id, trigger, idArgForEffekt);
        }
	}

	public void manualEffekt(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, "manualEffekt");
		CardState effektCard = getCardOfId(id);
		addEffektToList(id, effektCard.triggerState, -1);
		resolve();
	}

	public void resolve() {
		if (effektList.size() > 0 && !isResolving) {
			isResolving = true;
			Effekt effekt = effektList.get(0);
			effektList.remove(0);
	
			if (isEffektPossible(effekt.p, effekt.trigger, getCardOfId(effekt.id))) {
				CardState effektCard = getCardOfId(effekt.id);
				effektCard.setIsEffektActivate(true);
				effektCard.setIsEffektActivateInTurn(true);
				activeEffektCard = effektCard;
				cd.showEffektCard(effektCard);
		
				if (effekt.p.isPlayer) {
					handleEffekt(effekt.id, effekt.idArgForEffekt, false);
				} else {
					inactiveMode = true;
				}
			} else if (effektList.size() > 0) {
				isResolving = false;
				resolve();
			}
		}
	}

	public void handleEffekt(int id, int idArgForEffekt, boolean isSelected) {
		isResolving = true;
		CardState effektCard = getCardOfId(id);
		if (effektCard.selectState == State.ignoreState || isSelected) {
			
			effektCard.effekt(idArgForEffekt);
			
			if (effektCard.defaultCard.isSpell()) {
				Player p = getOwnerOfCard(effektCard);
				spielerPunkteAendern(p, -effektCard.defaultCard.getKosten(), PunkteArt.valueOf(effektCard.art.toString()), true);
				karteVonHandAufSpellGrave(p, id, true);
			} 

			send(true, null, null, null, null, null, null, null, null, "resumeAfterEffekt");

			if (!isOnTurn) {
				inactiveMode = true;
			}
			switchState(effektCard.nextStateForPlayer);
			resumeState();
		} else {
			inactiveMode = false;
			activeEffektCard.setUpOptionsToSelect();
			switchState(effektCard.selectState);
		}
	}

	public void resumeState() {
		isResolving = false;
		if (effektList.size() > 0) {
			resolve();
		} else {
			if (isOnTurn) {
				inactiveMode = false;
				if (continueToAttackPhaseTwo) {
					attackPhaseTwo(player, savedIdPlayerAttack, savedIdOpAttack, true);
				} else if (continueToAttackPhaseThree) {
					attackPhaseThree(player, savedIdPlayerAttack, savedIdOpAttack, true);
				} else if (continueToDirectAttack) {
					direkterAngriff(player, savedIdPlayerAttack, true);
				}
			}
		}
	}
	

//Move

	private void addCardToBoard(Player p, CardState card, boolean isHide) {
		card.wasPlayedInTurn = true;
		card.isHide = isHide;
		p.boardCards.add(card);
		
		if (!isHide) {
			card.setDefaultStatus();
			addBlockCardToList(card);

			if (card.art == Art.Fabelwesen) {
				spielerPunkteAendern(p, 1, PunkteArt.Segen, false);
			} else if (card.art == Art.Nachtgestalt) {
				spielerPunkteAendern(p, 1, PunkteArt.Fluch, false);
			}

			addEffektToList(card.id, Trigger.triggerKreaturAufrufen, -1);
			addEffekteToList(p.boardCards, Trigger.triggerOnBoardPlayerKreaturAufgerufen, card.id);
			addEffekteToList(getOpOfP(p).boardCards, Trigger.triggerOnBoardOponentKreaturAufgerufen, card.id);
		} 
	}

	private void removeCardFromBoard(Player p, CardState card) {
		p.boardCards.remove(card);
		card.resetStatsToLeaveBoard();
		removeBlockCardFromList(card);	
	}

	private void addCardToStapel(Player p, CardState card) {
		p.stapel.add(card);
	}

	private void removeCardFromStapel(Player p, CardState card) {
		p.stapel.remove(card);
	}

	private void addCardToHand(Player p, CardState card, boolean show) {
		p.handCards.add(card);
		if (show) {
			cd.showSpecialAddCardToHand(p, card);
		}
	}

	private void removeCardFromHand(Player p, CardState card) {
		p.handCards.remove(card);
	}

	private void addCardToGrave(Player p, CardState card, boolean show) {
		p.graveCards.add(card);
		addEffekteToList(p.boardCards, Trigger.triggerOnAddKreaturToGrave, card.id);
		addEffekteToList(getOpOfP(p).boardCards, Trigger.triggerOnAddKreaturToGrave, card.id);

		if (show) {
			cd.showAddToGrave(p, card);
		}
	}

	private void removeCardFromGrave(Player p, CardState card) {
		p.graveCards.remove(card);
	}

	private void addCardToSpellGrave(Player p, CardState card) {
		p.spellGraveCards.add(card);
	}

//Domain

	//Stapel
	public void kartenZiehen(Player player, int numberOfCards, boolean send) {
		send(send, player.isPlayer, numberOfCards, null, null, null, null, null, null, "drawCard");
	
		for (int i = 0; i < numberOfCards; i++) {
			if (player.stapel.isEmpty() || player.handCards.size() >= limitCardsInHand) {
				break;
			}
	
			int index = player.stapel.size() - 1;
			CardState card = player.stapel.get(index);

			if (isCardInStapel(card)) {
				removeCardFromStapel(player, card);
				addCardToHand(player, card, false);
				gp.playSE(2); 
			}
		}
		resolve();
	}

	public void karteVonStapelAufHand(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "karteVonStapelAufDieHand");
		CardState card = getCardOfId(id);
		if (isCardInStapel(card)) {
			removeCardFromStapel(p, card);
			addCardToHand(p, card, true);
			resolve();
		}
	}

	public void karteVonStapelAufBoard(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromStapelToBoard");
		CardState card = getCardOfId(id);

		if (isCardInStapel(card)) {
			removeCardFromStapel(p, card);
			addCardToBoard(p, card, false);
			gp.playSE(2);	
			resolve();
		}
	}
	
	//Hand
	public void karteVonHandAufBoard(Player p, int id, boolean hide, boolean isSpecial, boolean send) {
		send(send, p.isPlayer, id, null, hide, isSpecial, null, null, null, "moveKreaturFormHandToBoard");
		CardState card = getCardOfId(id);
		if (isCardInHand(card)) {
			if (!isSpecial) {
				--numberOfCreatureCanPlayInTurn;
			}
			removeCardFromHand(p, card);
			addCardToBoard(p, card, hide);
			gp.playSE(2);	
			resolve();
		}
	}

	public void karteVonHandAufStapel(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, "moveCardFromHandToStapel");
		CardState card = p.handCards.get(idx);
		if (isCardInHand(card)) {
			removeCardFromHand(p, card);
			addCardToStapel(p, card);
			gp.playSE(1);
			resolve();	
		}
	}

	public void karteVonHandAufFriedhof(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "karteVonHandZerstoeren");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			removeCardFromHand(p, card);
			if (card.defaultCard.isSpell()) {
				addCardToSpellGrave(p, card);
			} else {
				addCardToGrave(p, card, true);
			}
			resolve();
		}
	}

	public void kartenTauschenHand(Player p, int idP, int idOp, boolean send) {
		send(send, p.isPlayer, idP, idOp, null, null, null, null, null, "switchHandCardsWithOponent");
		Player op = getOpOfP(p);
		CardState cardP = getCardOfId(idP);
		CardState cardOp = getCardOfId(idOp);

		if (isCardInHand(cardP) && isCardInHand(cardOp)) {
			removeCardFromHand(p, cardP);
			removeCardFromHand(op, cardOp);
			addCardToHand(p, cardOp, true);
			addCardToHand(op, cardP, true);
			gp.playSE(2);	
			resolve();
		}
	}
	
	public void karteVonHandAufSpellGrave(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "karteVonHandAufSpellGrave");
		CardState card = getCardOfId(id);

		if (isCardInHand(card)) {
			removeCardFromHand(p, card);
			addCardToSpellGrave(p, card);
			gp.playSE(1);
			resolve();	
		}
	}
	//Friedhof
	public void karteVomFriedhofInHand(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromGraveToHand");
		CardState card = getCardOfId(id);

		if (isCardInGrave(card)) {
			removeCardFromGrave(p, card);
			addCardToHand(p, card, true);
			gp.playSE(2);	
			resolve();
		}
	}

	public void karteVomFriedhofAufBoard(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromGraveToBoard");
		CardState card = getCardOfId(id);

		if (isCardInGrave(card)) {
			removeCardFromGrave(p, card);
			addCardToBoard(p, card, false);
			gp.playSE(2);	
			resolve();
		}
	}

	//Board
	public void karteVonBoardInHand(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromBoardToHand");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			removeCardFromBoard(p, card);
			addCardToHand(p, card, true);
			gp.playSE(2);	
			resolve();
		}
	}

	public void karteVomBoardInFriedhof(Player p, int id, boolean send, boolean ignoreResolve) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromBoardToGrave");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {	
			removeCardFromBoard(p, card);
			addCardToGrave(p, card, true);
	
			addEffektToList(card.id, Trigger.triggerAfterDestroyed, -1);

			addEffekteToList(p.boardCards, Trigger.triggerOnZerstoertKreaturZerstoert, card.id);
			addEffekteToList(p.boardCards, Trigger.triggerOnZerstoertPlayerKreaturZerstoert, card.id);

			addEffekteToList(getOpOfP(p).boardCards, Trigger.triggerOnZerstoertKreaturZerstoert, card.id);
			addEffekteToList(getOpOfP(p).boardCards, Trigger.triggerOnZerstoertOponentKreaturZerstoert, card.id);

			if (p.isPlayer) {
				switchState(State.graveState);
			} else {
				switchState(State.graveOponentState);
			}

			//Wird bei einem Angriff verwendet und dort am ende resolved
			if (!ignoreResolve) {
				resolve();
			}
		}


	}

	public void karteBoardKontrolleUebernehmen(Player p, int opId, boolean send) {
		send(send, p.isPlayer, opId, null, null, null, null, null, null, "moveCardFromOponentBoardToOwnBoard");
		Player op = getOpOfP(p);
		CardState card = getCardOfId(opId);

		if (isCardOnBoard(card)) {
			op.boardCards.remove(card);
			p.boardCards.add(card);
			updateBoardBlocks();
			gp.playSE(2);	
			resolve();
		}
	}

	public void setUpDirekterAngriff(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, "setUpDirectAttack");
		CardState card = p.boardCards.get(idx);
		savedIdPlayerAttack = card.id;

		addEffektToList(card.id, Trigger.triggerBeforeDirekterAngriff, -1);
		addEffekteToList(getOpOfP(p).handCards, Trigger.triggerOnHandBeforeDamageDirekterAngriff, card.id);

		if (effektList.size() > 0) {
			continueToDirectAttack = true;
			resolve();
		} else {
			direkterAngriff(p, card.id, false);
		}
	}
		
	public void direkterAngriff(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "directAttack");
		continueToDirectAttack = false;
		CardState card = getCardOfId(id);
		cd.showDirectAttack(card);

		card.hasAttackOnTurn = true;
		spielerPunkteAendern(getOpOfP(p), -card.atk, PunkteArt.Leben, false);

		if (getOpOfP(p).lifeCounter > 0) {
			addEffektToList(card.id, Trigger.triggerDirekterAngriff, -1);
			addEffektToList(card.id, Trigger.triggerAfterDoAttack, -1);
			addEffekteToList(getOpOfP(p).handCards, Trigger.triggerOnHandDamageDirekterAngriff, card.id);
			switchState(State.boardState);
			resolve();
		} //else duelle ist vorbei
	}

	public void changeSavedIdPlayerAttack(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, "changeSavedIdPlayerAttack");
		savedIdPlayerAttack = id;
	}

	public void changeSavedIdOponentAttack(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, "changeSavedIdOponentAttack");
		savedIdOpAttack = id;
	}

	public void attackPhaseOne(Player p, int idPlayer, int idOponent, boolean send) {
		send(send, p.isPlayer, idPlayer, idOponent, null, null, null, null, null, "attackPhaseOne");
		savedIdPlayerAttack = idPlayer;
		savedIdOpAttack = idOponent;
		addEffektToList(idPlayer, Trigger.triggerAngriffSetupAngreifer, idOponent);
		addEffektToList(idOponent, Trigger.triggerAngriffSetupVerteidiger, idPlayer);

		if (effektList.size() > 0) {
			continueToAttackPhaseTwo = true;
			resolve();
		} else {
			attackPhaseTwo(p, savedIdPlayerAttack, savedIdOpAttack, false);
		}
	}

	public void attackPhaseTwo(Player p, int idPlayer, int idOponent, boolean send) {
		send(send, p.isPlayer, savedIdPlayerAttack, savedIdOpAttack, null, null, null, null, null, "attackPhaseTwo");
		continueToAttackPhaseTwo = false;

		CardState angreifer = getCardOfId(savedIdPlayerAttack);
		CardState verteidiger = getCardOfId(savedIdOpAttack);

		addEffektToList(angreifer.id, Trigger.triggerBeforeKarteAngreift, angreifer.id);
		addEffektToList(verteidiger.id, Trigger.triggerBeforeKarteWirdAngegriffen, angreifer.id);

		if (effektList.size() > 0) {
			continueToAttackPhaseThree = true;
			resolve();
		} else {
			attackPhaseThree(p, savedIdPlayerAttack, savedIdOpAttack, false);
		}
	}

	public void attackPhaseThree(Player p, int idPlayer, int idOponent, boolean send) {
		send(send, p.isPlayer, idPlayer, idOponent, null, null, null, null, null, "attackPhaseThree");
		continueToAttackPhaseThree = false;
		
		Player op = getOpOfP(p);

		CardState verteidiger = getCardOfId(idOponent);
		CardState angreifer = getCardOfId(idPlayer);

		if (verteidiger.isHide && verteidiger.atk > angreifer.atk) {
			cd.showAttackOnCardSelbstzerstoerung(angreifer, verteidiger);
			karteVomBoardInFriedhof(p, angreifer.id, false, true);
			addEffektToList(angreifer.id, Trigger.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
			addEffektToList(angreifer.id, Trigger.triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard, verteidiger.id);
		} else if (verteidiger.isHide && verteidiger.atk == angreifer.atk) {
			cd.showAttackOnCardDoppelZerstoerung(angreifer, verteidiger);
			karteVomBoardInFriedhof(p, angreifer.id, false, true);
			addEffektToList(angreifer.id, Trigger.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
			addEffektToList(angreifer.id, Trigger.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
			karteVomBoardInFriedhof(op, verteidiger.id, false, true);
			addEffektToList(verteidiger.id, Trigger.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
			addEffektToList(verteidiger.id, Trigger.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
		} else {
			if (verteidiger.statusSet.contains(Status.Schild)) {
				verteidiger.statusSet.remove(Status.Schild);
				cd.showAttackOnSchild(angreifer, verteidiger);
				switchState(State.boardState);
			} else if (verteidiger.life > angreifer.atk) {
				cd.showAttackOnCardSchaden(angreifer, verteidiger);
				verteidiger.life = verteidiger.life - angreifer.atk;
				addEffektToList(angreifer.id, Trigger.triggerSchadenZugefuegtDurchAngriff, verteidiger.id);
				addEffektToList(angreifer.id, Trigger.triggerAfterDoAttackAngreiferNochAufBoard, verteidiger.id);
			} else {
				cd.showAttackOnCardZersteorung(angreifer, verteidiger);
				karteVomBoardInFriedhof(op, verteidiger.id, false, true);
				addEffektToList(angreifer.id, Trigger.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
				addEffektToList(angreifer.id, Trigger.triggerAfterDoAttackAngreiferNochAufBoard, verteidiger.id);

				addEffektToList(verteidiger.id, Trigger.triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard, angreifer.id);
				addEffektToList(verteidiger.id, Trigger.triggerKarteWurdeDurchKampfZerstoert, angreifer.id);
			}

			addEffektToList(verteidiger.id, Trigger.triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard, angreifer.id);
		}


		addEffektToList(verteidiger.id, Trigger.triggerKarteWurdeAngegriffen, angreifer.id);
		addEffektToList(angreifer.id, Trigger.triggerAfterDoAttack, verteidiger.id);

		verteidiger.isHide = false;	
		angreifer.hasAttackOnTurn = true;
		angreifer.removeBeforeAttackEffekt(p);
		switchState(State.boardState);
		resolve();
	}

	// Spieler
	
	public void spielerPunkteAendern(Player p, int punkte, PunkteArt art, boolean send) {
		send(send, p.isPlayer, punkte, null, null, null, null, null, art.toString(), "spielerPunkteAendern");

		if (art == PunkteArt.Fluch) {
			p.fluchCounter += punkte;

			if (p.fluchCounter < 0) {
				p.fluchCounter = 0;
			}
			
		} else if (art == PunkteArt.Segen) {
			p.segenCounter += punkte;

			if (p.segenCounter < 0) {
				p.segenCounter = 0;
			}

		} else if (art == PunkteArt.Leben) {
			p.lifeCounter += punkte;
			
			if (p.lifeCounter <= 0) {
				p.lifeCounter = 0;
				addEffekteToList(player.boardCards, Trigger.triggerOnWin, -1);
				resolve();
				switchState(State.gameFinishedState);
				return;
			}
		} else {
			throw new Error("Unbekannte Punkte Art " + art);
		}
	}

	public void setBlockAufrufArtNextTurn(Player p, boolean isBlock, Art art, boolean send) {
		send(send, p.isPlayer, null, null, isBlock, null, art, null, null, "setBlockAufrufArtNextTurn");
		switch (art) {
			case Mensch:
				p.blockAufrufOneTurnMensch = isBlock;
				break;
			case Tier:
				p.blockAufrufOneTurnTier = isBlock;
				break;
			case Fabelwesen:
				p.blockAufrufOneTurnFabelwesen = isBlock;	
				break;
			case Nachtgestalt:
				p.blockAufrufOneTurnNachtgestalt = isBlock;
				break;
			case Segen:
				p.blockAufrufOneTurnSegen = isBlock;
				break;
			case Fluch:
				p.blockAufrufOneTurnFluch = isBlock;
				break;
			default:
				break;
		}
		resolve();
	}

	// Spieler Karten Mischen 
	public void kartenMischen(Player p, List<CardState> cards, boolean send) {
		String posName = "";
		if (cards == p.boardCards) {
			posName = "board";
		} else if (cards == p.stapel) {
			posName = "stapel";
		} else if (cards == p.handCards) {
			posName = "hand";
		}
		List<Integer> reihenfolge = new ArrayList<>();
		for (int i = 0; i < cards.size(); i++) {
			reihenfolge.add(i);
		}
		Collections.shuffle(reihenfolge);
		int[] arg = reihenfolge.stream().mapToInt(Integer::intValue).toArray();
		
		sortKarten(p, arg, posName, send);
	}

	public void sortKarten(Player p, int[] reihenfolge, String posName, boolean send) {
		send(send, p.isPlayer, null, null, null, null, null, reihenfolge, posName, "sortCards");
		
		List<CardState> sortedList = new ArrayList<>();

		if (posName.equals("board")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(p.boardCards.get(reihenfolge[i]));
			}	
			p.boardCards = sortedList;	
		} else if (posName.equals("stapel")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(p.stapel.get(reihenfolge[i]));
			}	
			p.stapel = sortedList;	
		} else if (posName.equals("hand")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(p.handCards.get(reihenfolge[i]));
			}	
			p.handCards = sortedList;	
		} else {
			throw new Error("sortiere Liste, unbekannte posName " + posName);
		}
	}

	// Karten Operationen

	public void karteDrehen(int id, boolean isHide, boolean send) {
		send(send, null, id, null, isHide, null, null, null, null, "setHide");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			card.isHide = isHide;
			if (isHide) {
				card.resetStatsToHide();
				removeBlockCardFromList(card);
			} else {
				card.setDefaultStatus();
				addBlockCardToList(card);
			}
			gp.playSE(2);	
			resolve();
		}
	}

	public void karteSchaden(Player p, int id, int schaden, boolean send, boolean ignoreResolve) {
		send(send, p.isPlayer, id, schaden, null, null, null, null, null, "setSchadenOfBoardCard");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			if (card.life <= schaden) {
				karteVomBoardInFriedhof(p, id, false, true);
			} else {
				card.life = card.life - schaden;
				cd.showAnimKarteStatsAenderung(p, card, false);
			}
			if (!ignoreResolve) {
				resolve();
			}
		}
	}

	public void karteHeilen(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, "setHeilenOfBoardCard");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			card.life = card.life + punkte;
			resolve();
		}
	}

	public void karteAngriffVerringern(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, "setAtkVerringernOfCardOnBoard");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			if (punkte > card.atk) {
				card.atk = 0;
			} else {
				card.atk = card.atk - punkte;
			}
			resolve();
		}
	}

	public void karteAngriffErhoehen(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, "setAtkErhoehenOfCardOnBoard");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			card.atk = card.atk + punkte;
			resolve();
		}
	}

	public void setKarteStatus(int id, boolean isStatus, Status status, boolean send) {
		send(send, null, id, null, isStatus, null, null, null, status.toString(), "setKarteStatus");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			if (isStatus && !card.statusSet.contains(status)) {
				card.statusSet.add(status);
			} else if (!isStatus && card.statusSet.contains(status)) {
				card.statusSet.remove(status);			
			}
			resolve();
		}
	}

	public void setArtOfCard(int id, Art art, boolean send) {
		send(send, null, id, null, null, null, art, null, null, "setArtOfCard");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			card.art = art;
			resolve();
		}
	}

	public void setKarteBlockAttackOnTurn(int id, boolean isBlock, boolean send) {
		send(send, null, id, null, isBlock, null, null, null, null, "setBlockAttackOnTurn");
		CardState card = getCardOfId(id);

		if (isCardOnBoard(card)) {
			card.blockAttackOnTurn = isBlock;
			resolve();
		}
	}

	// Turn

	public void forceOponentToEndTurn() {
		send(true, null, null, null, null, null, null, null, null, "foreToEndTurn");
	}

	public void endTurn() {
		send(isOnline, null, null, null, null, null, null, null, null, "playerEndTurn");
		updateAllBoardCardsForPlayer(player);
		if (isFirstTurn) {
			isFirstTurn = false;
		}
		inactiveMode = true;
		isOnTurn = false;

		for (Art art : Art.values()) {
			setBlockAufrufArtNextTurn(player, false, art, true);
		}
		resolve();
	}

	public void startTurn() {
		kartenZiehen(player, 1, true);
		updateAllBoardCardsForPlayer(oponent);
		numberOfCreatureCanPlayInTurn = 1;
		inactiveMode = false;
		isOnTurn = true;
		addEffekteToList(player.boardCards, Trigger.triggerOnStartRunde, -1);
		resolve();
	}

	public void updateAllBoardCardsForPlayer(Player p) {
		List<Integer> cardsToSchaden = new ArrayList<>();
		List<Integer> cardsToZerstoeren = new ArrayList<>();

		for (CardState card : p.boardCards) {
			card.hasAttackOnTurn = false;
			card.wasPlayedInTurn = false;
			card.isEffectActivateInTurn = false;
			card.blockAttackOnTurn = false;
			
			if (card.statusSet.contains(Status.Gift)) {
				cardsToZerstoeren.add(card.id);
			}	

			if (card.statusSet.contains(Status.Feuer)) {
				cardsToSchaden.add(card.id);
			}					
		}
		for (Integer id : cardsToSchaden) {
			karteSchaden(p, id, 2, false, true);
		}
		for (Integer id : cardsToZerstoeren) {
			karteVomBoardInFriedhof(p, id, false, true);
		}
	}

	//Get Methoden
	public Player getOwnerOfCard(CardState card) {
		Player[] players = {player, oponent};
	
		for (Player p : players) {
			List<List<CardState>> cardGroups = Arrays.asList(p.boardCards, p.handCards, p.graveCards, p.stapel, p.spellGraveCards);
			for (List<CardState> cardGroup : cardGroups) {
				for (CardState c : cardGroup) {
					if (c.id == card.id) {
						return p;
					}
				}
			}
		}
		return null;
	}

	public Player getOpOfP(Player p) {
		return p.isPlayer? oponent : player;
	}

	public CardState getCardOfId(int id) {
		Player[] players = {player, oponent};
	
		for (Player p : players) {
			List<List<CardState>> cardGroups = Arrays.asList(p.boardCards,p.handCards,p.graveCards,p.stapel);
	
			for (List<CardState> cardGroup : cardGroups) {
				for (CardState card : cardGroup) {
					if (card.id == id) {
						return card;
					}
				}
			}
		}
		return null;
	}

	public CardState getCardOfSpecificId(int id) {
		Player[] players = {player, oponent};
	
		for (Player p : players) {
			List<List<CardState>> cardGroups = List.of(p.boardCards, p.handCards, p.graveCards, p.stapel);
	
			for (List<CardState> cardGroup : cardGroups) {
				for (CardState card : cardGroup) {
					if (card.defaultCard.getId() == id) {
						return card;
					}
				}
			}
		}
		return null;
	}

	//Check Methoden
	public boolean containsSpecificCardId(List<CardState> cards, int id) {
		return cards.stream().anyMatch(card -> card.defaultCard.getId() == id);
	}

	public boolean isPlaySpellAllowed(Player p, CardState card) {
		Art art = card.art;
		boolean isArtBlocked = art == Art.Segen && p.blockAufrufOneTurnSegen || art == Art.Fluch && p.blockAufrufOneTurnFluch;
		return (!isArtBlocked && (card.art == Art.Fluch && card.defaultCard.getKosten() <= p.fluchCounter) || (card.art == Art.Segen && card.defaultCard.getKosten() <= p.segenCounter)) && card.isEffektPossible(p) && isOnTurn;
	}

	public boolean isPlayCreatureAllowed(Player p, CardState card) {
		Art art = card.art;
		return numberOfCreatureCanPlayInTurn > 0 &&
				p.hasBoardPlace() &&
			   (art == Art.Mensch && !p.blockAufrufOneTurnMensch ||
				art == Art.Tier && !p.blockAufrufOneTurnTier ||
				art == Art.Fabelwesen && !p.blockAufrufOneTurnFabelwesen ||
				art == Art.Nachtgestalt && !p.blockAufrufOneTurnNachtgestalt);
	}

	private boolean isAngriffBlockiert(Player p, CardState card) {
		return (
			card.art == Art.Mensch && p.blockAngriffMenschen ||
			card.art == Art.Tier && p.blockAngriffTiere ||
			card.art == Art.Fabelwesen && p.blockAngriffFabelwesen ||
			card.art == Art.Nachtgestalt && p.blockAngriffNachtgestalten ||
			card.blockAttackOnTurn
		);	
	}

	public boolean isAttackAlowed(Player p, int boardIdx) {
		CardState card = p.boardCards.get(boardIdx);

		boolean isArtRulesAllowedAttack = false;
		if (card.art == Art.Fabelwesen) {
			isArtRulesAllowedAttack = p.hasArtOnBoard(Art.Mensch);
		} else if (card.art == Art.Nachtgestalt) {
			isArtRulesAllowedAttack = !p.hasArtOnBoard(Art.Mensch);
		} else {
			isArtRulesAllowedAttack = true;
		}
		
		return !card.isHide && !card.hasAttackOnTurn && isArtRulesAllowedAttack && !isFirstTurn && !isAngriffBlockiert(p, card) && !card.statusSet.contains(Status.Blitz) && isOnTurn;
	}

	private boolean isEffektBlockiert(Player p, CardState card) {
		return (
 			card.art == Art.Mensch && p.blockEffektMenschen || 
			card.art == Art.Tier && p.blockEffektTiere || 
			card.art == Art.Fabelwesen && p.blockEffektFabelwesen || 
			card.art == Art.Nachtgestalt && p.blockEffektNachtgestalten);
	}

	public boolean isEffektManualActivatable(Player p, CardState card, int manualTrigger) {
		return !card.defaultCard.isSpell() && card.triggerState == manualTrigger && isEffektPossible(p, manualTrigger, card) && !card.isHide && isOnTurn && !inactiveMode;
	}
	
	public boolean isEffektPossible(Player p, int trigger, CardState card) {
		return card.isEffekt && card.isEffektPossible(p) && card.triggerState == trigger && !isEffektBlockiert(p, card) && !card.isHide;
	}

	public boolean isCardInStapel(CardState card) {
		return getOwnerOfCard(card).stapel.contains(card);
	}

	public boolean isCardInHand(CardState card) {
		return getOwnerOfCard(card).handCards.contains(card);
	}

	public boolean isCardOnBoard(CardState card) {
		return getOwnerOfCard(card).boardCards.contains(card);
	}

	public boolean isCardInGrave(CardState card) {
		return getOwnerOfCard(card).graveCards.contains(card);
	}

	public boolean isCardInSpellGrave(CardState card) {
		return getOwnerOfCard(card).spellGraveCards.contains(card);
	}
	
	// Hilfsmethoden ohne send
	
	public void specificKreaturAusStapelOderHandAufrufen(Player p, int specificId) {
		if (containsSpecificCardId(p.handCards, specificId)) {
			karteVonHandAufBoard(p, getCardOfSpecificId(specificId).id, false, true, true);
		} else if (containsSpecificCardId(p.stapel, specificId)) {
			karteVonStapelAufBoard(p, getCardOfSpecificId(specificId).id, true);
		} else {
			throw new Error("specificKreaturAusStapelOderHandAufrufen Spezifische Karte mit der ID nicht gefunden: " + specificId);
		}
	} 
	
	public void update() {
		cu.update();
	}
	
	public void draw(Graphics2D g2) {  
		cd.draw(g2);
	}
}
