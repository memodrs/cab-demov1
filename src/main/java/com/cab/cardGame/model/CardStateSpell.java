package com.cab.cardGame.model;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.Trigger;


public class CardStateSpell extends CardState {
	protected CardGame cardGame;

	public CardStateSpell(Card card, CardGame cardGame, int nextStateForPlayer, int selectState) {
		super(card);
		this.cardGame = cardGame;
		this.nextStateForPlayer = nextStateForPlayer;
		this.triggerState = Trigger.triggerManualFromHand;
		this.selectState = selectState;	
		isEffekt = true;
	}
}
