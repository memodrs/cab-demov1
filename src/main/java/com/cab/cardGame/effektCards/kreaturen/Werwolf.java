package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Werwolf extends CardStateEffekt {

	public Werwolf(Card card) {
		super(card, State.boardState, Trigger.triggerhatAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
        cardGame.karteSchaden(cardGame.player, id, 2, true, false);
	}

	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.boardCards.contains(this);
	};
}
