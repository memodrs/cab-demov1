package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Mumie extends CardStateEffekt {

	public Mumie(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    @Override
    public void effekt(Integer idx) {   
        cardGame.setBlockAufrufArtNextTurn(cardGame.oponent, true, Art.Mensch, true);
    }

    @Override
    public boolean isEffektPossible(Player p) {
        return true;
    }
}
