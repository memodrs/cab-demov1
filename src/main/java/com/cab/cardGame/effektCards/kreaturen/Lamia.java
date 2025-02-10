package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Lamia extends CardStateEffekt {

	public Lamia(Card card) {
		super(card, State.boardState, Trigger.triggerWurdeAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.setKarteStatus(id, true, Status.Gift, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}