package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Biene extends CardStateEffekt {

	public Biene(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {
		cardGame.karteVonHandAufBoard(cardGame.player, this.id, false, true, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace();
	};
}
