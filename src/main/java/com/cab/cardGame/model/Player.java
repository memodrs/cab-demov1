package com.cab.cardGame.model;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.EffektManager;

public class Player {
	public boolean isPlayer;
	public boolean isKI;
	public int lifeCounter = 20;
	public int segenCounter = 0;
	public int fluchCounter = 0;
	public List<CardState> stapel = new ArrayList<CardState>();
	public List<CardState> handCards = new ArrayList<CardState>();
	public List<CardState> boardCards = new ArrayList<CardState>();
	public List<CardState> graveCards = new ArrayList<CardState>();
	public List<CardState> spellGraveCards = new ArrayList<CardState>();

	public boolean isOnTurn;
	public boolean isFirstTurn;
	public boolean inactiveMode; 
	public int numberOfCreatureCanPlayInTurn;

	public List<Art> blockEffekteArt;
	public List<Art> blockAufrufArtFromHand;
	public List<Art> blockAngriffArt;

	public Player() {}

	public Player(List<Card> stapel, boolean isPlayer, boolean isPlayerStart) {
		this.isPlayer = isPlayer;

		for (Card card : stapel) {
			this.stapel.add(EffektManager.getCardStateForCard(card));
		}

		int startWert = (isPlayerStart == isPlayer) ? 0 : 200;

		for (int i = 0; i < stapel.size(); i++) {
			this.stapel.get(i).id = i + startWert;
		}

		blockEffekteArt = new ArrayList<>();
		blockAufrufArtFromHand = new ArrayList<>();
		blockAngriffArt = new ArrayList<>();

		numberOfCreatureCanPlayInTurn = 1;
		isOnTurn = isPlayerStart;
		isFirstTurn = isPlayerStart;
		inactiveMode = !isPlayerStart;
		isKI = false;
	}

    public boolean hasBoardPlace() {
		return boardCards.size() < 4;
	}

	public boolean isBoardEmpty() {
		return boardCards.size() == 0;
	}

	public boolean isGraveEmpty() {
		return graveCards.size() == 0;
	}

	public boolean hasArtOnBoard(Art art) {
		return boardCards.stream().anyMatch(card -> !card.isHide && card.art == art);
	}

	public boolean hasOpenCardsOnBoard() {
		return boardCards.stream().anyMatch(card -> !card.isHide);
	}

	public boolean hasHiddenCardsOnBoard() {
		return boardCards.stream().anyMatch(card -> card.isHide);
	}

	public boolean hasGraveCards() {
		return graveCards.size() > 0;
	}

	public boolean hasArtOnGrave(Art art) {
		return graveCards.stream().anyMatch(card -> !card.isHide && card.art == art);
	}

	public boolean hasKreaturInHand() {
		return handCards.stream().anyMatch(card -> !card.defaultCard.isSpell());
	}

	public boolean hasSpecificCardInHand(int specificId) {
		return handCards.stream().anyMatch(card -> card.defaultCard.getId() == specificId);
	}

	public boolean hasSpecificCardInStapel(int specificId) {
		return stapel.stream().anyMatch(card -> card.defaultCard.getId() == specificId);
	}

	public boolean hasSpecificCardInGrave(int specificId) {
		return graveCards.stream().anyMatch(card -> card.defaultCard.getId() == specificId);
	}

	public boolean isEffektBlockiert(CardState card) {
		return blockEffekteArt.contains(card.art);
	}

	public boolean isPlayCreatureAllowed(CardState card) {
		return numberOfCreatureCanPlayInTurn > 0 && hasBoardPlace() && !blockAufrufArtFromHand.contains(card.art) && !card.defaultCard.isSpell();
	}

	public boolean isPlaySpellAllowed(Player oponent, CardState card) {
		return !blockEffekteArt.contains(card.art) && hasEnoughPoints(card) && isOnTurn;
	}

	private boolean hasEnoughPoints(CardState card) {
		return (card.art == Art.Fluch && card.defaultCard.getKosten() <= fluchCounter) || 
				(card.art == Art.Segen && card.defaultCard.getKosten() <= segenCounter);
	}
	public boolean isAngriffBlockiert(CardState card) {
		return blockAngriffArt.contains(card.art) || card.blockAttackOnTurn;	

	}

	public boolean isAttackAlowed(CardState card) {
		boolean isArtRulesAllowedAttack = false;
		if (card.art == Art.Fabelwesen) {
			isArtRulesAllowedAttack = hasArtOnBoard(Art.Mensch);
		} else if (card.art == Art.Nachtgestalt) {
			isArtRulesAllowedAttack = !hasArtOnBoard(Art.Mensch);
		} else {
			isArtRulesAllowedAttack = true;
		}
		
		return !card.isHide && !card.hasAttackOnTurn && isArtRulesAllowedAttack && !isFirstTurn && !isAngriffBlockiert(card) && !card.statusSet.contains(Status.Blitz) && isOnTurn;
	}

	public void resetBlocks() {
		blockEffekteArt.clear();
		blockAngriffArt.clear();
	}

	public void resetStatsOnEndTurn() {
		if (isFirstTurn) {
			isFirstTurn = false;
		}
		inactiveMode = true;
		isOnTurn = false;
	}

	public void resetStatsOnStartTurn() {
		numberOfCreatureCanPlayInTurn = 1;
		inactiveMode = false;
		isOnTurn = true;
	}
}
