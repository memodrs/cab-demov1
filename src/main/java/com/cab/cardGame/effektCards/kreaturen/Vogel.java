package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Vogel extends CardStateEffekt {

	public Vogel(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromHand, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		cardGame.karteVonHandAufBoard(cardGame.player, this.id, false, true, true);
	}

	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace();
	};
}
