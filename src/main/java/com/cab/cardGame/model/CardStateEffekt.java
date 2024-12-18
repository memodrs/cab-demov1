package com.cab.cardGame.model;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;

public class CardStateEffekt extends CardState {
	protected CardGame cardGame;

	public CardStateEffekt(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card);
		this.cardGame = cardGame;
		this.nextStateForPlayer = nextStateForPlayer;
		this.triggerState = triggerState;
		this.selectState = selectState;	
		this.isEffekt = true;
	}
}
