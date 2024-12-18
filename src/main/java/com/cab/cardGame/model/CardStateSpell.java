package com.cab.cardGame.model;

import com.cab.card.Card;
import com.cab.cardGame.config.Trigger;


public class CardStateSpell extends CardState {

	public CardStateSpell(Card card, int nextStateForPlayer, int selectState) {
		super(card);
		this.nextStateForPlayer = nextStateForPlayer;
		this.triggerState = Trigger.triggerManualFromHand;
		this.selectState = selectState;	
		this.isEffekt = true;
	}
}
