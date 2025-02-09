package com.cab.cardGame.model;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
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

	public boolean blockEffektMenschen = false;
	public boolean blockEffektTiere = false;
	public boolean blockEffektFabelwesen = false;
	public boolean blockEffektNachtgestalten = false;
	public boolean blockAngriffMenschen = false;
	public boolean blockAngriffTiere = false;
	public boolean blockAngriffFabelwesen = false;
	public boolean blockAngriffNachtgestalten = false;

	public boolean blockAufrufOneTurnMensch =  false;
	public boolean blockAufrufOneTurnTier =  false;
	public boolean blockAufrufOneTurnFabelwesen =  false;
	public boolean blockAufrufOneTurnNachtgestalt =  false;
	public boolean blockAufrufOneTurnSegen =  false;
	public boolean blockAufrufOneTurnFluch =  false;

	public Player(List<Integer> stapel, EffektManager effektManager, boolean isPlayer, boolean isPlayerStart) {
		this.isPlayer = isPlayer;

		for (Integer id : stapel) {
			this.stapel.add(effektManager.getCardForId(id));
		}

		int startWert = (isPlayerStart == isPlayer) ? 0 : 200;

		for (int i = 0; i < stapel.size(); i++) {
			this.stapel.get(i).id = i + startWert;
		}

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
		return (
 			card.art == Art.Mensch && blockEffektMenschen || 
			card.art == Art.Tier && blockEffektTiere || 
			card.art == Art.Fabelwesen && blockEffektFabelwesen || 
			card.art == Art.Nachtgestalt && blockEffektNachtgestalten);
	}

	public boolean isPlayCreatureAllowed(CardState card) {
		Art art = card.art;
		return numberOfCreatureCanPlayInTurn > 0 &&
				hasBoardPlace() &&
			   (art == Art.Mensch && !blockAufrufOneTurnMensch ||
				art == Art.Tier && !blockAufrufOneTurnTier ||
				art == Art.Fabelwesen && !blockAufrufOneTurnFabelwesen ||
				art == Art.Nachtgestalt && !blockAufrufOneTurnNachtgestalt);
	}

	public boolean isPlaySpellAllowed(Player oponent, CardState card) {
		Art art = card.art;
		boolean isArtBlocked = art == Art.Segen && blockAufrufOneTurnSegen || art == Art.Fluch && blockAufrufOneTurnFluch;
		return (!isArtBlocked && 
				(card.art == Art.Fluch && card.defaultCard.getKosten() <= fluchCounter) || 
				(card.art == Art.Segen && card.defaultCard.getKosten() <= segenCounter)) 
				&& card.isEffektPossible(this, oponent) && isOnTurn;
	}

	public boolean isAngriffBlockiert(CardState card) {
		return (
			card.art == Art.Mensch && blockAngriffMenschen ||
			card.art == Art.Tier && blockAngriffTiere ||
			card.art == Art.Fabelwesen && blockAngriffFabelwesen ||
			card.art == Art.Nachtgestalt && blockAngriffNachtgestalten ||
			card.blockAttackOnTurn
		);	
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
		blockEffektMenschen = false;
		blockEffektTiere = false;
		blockEffektFabelwesen = false;
		blockEffektNachtgestalten = false;

		blockAngriffMenschen = false;
		blockAngriffTiere = false;
		blockAngriffFabelwesen = false;
		blockAngriffNachtgestalten = false;
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
