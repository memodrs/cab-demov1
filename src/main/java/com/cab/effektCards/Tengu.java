package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Tengu extends EffektCardState {

	public Tengu(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		if (this.statusSet.contains(Status.Fluegel)) {
			cardGame.setKarteStatus(p, this.id, false, Status.Schild, true);
		} else {
			cardGame.setKarteStatus(p, this.id, true, Status.Schild, true);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
