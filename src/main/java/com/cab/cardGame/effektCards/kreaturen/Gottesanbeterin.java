package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Gottesanbeterin extends CardStateEffekt {

	public Gottesanbeterin(Card card) {
		super(card, State.boardState, Trigger.triggerhatAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {		
		hasAttackOnTurn = false;
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return !isEffectActivateInTurn;
	}
}