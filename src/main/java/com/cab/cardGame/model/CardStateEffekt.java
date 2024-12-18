package com.cab.cardGame.model;

import com.cab.card.Card;

public class CardStateEffekt extends CardState {

	public CardStateEffekt(Card card, int nextStateForPlayer, int triggerState, int selectState) {
		super(card);
		this.nextStateForPlayer = nextStateForPlayer;
		this.triggerState = triggerState;
		this.selectState = selectState;	
		this.isEffekt = true;
	}
}
