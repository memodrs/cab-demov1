package com.cab.cardGame.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffErhoehen;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Fleisch extends CardStateSpell {

	public Fleisch(Card card) {
		super(card, State.boardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
			if (card.art == Art.Tier) {
				new KarteAngriffErhoehen().execute(cardGame, card.id, 2, true);
				new KarteHeilen().execute(cardGame, card.id, 2, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasArtOnBoard(Art.Tier);
	}
}