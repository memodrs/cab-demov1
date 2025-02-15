package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteSchaden;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Igel extends CardStateEffekt {

	public Igel(Card card) {
		super(card, State.boardState, Trigger.triggerWurdeAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteSchaden().execute(cardGame, cardGame.getOpOfCard(this), id, 2, true, false);
    }
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}
