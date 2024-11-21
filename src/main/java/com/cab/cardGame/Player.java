package com.cab.cardGame;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Art;

public class Player {
	public boolean isPlayer;

	public String name;
	public int lifeCounter = 20;
	public int segenCounter = 20;
	public int fluchCounter = 0;
	public List<CardState> stapel = new ArrayList<CardState>();
	public List<CardState> handCards = new ArrayList<CardState>();
	public List<CardState> boardCards = new ArrayList<CardState>();
	public List<CardState> graveCards = new ArrayList<CardState>();
	public List<CardState> spellGraveCards = new ArrayList<CardState>();

	//BoardStates
	public boolean blockEffektMenschen = false;
	public boolean blockEffektTiere = false;
	public boolean blockEffektFabelwesen = false;
	public boolean blockEffektNachtgestalten = false;
	public boolean blockAngriffMenschen = false;
	public boolean blockAngriffTiere = false;
	public boolean blockAngriffFabelwesen = false;
	public boolean blockAngriffNachtgestalten = false;

	//Blcoks
	public boolean blockAufrufOneTurnMensch =  false;
	public boolean blockAufrufOneTurnTier =  false;
	public boolean blockAufrufOneTurnFabelwesen =  false;
	public boolean blockAufrufOneTurnNachtgestalt =  false;
	public boolean blockAufrufOneTurnSegen =  false;
	public boolean blockAufrufOneTurnFluch =  false;

	public Player(List<Integer> stapel, String name, CardGame cg, boolean isPlayer) {
		this.isPlayer = isPlayer;
		List<CardState> cards = new ArrayList<CardState>();
		for (Integer id : stapel) {
			cards.add(cg.effekteMangaer.getCardForId(id));
		}
		
		this.stapel = cards;
		this.name = name;
	}

	public boolean hasBoardPlace() {
		return boardCards.size() < 4;
	}

	public boolean isArtOnBoard(Art art) {
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
