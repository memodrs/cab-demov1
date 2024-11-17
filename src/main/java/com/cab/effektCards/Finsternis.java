package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Finsternis extends EffektCardState {

	public Finsternis(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void setBlock(Player p) {
        p.blockEffektMenschen = true;
        p.blockEffektTiere = true;
        p.blockEffektFabelwesen = true;
        p.blockEffektNachtgestalten = true;

        Player op = cardGame.getOpOfP(p);
        op.blockEffektMenschen = true;
        op.blockEffektTiere = true;
        op.blockEffektFabelwesen = true;
        op.blockEffektNachtgestalten = true;
    }
}