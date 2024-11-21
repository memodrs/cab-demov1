package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;

public class Daemonenjaeger extends EffektCardState {

	public Daemonenjaeger(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    public void setBlock() {
        cardGame.player.blockEffektNachtgestalten = true;
        cardGame.oponent.blockEffektNachtgestalten = true;
    }
}