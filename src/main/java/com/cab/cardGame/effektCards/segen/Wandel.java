package com.cab.cardGame.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Wandel extends CardStateSpell {

	public Wandel(Card card, CardGame cardGame, int nextStateForPlayer, int selectState) {
		super(card, cardGame, nextStateForPlayer, selectState);
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