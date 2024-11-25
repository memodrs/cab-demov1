package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Igel extends EffektCardState {

	public Igel(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteSchaden(cardGame.oponent, id, 2, true, false);
    }
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
