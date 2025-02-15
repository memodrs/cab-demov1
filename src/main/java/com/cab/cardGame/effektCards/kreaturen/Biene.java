package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Biene extends CardStateEffekt {

	public Biene(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromHand, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonHandAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, false, true, true);
	}

	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace();
	};
}
