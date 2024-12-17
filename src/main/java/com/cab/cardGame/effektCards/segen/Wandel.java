package com.cab.cardGame.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Wandel extends EffektCardState {

	public Wandel(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.setArtOfCard(id, Art.Mensch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.hasArtOnBoard(Art.Fabelwesen) || p.hasArtOnBoard(Art.Nachtgestalt) || p.hasArtOnBoard(Art.Tier) || p.hasArtOnBoard(Art.Unbekannt);
	}

	public boolean isCardValidForSelection(CardState card) {
		return card.art != Art.Mensch && !card.isHide;
	}
}