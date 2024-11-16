package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Zwerg extends EffektCardState {
	final int SCHWERT_ID =  122;

	public Zwerg(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		cardGame.specificKarteAusStapelinDieHand(p, SCHWERT_ID);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.stapel.contains(cardGame.getCardOfSpecificId(SCHWERT_ID));
	}
}