package com.cab.cardGame.model;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;

public class EffektCardState extends CardState {
	protected CardGame cardGame;

	public EffektCardState(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card);
		this.cardGame = cardGame;
		this.nextStateForPlayer = nextStateForPlayer;
		this.triggerState = triggerState;
		this.selectState = selectState;	
		isEffekt = true;
	}
}
