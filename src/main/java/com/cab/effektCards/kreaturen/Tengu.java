package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Tengu extends EffektCardState {

	public Tengu(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		if (this.statusSet.contains(Status.Fluegel)) {
			cardGame.setKarteStatus(cardGame.player, this.id, false, Status.Fluegel, true);
		} else {
			cardGame.setKarteStatus(cardGame.player, this.id, true, Status.Fluegel, true);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
