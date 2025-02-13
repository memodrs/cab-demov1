package com.cab.cardGame;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.cab.GamePanel;
import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.actions.*;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Effekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;
import com.cab.configs.Messages;
import com.cab.network.Connection;
import com.cab.states.GameState;

public class CardGame extends GameState {
	public GamePanel gp;
	public CardGameDrawer cd;
	public CardGameUpdater cu;

	public CardGameState cardGameState;

	public List<CardState> cardsOnBoard;

	//Config
	public int limitCardsInHand = 10;

	public KI ki;

	public boolean isSoundOn;
	public Connection connection;
	public boolean isOnline;
	public Player player;
	public Player oponent;
	

	//Selections	
	public int selectedIdx;
	public int selectedHandCardIdx;
	public int selectedBoardCardIdx;
	public int selectGraveCardIdx;


	public CardState activeEffektCard;
	
	public boolean continueToDirectAttack;
	public boolean continueToAttackPhaseTwo;
	public boolean continueToAttackPhaseThree;


	public int savedIdPlayerAttack;
	public int savedIdOpAttack;

	public Map<String, String> optionsToSelect;
	public List<CardState> optionsCardsToSelect;

	//Duell Logik
	public List<Effekt> effektList;

	public boolean isResolving;

	public CardGame(GamePanel gp) {
		this.gp = gp;
	}

	
	public void createGame(List<Integer> stapelOponent, boolean isPlayerStart, Connection connection) {
		cardsOnBoard = new ArrayList<>();
		effektList = new ArrayList<>();
		isResolving = false;
		continueToDirectAttack = false;
		continueToAttackPhaseTwo = false;
		continueToAttackPhaseThree = false;
		
		List<Card> cardsPlayer = new ArrayList<>();
		List<Card> cardsOponent = new ArrayList<>();

		for (Integer id : gp.player.stapel) {
			cardsPlayer.add(gp.cardLoader.getCard(id));
		}

		for (Integer id : stapelOponent) {
			cardsOponent.add(gp.cardLoader.getCard(id));
		}
		this.player = new Player(cardsPlayer, true, isPlayerStart);
		this.oponent = new Player(cardsOponent, false, isPlayerStart);

		this.connection = connection;

		if (this.connection == null) {
            this.isOnline = false;	
			this.ki = new KI(this);
		} else {
            this.isOnline = true;
		}

		this.cardGameState = new CardGameState();
		switchState(State.handCardState);

		this.cd = new CardGameDrawer(this);
		this.cu = new CardGameUpdater(this);
		isSoundOn = true;
		gp.playMusic(5);


		kartenMischen(player, player.stapel, true);
		kartenZiehen(player, 5, true);
	}

	public void switchState(int state) {
		selectedIdx = 0;
		cardGameState.setCurrentState(state);
	}

	public void send(Boolean send, Boolean isPlayer, Integer argIntOne, Integer argIntTwo, Boolean argBoolean, Boolean aBooleanTwo, Art art, int[] array, String argString, String msg) {
		if (send && isOnline) {
			connection.send(isPlayer, argIntOne, argIntTwo, argBoolean, aBooleanTwo, art, array, argString, msg);
		}
	}

	public void playSE(int soundId) {
		if (isSoundOn) {
			gp.playSE(soundId);
		}
	}

	public void startTurn(Player p) {
		kartenZiehen(p, 1, true);
		p.resetStatsOnStartTurn();
		addEffekteToList(p.boardCards, Trigger.triggerOnStartRunde, -1);
		resolve();
	}

	//Effekt
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
					player.inactiveMode = true;
				}

				if (effekt.p.isKI) {
					handleEffektKI(effekt.id, effekt.idArgForEffekt, false);
				}

			} else if (effektList.size() > 0) {
				isResolving = false;
				resolve();
			}
		}
	}

	public void handleEffektKI(int id, int idArgForEffekt, boolean isSelected) {
		isResolving = true;
		CardState effektCard = getCardOfId(id);
		Player p = getOwnerOfCard(effektCard);

		if (effektCard.selectState == State.ignoreState || isSelected) {
			
			effektCard.effekt(this, idArgForEffekt);

			if (effektCard.defaultCard.isSpell()) {
				spielerPunkteAendern(p, -effektCard.defaultCard.getKosten(), PunkteArt.valueOf(effektCard.art.toString()), true);
				karteVonHandAufSpellGrave(p, id, true);
			} 
			if (!p.isOnTurn) {
				p.inactiveMode = true;
			}
			p.inactiveMode = !p.isOnTurn;

			isResolving = false;
			if (effektList.size() > 0) {
				resolve();
			} else {
				if (p.isOnTurn) {
					if (continueToAttackPhaseTwo) {
						attackPhaseTwo(p, false);
					} else if (continueToAttackPhaseThree) {
						attackPhaseThree(p, false);
					} else if (continueToDirectAttack) {
						direkterAngriff(p, savedIdPlayerAttack, false);
					}
				}
			}
		} else {
			p.inactiveMode = false;
			optionsCardsToSelect = new ArrayList<>();
			optionsToSelect = new HashMap<>();
			activeEffektCard.setUpOptionsToSelect(this);
			ki.handleSelectState(effektCard.selectState);
		}
	}

	public void handleEffekt(int id, int idArgForEffekt, boolean isSelected) {
		isResolving = true;
		CardState effektCard = getCardOfId(id);
		Player p = getOwnerOfCard(effektCard);

		if (effektCard.selectState == State.ignoreState || isSelected) {
			effektCard.effekt(this, idArgForEffekt);
			if (effektCard.defaultCard.isSpell()) {
				spielerPunkteAendern(p, -effektCard.defaultCard.getKosten(), PunkteArt.valueOf(effektCard.art.toString()), true);
				karteVonHandAufSpellGrave(p, id, true);
			} 
			if (!player.isOnTurn) {
				player.inactiveMode = true;
			}

			send(true, null, null, null, null, null, null, null, null, "resumeAfterEffekt");
			switchState(effektCard.nextStateForPlayer);
			resumeState();
		} else {
			player.inactiveMode = false;
			optionsCardsToSelect = new ArrayList<>();
			optionsToSelect = new HashMap<>();
			activeEffektCard.setUpOptionsToSelect(this);
			switchState(effektCard.selectState);
		}
	}

	public void resumeState() {
		isResolving = false;
		if (effektList.size() > 0) {
			resolve();
		} else {
			if (player.isOnTurn) {
				player.inactiveMode = false;
				if (continueToAttackPhaseTwo) {
					attackPhaseTwo(player, true);
				} else if (continueToAttackPhaseThree) {
					attackPhaseThree(player, true);
				} else if (continueToDirectAttack) {
					direkterAngriff(player, savedIdPlayerAttack, true);
				}
			}
		}
	}
	

//Move
	public void addCardToBoard(Player p, CardState card, boolean isHide) {
		card.wasPlayedInTurn = true;
		card.isHide = isHide;
		p.boardCards.add(card);
		cardsOnBoard.add(card);

		if (!isHide) {
			card.setDefaultStatus();
			if (card.art == Art.Fabelwesen) {
				spielerPunkteAendern(p, 1, PunkteArt.Segen, false);
			} else if (card.art == Art.Nachtgestalt) {
				spielerPunkteAendern(p, 1, PunkteArt.Fluch, false);
			}
			updateBlocks();
			addEffektToList(card.id, Trigger.triggerKreaturAufrufen, -1);
			addEffekteToList(p.boardCards, Trigger.triggerOnBoardPlayerKreaturAufgerufen, card.id);
			addEffekteToList(getOpOfP(p).boardCards, Trigger.triggerOnBoardOponentKreaturAufgerufen, card.id);
		} 
	}

	public void removeCardFromBoard(Player p, CardState card) {
		p.boardCards.remove(card);
		cardsOnBoard.remove(card);
		updateBlocks();
		card.resetStatsToLeaveBoard();
	}

	public void addCardToStapel(Player p, CardState card) {
		p.stapel.add(card);
	}

	public void removeCardFromStapel(Player p, CardState card) {
		p.stapel.remove(card);
	}

	public void addCardToHand(Player p, CardState card, boolean show) {
		p.handCards.add(card);
		if (show) {
			cd.showSpecialAddCardToHand(p, card);
		}
	}

	public void removeCardFromHand(Player p, CardState card) {
		p.handCards.remove(card);
	}

	public void addCardToGrave(Player p, CardState card, boolean show) {
		p.graveCards.add(card);
		addEffekteToList(p.boardCards, Trigger.triggerOnAddKreaturToGrave, card.id);
		addEffekteToList(getOpOfP(p).boardCards, Trigger.triggerOnAddKreaturToGrave, card.id);

		if (show) {
			cd.showAddToGrave(p, card);
		}
	}

	public void removeCardFromGrave(Player p, CardState card) {
		p.graveCards.remove(card);
	}

	public void addCardToSpellGrave(Player p, CardState card) {
		p.spellGraveCards.add(card);
	}

	public void updateBlocks() {
		player.resetBlocks();
		oponent.resetBlocks();

		for (CardState card : cardsOnBoard) {
			Player owner = getOwnerOfCard(card);
			if (!owner.isEffektBlockiert(card)) {
				card.setBlock(owner, owner.isPlayer? oponent : player);
		}
	}
}

//Domain
	//EFFEKT
	public void manualEffekt(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, Messages.MANUAL_EFFEKT);
		new ManualEffekt(id).execute(this);
	}

	public void selectOptionFromList(String selectedOption, boolean send) {
		send(send, null, null, null, null, null, null, null, selectedOption, Messages.SELECT_OPTION_FROM_LIST);
		new SelectOptionFromList(selectedOption).execute(this);
	}

	public void selectTargetCard(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, Messages.SELECT_TARGET_CARD);
		new SelectTargetCard(id).execute(this);
	}

	//Stapel
	public void kartenZiehen(Player p, int numberOfCards, boolean send) {		
		send(send, p.isPlayer, numberOfCards, null, null, null, null, null, null, Messages.KARTEN_ZIEHEN);
		new KartenZiehen(p, numberOfCards).execute(this);
	}

	public void karteVonStapelAufHand(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_STAPEL_AUF_HAND);
		new KarteVonStapelAufHand(p, id).execute(this);
	}

	public void karteVonStapelAufBoard(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_STAPEL_AUF_BOARD);
		new KarteVonStapelAufBoard(p, id).execute(this);
	}
	
	//Hand
	public void karteVonHandAufBoard(Player p, int id, boolean hide, boolean isSpecial, boolean send) {
		send(send, p.isPlayer, id, null, hide, isSpecial, null, null, null, Messages.KARTE_VON_HAND_AUF_BOARD);
		new KarteVonHandAufBoard(p, id, hide, isSpecial).execute(this);
	}

	public void karteVonHandAufStapel(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_HAND_AUF_STAPEL);
		new KarteVonHandAufStapel(p, id).execute(this);;
	}

	public void karteVonHandAufFriedhof(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_HAND_AUF_FRIEDHOF);
		new KarteVonHandAufFriedhof(p, id).execute(this);
	}

	public void kartenTauschenHand(Player p, int idP, int idOp, boolean send) {
		send(send, p.isPlayer, idP, idOp, null, null, null, null, null, Messages.KARTEN_TAUSCHEN_HAND);
		new KartenTauschenHand(p, idP, idOp).execute(this);
	}
	
	public void karteVonHandAufSpellGrave(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_HAND_AUF_SPELL_GRAVE);
		new KarteVonHandAufSpellGrave(p, id).execute(this);
	}

	//Friedhof
	public void karteVomFriedhofInHand(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VOM_FRIEDHOF_IN_HAND);
		new KarteVomFriedhofInHand(p, id).execute(this);
	}

	public void karteVomFriedhofAufBoard(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VOM_FRIEDHOF_AUF_BOARD);
		new KarteVomFriedhofAufBoard(p, id).execute(this);
	}

	//Board
	public void karteVonBoardInHand(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VON_BOARD_IN_HAND);
		new KarteVonBoardInHand(p, id).execute(this);
	}

	public void karteVomBoardInFriedhof(Player p, int id, boolean send, boolean ignoreResolve) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.KARTE_VOM_BOARD_IN_FRIEDHOF);
		new KarteVomBoardInFriedhof(p, id, ignoreResolve).execute(this);
	}

	public void karteBoardKontrolleUebernehmen(Player p, int opId, boolean send) {
		send(send, p.isPlayer, opId, null, null, null, null, null, null, Messages.KARTE_BOARD_KONTROLLE_UEBERNEHMEN);
		new KarteBoardKontrolleUebernehmen(p, opId).execute(this);
	}

	public void setUpDirekterAngriff(Player p, int idx, boolean send) {
		send(send, p.isPlayer, idx, null, null, null, null, null, null, Messages.SETUP_DIREKTER_ANGRIFF);
		new SetUpDirekterAngriff(p, idx).execute(this);
	}

	public void direkterAngriff(Player p, int id, boolean send) {
		send(send, p.isPlayer, id, null, null, null, null, null, null, Messages.DIREKTER_ANGRIFF);
		new DirekterAngriff(p, id).execute(this);
	}

	public void changeSavedIdPlayerAttack(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, Messages.CHANGE_SAVED_ID_PLAYER_ATTACK);
		new ChangeSavedIdPlayerAttack(id).execute(this);
	}

	public void changeSavedIdOponentAttack(int id, boolean send) {
		send(send, null, id, null, null, null, null, null, null, Messages.CHANGE_SAVED_ID_OPONENT_ATTACK);
		new ChangeSavedIdOponentAttack(id).execute(this);
	}

	public void attackPhaseOne(Player p, int idPlayer, int idOponent, boolean send) {
		send(send, p.isPlayer, idPlayer, idOponent, null, null, null, null, null, Messages.ATTACK_PHASE_ONE);
		new AttackPhaseOne(p, idPlayer, idOponent).execute(this);
	}

	public void attackPhaseTwo(Player p, boolean send) {
		send(send, p.isPlayer, savedIdPlayerAttack, savedIdOpAttack, null, null, null, null, null, Messages.ATTACK_PHASE_TWO);
		new AttackPhaseTwo(p).execute(this);
	}

	public void attackPhaseThree(Player p, boolean send) {
		send(send, p.isPlayer, savedIdPlayerAttack, savedIdOpAttack, null, null, null, null, null, Messages.ATTACK_PHASE_THREE);
		new AttackPhaseThree(p).execute(this);
	}

	// Spieler
	public void spielerPunkteAendern(Player p, int punkte, PunkteArt art, boolean send) {
		send(send, p.isPlayer, punkte, null, null, null, null, null, art.toString(), Messages.SPIELER_PUNKTE_AENDERN);
		new SpielerPunkteAendern(p, punkte, art).execute(this);
	}

	public void setBlockAufrufArtNextTurn(Player p, boolean isBlock, Art art, boolean send) {
		send(send, p.isPlayer, null, null, isBlock, null, art, null, null, Messages.SET_BLOCK_AUFRUF_ART_NEXT_TURN);
		new SetBlockAufrufArtNextTurn(p, isBlock, art).execute(this);
	}

	public void sortKarten(Player p, int[] reihenfolge, String posName, boolean send) {
		send(send, p.isPlayer, null, null, null, null, null, reihenfolge, posName, Messages.SORT_KARTEN);
		new SortKarten(p, reihenfolge, posName).execute(this);
	}

	// Karten Operationen
	public void karteDrehen(int id, boolean isHide, boolean send) {
		send(send, null, id, null, isHide, null, null, null, null, Messages.KARTE_DREHEN);
		new KarteDrehen(id, isHide).execute(this);
	}

	public void karteSchaden(Player p, int id, int schaden, boolean send, boolean ignoreResolve) {
		send(send, p.isPlayer, id, schaden, null, null, null, null, null, Messages.KARTE_SCHADEN);
		new KarteSchaden(p, id, schaden, ignoreResolve).execute(this);
	}

	public void karteHeilen(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, Messages.KARTE_HEILEN);
		new KarteHeilen(id, punkte).execute(this);
	}

	public void karteAngriffVerringern(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, Messages.KARTE_ANGRIFF_VERRINGERN);
		new KarteAngriffVerringern(id, punkte).execute(this);
	}

	public void karteAngriffErhoehen(int id, int punkte, boolean send) {
		send(send, null, id, punkte, null, null, null, null, null, Messages.KARTE_ANGRIFF_ERHOEHEN);
		new KarteAngriffErhoehen(id, punkte).execute(this);
	}

	public void setKarteStatus(int id, boolean isStatus, Status status, boolean send) {
		send(send, null, id, null, isStatus, null, null, null, status.toString(), Messages.SET_KARTE_STATUS);
		new SetKarteStatus(id, isStatus, status).execute(this);
	}

	public void setArtOfCard(int id, Art art, boolean send) {
		send(send, null, id, null, null, null, art, null, null, Messages.SET_ART_OF_CARD);
		new SetArtOfCard(id, art).execute(this);
	}

	public void setKarteBlockAttackOnTurn(int id, boolean isBlock, boolean send) {
		send(send, null, id, null, isBlock, null, null, null, null, Messages.SET_KARTE_BLOCK_ATTACK_ON_TURN);
		new SetKarteBlockAttackOnTurn(id, isBlock).execute(this);
	}

	// Turn

	//TODO mit ki testen
	public void forceOponentToEndTurn() {
		send(true, oponent.isPlayer, null, null, null, null, null, null, null, Messages.FORCE_OPONENT_TO_END_TURN);
		new ForceOponentToEndTurn(oponent).execute(this);
	}

	public void endTurn(Player p) {
		send(isOnline, p.isPlayer, null, null, null, null, null, null, null, Messages.END_TURN);
		new EndTurn(p).execute(this);
	}

	//Get Methoden
	public Player getOwnerOfCard(CardState card) {
		return Stream.of(player, oponent)
			.filter(p -> Stream.of(p.boardCards, p.handCards, p.graveCards, p.stapel, p.spellGraveCards).anyMatch(group -> group.stream().anyMatch(c -> c.id == card.id)))
			.findFirst()
			.orElse(null);
	}

	public Player getOpOfP(Player p) {
		return p.isPlayer? oponent : player;
	}

	public CardState getCardOfId(int id) {
		return Stream.of(player, oponent)
			.flatMap(p -> Stream.of(p.boardCards, p.handCards, p.graveCards, p.stapel).flatMap(List::stream))
			.filter(card -> card.id == id)
			.findFirst()
			.orElse(null);
	}

	public CardState getCardOfSpecificId(int id) {
		return Stream.of(player, oponent)
			.flatMap(p -> Stream.of(p.boardCards, p.handCards, p.graveCards, p.stapel).flatMap(List::stream))
			.filter(card -> card.defaultCard.getId() == id)
			.findFirst()
			.orElse(null);
	}

	public boolean isEffektManualActivatable(Player p, CardState card, int manualTrigger) {
		return !card.defaultCard.isSpell() && card.triggerState == manualTrigger && isEffektPossible(p, manualTrigger, card) && !card.isHide && p.isOnTurn && !p.inactiveMode;
	}
	
	public boolean isEffektPossible(Player p, int trigger, CardState card) {
		return card.isEffekt && card.isEffektPossible(p, getOpOfP(p)) && card.triggerState == trigger && !p.isEffektBlockiert(card) && !card.isHide;
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
		if (p.hasSpecificCardInHand(specificId)) {
			karteVonHandAufBoard(p, getCardOfSpecificId(specificId).id, false, true, true);
		} else if (p.hasSpecificCardInStapel(specificId)) {
			karteVonStapelAufBoard(p, getCardOfSpecificId(specificId).id, true);
		} else {
			throw new Error("specificKreaturAusStapelOderHandAufrufen Spezifische Karte mit der ID nicht gefunden: " + specificId);
		}
	} 

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
	
	@Override
	public void update() {
		cu.update();
	}
	
	@Override
	public void draw(Graphics2D g2) {  
		cd.draw(g2);
	}
}
