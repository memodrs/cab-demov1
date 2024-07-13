package com.cab.cardGame;

import com.cab.card.Card;

public class EffektCardState extends CardState {
	public EffektCardState(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame);
		this.nextStateForPlayer = nextStateForPlayer;
		this.triggerState = triggerState;
		this.selectState = selectState;	
		isEffekt = true;
	}
}
