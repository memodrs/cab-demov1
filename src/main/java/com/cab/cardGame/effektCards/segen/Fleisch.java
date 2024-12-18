package com.cab.cardGame.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Fleisch extends CardStateSpell {

	public Fleisch(Card card) {
		super(card, State.boardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		for (CardState card : cardGame.player.boardCards) {
			if (card.art == Art.Tier) {
				cardGame.karteAngriffErhoehen(card.id, 2, true);
				cardGame.karteHeilen(card.id, 2, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasArtOnBoard(Art.Tier);
	}
}