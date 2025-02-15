package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonBoardInHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Loewe extends CardStateEffekt {

	public Loewe(Card card) {
		super(card, State.boardState, Trigger.triggerWurdeAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new KarteVonBoardInHand().execute(cardGame, cardGame.getOpOfCard(this), id, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}