package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Schlange extends EffektCardState implements EffektCard {

	public Schlange(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		cardGame.setKarteStatus(cardGame.getOpOfP(p), id, true, Status.Gift, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}