package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;


public class Beschwoerung extends CardStateSpell {

	public Beschwoerung(Card card) {
		super(card, State.boardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.getOwnerOfCard(this).numberOfCreatureCanPlayInTurn++;
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}