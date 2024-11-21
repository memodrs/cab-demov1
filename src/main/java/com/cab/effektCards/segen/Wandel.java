package com.cab.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Wandel extends EffektCardState {

	public Wandel(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.setArtOfCard(cardGame.player, id, Art.Mensch, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.isArtOnBoard(Art.Fabelwesen) || p.isArtOnBoard(Art.Nachtgestalt) || p.isArtOnBoard(Art.Tier) || p.isArtOnBoard(Art.Unbekannt);
	}

	public boolean isCardValidForSelection(CardState card) {
		return card.art != Art.Mensch && !card.isHide;
	}
}