package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;


public class Bauer extends EffektCardState {

	public Bauer(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.kartenZiehen(cardGame.player, 1, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
