package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Daemonenjaeger extends EffektCardState implements EffektCard {

	public Daemonenjaeger(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void setBlock(Player p) {
        p.blockEffektNachtgestalten = true;
        cardGame.getOpOfP(p).blockEffektNachtgestalten = true;
    }
}