package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Pirat extends CardStateEffekt {

	public Pirat(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOpOfCard(this), -2, PunkteArt.Segen, true);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), 2, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOpOfCard(this).segenCounter > 1;
	}
}
