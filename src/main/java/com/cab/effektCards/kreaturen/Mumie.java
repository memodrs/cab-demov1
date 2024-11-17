package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Mumie extends EffektCardState {

	public Mumie(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Player p, Integer idx) {   
        cardGame.setBlockAufrufArtNextTurn(cardGame.getOpOfP(p), true, Art.Mensch, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return true;
    }
}