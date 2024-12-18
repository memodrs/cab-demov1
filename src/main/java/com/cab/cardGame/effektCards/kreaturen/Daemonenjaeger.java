package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Daemonenjaeger extends CardStateEffekt {

	public Daemonenjaeger(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    public void setBlock(Player p) {
        p.blockEffektNachtgestalten = true;
        cardGame.getOpOfP(p).blockEffektNachtgestalten = true;
    }
}