package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KartenZiehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;



public class Bauer extends CardStateEffekt {

	public Bauer(Card card) {
		super(card, State.handCardState, Trigger.triggerOnStartRunde, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KartenZiehen().execute(cardGame, cardGame.getOwnerOfCard(this), 1, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}
