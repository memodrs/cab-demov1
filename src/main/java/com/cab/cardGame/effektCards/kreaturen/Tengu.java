package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Tengu extends CardStateEffekt {

	public Tengu(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		if (this.statusSet.contains(Status.Fluegel)) {
			cardGame.setKarteStatus(this.id, false, Status.Fluegel, true);
		} else {
			cardGame.setKarteStatus(this.id, true, Status.Fluegel, true);
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
