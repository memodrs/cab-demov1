package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Qualle extends EffektCardState {

	public Qualle(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.setKarteStatus(id, true, Status.Blitz, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}