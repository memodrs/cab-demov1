package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffErhoehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Aswang extends CardStateEffekt {	
	public Aswang(Card card) {
		super(card, State.boardState, Trigger.triggerhatAngegriffen, State.ignoreState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteAngriffErhoehen().execute(cardGame, this.id, 2, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}