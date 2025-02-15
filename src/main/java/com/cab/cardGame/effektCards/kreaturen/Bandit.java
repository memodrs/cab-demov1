package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Bandit extends CardStateEffekt {

	public Bandit(Card card) {
		super(card, State.boardState, Trigger.triggerWurdeDurchAngriffZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), cardGame.getCardOfId(id).atk, PunkteArt.Leben, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}	



