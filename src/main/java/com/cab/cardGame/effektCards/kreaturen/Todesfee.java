package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Todesfee extends CardStateEffekt {

	public Todesfee(Card card) {
		super(card, State.boardState, Trigger.triggerOnZerstoertKreaturZerstoert, State.ignoreState);
	}

	public void effekt(CardGame cardGame, Integer id) {
        new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), 1, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).boardCards.contains(this);
	}
}
