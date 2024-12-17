package com.cab.cardGame.model;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;

public class EffektCardState extends CardState {
	public EffektCardState(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame);
		this.nextStateForPlayer = nextStateForPlayer;
		this.triggerState = triggerState;
		this.selectState = selectState;	
		isEffekt = true;
	}
}
