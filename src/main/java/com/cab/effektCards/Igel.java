package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Igel extends EffektCardState {

	public Igel(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		cardGame.karteSchaden(cardGame.getOpOfP(p), id, 2, true);
    }
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
