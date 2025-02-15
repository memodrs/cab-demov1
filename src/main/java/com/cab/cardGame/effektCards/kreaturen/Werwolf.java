package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteSchaden;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Werwolf extends CardStateEffekt {

	public Werwolf(Card card) {
		super(card, State.boardState, Trigger.triggerhatAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		new KarteSchaden().execute(cardGame, cardGame.getOwnerOfCard(this), id, 2, true, false);
	}

	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).boardCards.contains(this);
	};
}
