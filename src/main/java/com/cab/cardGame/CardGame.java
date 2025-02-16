package com.cab.cardGame;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

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
		new KartenZiehen().execute(this, player, 5, true);
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

	public void startTurn(Player p) {
		new KartenZiehen().execute(this, p, 1, true);
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
			Effekt effekt = effektList.get(0);
			effektList.remove(0);
			CardState effektCard = getCardOfId(effekt.id);

			if (isEffektPossible(effekt.p, effekt.trigger, getCardOfId(effekt.id))) {
				effektCard.setIsEffektActivate(true);
				effektCard.setIsEffektActivateInTurn(true);
				activeEffektCard = effektCard;
				cd.showEffektCard(effektCard);
		
				if (effekt.p.isPlayer) {
					handleEffekt(effekt.id, effekt.idArgForEffekt, false);
				} else {
					player.inactiveMode = true;
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
		Player p = getOwnerOfCard(effektCard);

		if (effektCard.selectState == State.ignoreState || isSelected) {
			effektCard.effekt(this, idArgForEffekt);
			if (effektCard.defaultCard.isSpell()) {
				new SpielerPunkteAendern().execute(this, p, -effektCard.defaultCard.getKosten(), PunkteArt.valueOf(effektCard.art.toString()), true);
				new KarteVonHandAufSpellGrave().execute(this, p, id, true);				
			} 
			if (!player.isOnTurn) {
				player.inactiveMode = true;
			}

			send(true, null, null, null, null, null, null, null, null, "resumeAfterEffekt");
			switchState(effektCard.nextStateForPlayer);
			resumeState();
		} else {
			player.inactiveMode = false;
			optionsToSelect = new HashMap<>();
			effektCard.setupOptions(this);
			optionsCardsToSelect = new ArrayList<>(effektCard.getCardListToSelect(this));
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
					new AttackPhaseTwo().execute(this, player, true);
				} else if (continueToAttackPhaseThree) {
					new AttackPhaseThree().execute(this, player, true);
				} else if (continueToDirectAttack) {
					new DirekterAngriff().execute(this, player, savedIdPlayerAttack, true);
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
				new SpielerPunkteAendern().execute(this, p, 1, PunkteArt.Segen, false);
			} else if (card.art == Art.Nachtgestalt) {
				new SpielerPunkteAendern().execute(this, p, 1, PunkteArt.Fluch, false);
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
				card.setBlock(this);
			}
		}
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

	public Player getOpOfCard(CardState card) {
		return getOwnerOfCard(card).isPlayer? oponent : player;
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

	public boolean isCardTargetEmpty(CardState card) {
		return card.selectState == State.selectOptionCardListState && (card.getCardListToSelect(this).size() == 0);
	}

	public boolean isEffektManualActivatable(Player p, CardState card, int manualTrigger) {
		return card.isEffekt &&!card.defaultCard.isSpell() && card.triggerState == manualTrigger && isEffektPossible(p, manualTrigger, card) && p.isOnTurn;
	}
	
	public boolean isEffektPossible(Player p, int trigger, CardState card) {
		return card.isEffekt && card.isEffektPossible(this) && card.triggerState == trigger && !p.isEffektBlockiert(card) && !card.isHide && !isCardTargetEmpty(card);
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
			new KarteVonHandAufBoard().execute(this, p, getCardOfSpecificId(specificId).id, false, true, true);
		} else if (p.hasSpecificCardInStapel(specificId)) {
			new KarteVonStapelAufHand().execute(this, p, getCardOfSpecificId(specificId).id, true);
		} else {
			throw new Error("specificKreaturAusStapelOderHandAufrufen Spezifische Karte mit der ID nicht gefunden: " + specificId);
		}
	} 

	public List<CardState> optionCardsToSelectCardsOnBoard(Player player, boolean isHide) {
		return player.boardCards.stream()
			.filter(card -> card.isHide == isHide)
			.collect(Collectors.toList());
	}

	public List<CardState> optionCardsToSelectOpenCardsArtOnBoard(Player player, Art art) {
		return player.boardCards.stream()
			.filter(card -> !card.isHide && card.art == art)
			.collect(Collectors.toList());
	}

	public List<CardState> optionCardsToSelectOpenCardsHasStatusNotOnBoard(Player player, Status status) {
		return player.boardCards.stream()
			.filter(card -> !card.isHide && !card.statusSet.contains(status))
			.collect(Collectors.toList());
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
		
		new SortKarten().execute(this, p, arg, posName, send);
	}
	
	public void playSE(int soundId) {
		if (isSoundOn) {
			gp.playSE(soundId);
		}
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
