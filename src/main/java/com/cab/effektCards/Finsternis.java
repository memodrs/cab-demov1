package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;

public class Finsternis extends EffektCardState implements EffektCard {

	public Finsternis(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void setBlock() {
        isBlockActiv = true;
        cardGame.setBlockEffekte(true, true);
    }

    @Override
    public void removeBlock() {
        isBlockActiv = false;
        cardGame.setBlockEffekte(false, true);
    }
}