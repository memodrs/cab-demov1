package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Tengu extends CardStateEffekt {

	public Tengu(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		if (this.statusSet.contains(Status.Fluegel)) {
			cardGame.setKarteStatus(this.id, false, Status.Fluegel, true);
		} else {
			cardGame.setKarteStatus(this.id, true, Status.Fluegel, true);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
