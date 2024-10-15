package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Finsternis extends EffektCardState implements EffektCard {

	public Finsternis(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer idx) {
        cardGame.setBlockEffekte(true, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return true;
    }

    @Override
    public void removeBlocks(Player p) {
        cardGame.setBlockEffekte(false, false);
    }
}