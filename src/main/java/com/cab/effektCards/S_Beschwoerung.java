package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class S_Beschwoerung extends EffektCardState implements EffektCard {

	public S_Beschwoerung(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		if (cardGame.creatureWasPlayedInTurn) {
			cardGame.creatureWasPlayedInTurn = false;
		} else {
			cardGame.doppelBeschwoerung = true;
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return p.handCards.size() > 1;
	}
}