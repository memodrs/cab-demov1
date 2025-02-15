package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffErhoehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Hydra extends CardStateEffekt {

	public Hydra(Card card) {
		super(card, State.boardState, Trigger.triggerBeforeKarteWirdAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteAngriffErhoehen().execute(cardGame, this.id, atk, true);
    }
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).boardCards.contains(this);
	}
}
