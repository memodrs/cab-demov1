package com.cab.cardGame.model;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;
import com.cab.cardGame.EffektManager;

public class Player {
	public boolean isPlayer;
	public int lifeCounter = 20;
	public int segenCounter = 0;
	public int fluchCounter = 0;
	public List<CardState> stapel = new ArrayList<CardState>();
	public List<CardState> handCards = new ArrayList<CardState>();
	public List<CardState> boardCards = new ArrayList<CardState>();
	public List<CardState> graveCards = new ArrayList<CardState>();
	public List<CardState> spellGraveCards = new ArrayList<CardState>();

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

	public Player(List<Integer> stapel, EffektManager effektManager, boolean isPlayer) {
		this.isPlayer = isPlayer;
		for (Integer id : stapel) {
			this.stapel.add(effektManager.getCardForId(id));
		}
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
}
