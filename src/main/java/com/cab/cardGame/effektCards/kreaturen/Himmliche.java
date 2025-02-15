package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Himmliche extends CardStateEffekt {

	public Himmliche(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {		
        int fluchPunkte = cardGame.getOwnerOfCard(this).fluchCounter;
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), -fluchPunkte, PunkteArt.Fluch, true);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), fluchPunkte, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).fluchCounter > 0;
	}
}	