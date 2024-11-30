package com.cab.cardGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import com.cab.GamePanel;
import com.cab.card.Art;
import com.cab.card.Status;

public class CardGame {
	public GamePanel gp;
	public CardGameDrawer cd;
	public CardGameUpdater cu;
	public EffekteMangaer effekteMangaer;

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
	
	//States
	final int handCardState = 0;
	final int handCardSelectedState = 1;	
	final int boardState = 2;
	final int boardCardSelectedState = 3;	
	final int boardOponentState = 4;
	final int selectCardToAttackState = 5;
	final int graveState = 6;
	final int graveSelectedState = 7;
	final int graveOponentState = 8;
	final int graveSelectedOponentState = 9;
	final int effektSelectOponentBoardState = 10;
	final int effektSelectOponentGraveState = 11;
	final int effektSelectOwnBoardState = 12;
	final int effektSelectOwnGraveState = 13;
	final int effektQuestionStateBoard = 14;
	final int effektQuestionStateHand = 15;
	final int effektQuestionStateGrave = 16;
	final int spellGraveState = 17;
	final int spellGraveOponentState = 18;
	final int selectOptionState = 19;
	final int selectOptionCardListState = 20;

	final int onAufgbenState = 90;
	final int askAufgebenState = 91;
	final int gameFinishedState = 100;

	public int currentState;
	
	boolean continueToAttackPhaseTwo;
	boolean continueToAttackPhaseThree;

	public int savedIdPlayerAttack;
	public int savedIdOpAttack;

	public List<String> optionsToSelect;
	public List<CardState> optionsCardsToSelect;

	//Duell Logik
	boolean isOnTurn;
	boolean isFirstTurn;
	boolean inactiveMode; 
	public int numberOfCreatureCanPlayInTurn;
	
	List<Effekt> effektList;
	List<CardState> blockCardsOnBoard;

	boolean isResolving = false;


	public CardGame(GamePanel gp) {
		this.gp = gp;
	}
	
	public void createGame(List<Integer> stapelOponent, boolean isPlayerStart, boolean isOnline) {
		this.effekteMangaer = new EffekteMangaer(this);
		this.cd = new CardGameDrawer(this);
		this.cu = new CardGameUpdater(this, gp.keyH);

		// Alles resetten
		selectedIdx = 0;
		currentState= handCardState;
		numberOfCreatureCanPlayInTurn = 1;

		// Falls im letzten Duell die Werte wegen verbindungsabbruch nicht zurückgesetzt werden konnten
		continueToAttackPhaseTwo = false;
		continueToAttackPhaseThree = false;

		effektList = new ArrayList<>();
		blockCardsOnBoard = new ArrayList<>();

		this.player = new Player(gp.player.stapel, this, true);
		this.oponent = new Player(stapelOponent, this, false);

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

		gp.stopMuic();
		gp.playMusic(5);
	}

	public void send(Boolean send, Boolean isPlayer, Integer argIntOne, Integer argIntTwo, Boolean argBoolean, Boolean aBooleanTwo, Art art, int[] array, String argString, String msg) {
		if (send && isOnline) {
			gp.connection.send(isPlayer, argIntOne, argIntTwo, argBoolean, aBooleanTwo, art, array, argString, msg);
		}
	}

	public void switchState(int state) {
		selectedIdx = 0;
		currentState = state;
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

	public void addEffektToChain(int id, int trigger, int idArgForEffekt) {
		CardState effektCard = getCardOfId(id);
		Player p = getOwnerOfCard(effektCard);
		if (isEffektPossible(p, trigger, effektCard)) {
			effektList.add(new Effekt(p, id, trigger, idArgForEffekt));
		}
	}

	public void manualEffekt(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, "manualEffekt");
		CardState effektCard = getCardOfId(id);
		addEffektToChain(id, effektCard.triggerState, -1);
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
				resolve();
			}
		}
	}

	public void handleEffekt(int id, int idArgForEffekt, boolean isSelected) {
		isResolving = true;
		CardState effektCard = getCardOfId(id);
		if (effektCard.selectState == effekteMangaer.ignoreState || isSelected) {
			effektCard.effekt(idArgForEffekt);
			
			if (effektCard.defaultCard.isSpell) {
				Player p = getOwnerOfCard(effektCard);
				if (effektCard.art == Art.Segen) {
					spielerPunkteAendern(p, -effektCard.defaultCard.kosten, PunkteArt.Segen, true);
				} else if (effektCard.art == Art.Fluch) {
					spielerPunkteAendern(p, -effektCard.defaultCard.kosten, PunkteArt.Fluch, true);
				}
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
				}
			}
		}
	}
	
	//Move

	private void addCardToBoard(Player p, CardState card, boolean isHide, boolean isSpecial) {
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

			addEffektToChain(card.id, effekteMangaer.triggerKreaturAufrufen, -1);

			for (int i = 0; i < p.boardCards.size(); i++) {
				addEffektToChain(p.boardCards.get(i).id, effekteMangaer.triggerOnBoardPlayerKreaturAufgerufen, card.id);
			}
	
			for (int i = 0; i < getOpOfP(p).boardCards.size(); i++) {
				addEffektToChain(getOpOfP(p).boardCards.get(i).id, effekteMangaer.triggerOnBoardOponentKreaturAufgerufen, card.id);
			}
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
		
		for (CardState c : p.boardCards) {
			addEffektToChain(c.id, effekteMangaer.triggerOnAddKreaturToGrave, card.id);
		}
		for (CardState c : getOpOfP(p).boardCards) {
			addEffektToChain(c.id, effekteMangaer.triggerOnAddKreaturToGrave, card.id);
		}

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
	public void kartenZiehen(Player p, int numberOfCards, boolean send) {
		send(send, p.isPlayer, numberOfCards, null, null, null, null, null, null, "moveCardFromStapelToHand");
		for (int i = 0; i < numberOfCards; i++) {
			if (p.stapel.size() > 0 && p.handCards.size() < limitCardsInHand) {
				int idx = p.stapel.size() - 1;
				CardState card = p.stapel.get(idx);
				removeCardFromStapel(p, card);
				addCardToHand(p, card, false);
				gp.playSE(1);	
			} 
		}
		resolve();
	}

	public void karteVonStapelAufDieHand(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "karteVonStapelAufDieHand");
		CardState card = getCardOfId(id);
		removeCardFromStapel(p, card);
		addCardToHand(p, card, true);
		resolve();
	}

	public void kreaturAufrufen(Player p, int id, boolean hide, boolean isSpecial, boolean send) {
		send(send, p.isPlayer, id, null, hide, isSpecial, null, null, null, "moveKreaturFormHandToBoard");
		if (!isSpecial) {
			--numberOfCreatureCanPlayInTurn;
		}

		CardState card = getCardOfId(id);
		removeCardFromHand(p, card);
		addCardToBoard(p, card, hide, isSpecial);
		gp.playSE(1);	

		resolve();
	}

	public void kreaturVomFriedhofInDieHandNehmen(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromGraveToHand");
		CardState card = getCardOfId(id);
		removeCardFromGrave(p, card);
		addCardToHand(p, card, true);
		gp.playSE(1);	
		resolve();
	}

	public void kreaturVomBoardInDieHandGeben(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromBoardToHand");
		CardState card = getCardOfId(id);
		removeCardFromBoard(p, card);
		addCardToHand(p, card, true);
		gp.playSE(1);	
		resolve();
	}

	public void kreaturAufrufenVomStapel(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromStapelToBoard");
		CardState card = getCardOfId(id);
		removeCardFromStapel(p, card);
		addCardToBoard(p, card, false, true);
		gp.playSE(1);	
		resolve();
	}

	public void kreaturVomFriedhofAufrufen(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromGraveToBoard");
		CardState card = getCardOfId(id);
		removeCardFromGrave(p, card);
		addCardToBoard(p, card, false, true);
		gp.playSE(1);	
		resolve();
	}

	public void kreaturVomBoardZerstoeren(Player p, int id, boolean send, boolean ignoreResolve) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "moveCardFromBoardToGrave");
		if (p.isPlayer) {
			switchState(graveState);
		} else {
			switchState(graveOponentState);
		}
		CardState card = getCardOfId(id);
		removeCardFromBoard(p, card);
		addCardToGrave(p, card, true);

		for (int i = 0; i < p.boardCards.size(); i++) {
			addEffektToChain(p.boardCards.get(i).id, effekteMangaer.triggerOnZerstoertKreaturZerstoert, card.id);
			addEffektToChain(p.boardCards.get(i).id, effekteMangaer.triggerOnZerstoertPlayerKreaturZerstoert, card.id);
		}

		for (int i = 0; i < getOpOfP(p).boardCards.size(); i++) {
			addEffektToChain(getOpOfP(p).boardCards.get(i).id, effekteMangaer.triggerOnZerstoertKreaturZerstoert, card.id);
			addEffektToChain(getOpOfP(p).boardCards.get(i).id, effekteMangaer.triggerOnZerstoertOponentKreaturZerstoert, card.id);
		}

		addEffektToChain(card.id, effekteMangaer.triggerAfterDestroyed, -1);

		//Wird bei einem Angriff verwendet und dort am ende resolved
		if (!ignoreResolve) {
			resolve();
		}
	}

	public void karteVonHandAufDenStapel(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, "moveCardFromHandToStapel");
		CardState card = p.handCards.get(idx);
		removeCardFromHand(p, card);
		addCardToStapel(p, card);
		gp.playSE(1);
		resolve();	
	}

	public void karteKontrolleUebernehmen(Player p, int opId, boolean send) {
		send(send, p.isPlayer, opId, null, null, null, null, null, null, "moveCardFromOponentBoardToOwnBoard");
		Player op = getOpOfP(p);
		CardState card = getCardOfId(opId);
		op.boardCards.remove(card);
		p.boardCards.add(card);
		updateBoardBlocks();
		gp.playSE(1);	
		resolve();
	}

	public void kartenTauschenHand(Player p, int idP, int idOp, boolean send) {
		send(send, p.isPlayer, idP, idOp, null, null, null, null, null, "switchHandCardsWithOponent");
		Player op = getOpOfP(p);
		CardState cardP = getCardOfId(idP);
		CardState cardOp = getCardOfId(idOp);
		removeCardFromHand(p, cardP);
		removeCardFromHand(op, cardOp);
		addCardToHand(p, cardOp, true);
		addCardToHand(op, cardP, true);
		gp.playSE(1);	
		resolve();
	}
	
	public void karteVonHandZerstoeren(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "karteVonHandZerstoeren");
		CardState card = getCardOfId(id);
		removeCardFromHand(p, card);
		if (card.defaultCard.isSpell) {
			addCardToSpellGrave(p, card);
		} else {
			addCardToGrave(p, card, true);
		}
		resolve();
	}

	public void karteVonHandAufSpellGrave(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, "karteVonHandAufSpellGrave");
		CardState card = getCardOfId(id);
		removeCardFromHand(p, card);
		addCardToSpellGrave(p, card);
		gp.playSE(1);
		resolve();	
	}
		
	public void direkterAngriff(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, "directAttack");
		CardState card = p.boardCards.get(idx);
		cd.showDirectAttack(card);

		card.hasAttackOnTurn = true;
		spielerPunkteAendern(getOpOfP(p), -card.atk, PunkteArt.Leben, false);

		if (getOpOfP(p).lifeCounter <= 0) {
			return;
		}

		addEffektToChain(card.id, effekteMangaer.triggerDirekterAngriff, -1);
		addEffektToChain(card.id, effekteMangaer.triggerAfterDoAttack, -1);

		for (int i = 0; i < getOpOfP(p).handCards.size(); i++) {
			addEffektToChain(getOpOfP(p).handCards.get(i).id, effekteMangaer.triggerOnHandDamageDirekterAngriff, card.id);
		}
		switchState(boardState);
		resolve();
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
		addEffektToChain(idPlayer, effekteMangaer.triggerAngriffSetupAngreifer, idOponent);
		addEffektToChain(idOponent, effekteMangaer.triggerAngriffSetupVerteidiger, idPlayer);

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

		addEffektToChain(angreifer.id, effekteMangaer.triggerBeforeKarteAngreift, angreifer.id);
		addEffektToChain(verteidiger.id, effekteMangaer.triggerBeforeKarteWirdAngegriffen, angreifer.id);

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

		angreifer.hasAttackOnTurn = true;
		
		addEffektToChain(verteidiger.id, effekteMangaer.triggerKarteWurdeAngegriffen, angreifer.id);

		if (verteidiger.isHide && verteidiger.atk > angreifer.atk) {
			cd.showAttackOnCardSelbstzerstoerung(angreifer, verteidiger);
			kreaturVomBoardZerstoeren(p, angreifer.id, false, true);
			addEffektToChain(angreifer.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
		} else if (verteidiger.isHide && verteidiger.atk == angreifer.atk) {
			cd.showAttackOnCardDoppelZerstoerung(angreifer, verteidiger);
			kreaturVomBoardZerstoeren(p, angreifer.id, false, true);
			addEffektToChain(angreifer.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
			addEffektToChain(angreifer.id, effekteMangaer.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
			kreaturVomBoardZerstoeren(op, verteidiger.id, false, true);
			addEffektToChain(verteidiger.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoert, verteidiger.id);
			addEffektToChain(verteidiger.id, effekteMangaer.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
		} else {
			if (verteidiger.statusSet.contains(Status.Schild)) {
				angreifer.hasAttackOnTurn = true;
				verteidiger.statusSet.remove(Status.Schild);
				cd.showAttackOnSchild(angreifer, verteidiger);
				switchState(boardState);
			} else if (verteidiger.life > angreifer.atk) {
				cd.showAttackOnCardSchaden(angreifer, verteidiger);
				verteidiger.life = verteidiger.life - angreifer.atk;
				addEffektToChain(angreifer.id, effekteMangaer.triggerSchadenZugefuegtDurchAngriff, verteidiger.id);
				addEffektToChain(angreifer.id, effekteMangaer.triggerAfterDoAttackAngreiferNochAufBoard, verteidiger.id);
			} else {
				cd.showAttackOnCardZersteorung(angreifer, verteidiger);
				kreaturVomBoardZerstoeren(op, verteidiger.id, false, true);
				addEffektToChain(verteidiger.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard, angreifer.id);
				addEffektToChain(verteidiger.id, effekteMangaer.triggerKarteWurdeDurchKampfZerstoert, angreifer.id);
				addEffektToChain(angreifer.id, effekteMangaer.triggerKarteHatDurchAngriffKarteZerstoert, verteidiger.id);
				addEffektToChain(angreifer.id, effekteMangaer.triggerAfterDoAttackAngreiferNochAufBoard, verteidiger.id);
			}

			addEffektToChain(verteidiger.id, effekteMangaer.triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard, angreifer.id);
		}

		verteidiger.isHide = false;	


		addEffektToChain(angreifer.id, effekteMangaer.triggerAfterDoAttack, verteidiger.id);

		angreifer.removeBeforeAttackEffekt(p);
		switchState(boardState);
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

				for (CardState card : player.boardCards) {
					addEffektToChain(card.id, effekteMangaer.triggerOnWin, -1);
				}
				resolve();
				switchState(gameFinishedState);
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
		card.isHide = isHide;
		if (isHide) {
			card.resetStatsToHide();
			removeBlockCardFromList(card);
		} else {
			card.setDefaultStatus();
			addBlockCardToList(card);
		}
		gp.playSE(1);	
		resolve();
	}

	public void karteSchaden(Player p, int id, int schaden, boolean send, boolean ignoreResolve) {
		send(send, p.isPlayer, id, schaden, null, null, null, null, null, "setSchadenOfBoardCard");
		CardState card = getCardOfId(id);
		if (card.life <= schaden) {
			kreaturVomBoardZerstoeren(p, id, false, true);
		} else {
			card.life = card.life - schaden;
			cd.showAnimKarteStatsAenderung(p, card, false);
		}
		if (!ignoreResolve) {
			resolve();
		}
	}

	public void karteHeilen(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, "setHeilenOfBoardCard");
		CardState card = getCardOfId(id);
		card.life = card.life + punkte;
		resolve();
	}

	public void karteAngriffVerringern(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, "setAtkVerringernOfCardOnBoard");
		CardState card = getCardOfId(id);
		if (punkte > card.atk) {
			card.atk = 0;
		} else {
			card.atk = card.atk - punkte;
		}
		resolve();
	}

	public void karteAngriffErhoehen(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, "setAtkErhoehenOfCardOnBoard");
		CardState card = getCardOfId(id);
		card.atk = card.atk + punkte;
		resolve();
	}

	public void setKarteStatus(int id, boolean isStatus, Status status, boolean send) {
		send(send, null, id, null, isStatus, null, null, null, status.toString(), "setKarteStatus");
		CardState card = getCardOfId(id);
		if (isStatus && !card.statusSet.contains(status)) {
			card.statusSet.add(status);
		} else if (!isStatus && card.statusSet.contains(status)) {
			card.statusSet.remove(status);			
		}
		resolve();
	}

	public void setArtOfCard(int id, Art art, boolean send) {
		send(send, null, id, null, null, null, art, null, null, "setArtOfCard");
		CardState card = getCardOfId(id);
		card.art = art;
		resolve();
	}

	public void setKarteBlockAttackOnTurn(int id, boolean isBlock, boolean send) {
		send(send, null, id, null, isBlock, null, null, null, null, "setBlockAttackOnTurn");
		CardState card = getCardOfId(id);
		card.blockAttackOnTurn = isBlock;
		resolve();
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

		for (CardState card : player.boardCards) {
			addEffektToChain(card.id, effekteMangaer.triggerOnStartRunde, -1);
		}
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
			kreaturVomBoardZerstoeren(p, id, false, true);
		}
	}

	//Get Methoden
	public Player getOwnerOfCard(CardState card) {
		Player[] players = {player, oponent};
	
		for (Player p : players) {
			List<List<CardState>> cardGroups = Arrays.asList(p.boardCards,p.handCards,p.graveCards,p.stapel);
	
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
					if (card.defaultCard.id == id) {
						return card;
					}
				}
			}
		}
		return null;
	}

	//Check Methoden

	public boolean containsCardId(List<CardState> cards, int id) {
		return cards.contains(getCardOfId(id));
	}

	public boolean containsSpecificCardId(List<CardState> cards, int id) {
		return cards.stream().anyMatch(card -> card.defaultCard.id == id);
	}

	public boolean isPlaySpellAllowed(Player p, CardState card) {
		Art art = card.art;
		boolean isArtBlocked = art == Art.Segen && p.blockAufrufOneTurnSegen || art == Art.Fluch && p.blockAufrufOneTurnFluch;
		return (!isArtBlocked && (card.art == Art.Fluch && card.defaultCard.kosten <= p.fluchCounter) || (card.art == Art.Segen && card.defaultCard.kosten <= p.segenCounter)) && card.isEffektPossible(p) && isOnTurn;
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

	public boolean checkIsAttackAlowed(Player p, int boardIdx) {
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
		return !card.defaultCard.isSpell && card.triggerState == manualTrigger && isEffektPossible(p, manualTrigger, card) && !card.isHide && isOnTurn && !inactiveMode;
	}
	
	public boolean isEffektPossible(Player p, int trigger, CardState card) {
		return card.isEffekt && card.isEffektPossible(p) && card.triggerState == trigger && !isEffektBlockiert(p, card);
	}

	public boolean isState(int state) {
		return currentState == state;
	}
	
	// Hilfsmethoden ohne send
	
	public void specificKreaturAusStapelOderHandAufrufen(Player p, int specificId) {
		if (containsSpecificCardId(p.handCards, specificId)) {
			kreaturAufrufen(p, getCardOfSpecificId(specificId).id, false, true, true);
		} else if (containsSpecificCardId(p.stapel, specificId)) {
			kreaturAufrufenVomStapel(p, getCardOfSpecificId(specificId).id, true);
		} else {
			throw new Error("specificKreaturAusStapelOderHandAufrufen Spezifische Karte mit der ID nicht gefunden: " + specificId);
		}
	} 

	public void specificKarteAusStapelinDieHand(Player p, int specificId) {
		removeCardFromStapel(p, getCardOfSpecificId(specificId));
		addCardToHand(p, getCardOfSpecificId(specificId), true);
	} 

	public void update() {
		cu.update();
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());     
		cd.draw(g2);
	}
	
	public void simulateOponentTurn() {
		kartenZiehen(oponent, 1, false);
	}
}
