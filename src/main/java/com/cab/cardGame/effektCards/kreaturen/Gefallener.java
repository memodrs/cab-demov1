package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Gefallener extends CardStateEffekt {

	public Gefallener(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {		
        int segenPunkte = cardGame.getOwnerOfCard(this).segenCounter;
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), -segenPunkte, PunkteArt.Segen, true);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), segenPunkte, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).segenCounter > 0;
	}
	
}