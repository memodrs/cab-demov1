package com.cab.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Beschwoerung extends EffektCardState {

	public Beschwoerung(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.numberOfCreatureCanPlayInTurn++;
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}