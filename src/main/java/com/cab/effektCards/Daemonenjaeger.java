package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;

public class Daemonenjaeger extends EffektCardState implements EffektCard {

	public Daemonenjaeger(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void setBlock() {
        isBlockActiv = true;
        cardGame.setBlockEffekteNachtgestaltenOponent(true, true);
        cardGame.setBlockEffekteNachtgestaltenPlayer(true, true);
    }

    @Override
    public void removeBlock() {
        isBlockActiv = false;
        cardGame.setBlockEffekteNachtgestaltenOponent(false, true);
        cardGame.setBlockEffekteNachtgestaltenPlayer(false, true);
    }
}