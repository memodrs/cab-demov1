package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;



public class Pegasus extends CardStateEffekt {

	public Pegasus(Card card) {
		super(card, State.boardState, Trigger.triggerhatAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), 1, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}
