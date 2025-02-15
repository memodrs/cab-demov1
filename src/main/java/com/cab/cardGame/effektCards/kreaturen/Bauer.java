package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KartenZiehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;


public class Bauer extends CardStateEffekt {

	public Bauer(Card card) {
		super(card, State.handCardState, Trigger.triggerOnStartRunde, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KartenZiehen().execute(cardGame, cardGame.player, 1, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
