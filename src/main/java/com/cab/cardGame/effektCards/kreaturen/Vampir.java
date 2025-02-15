package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Vampir extends CardStateEffekt {	
	public Vampir(Card card) {
		super(card, State.boardState, Trigger.triggerhatAngegriffen, State.ignoreState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteHeilen().execute(cardGame, this.id, atk, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).boardCards.contains(this);
	}
}