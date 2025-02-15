package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;



public class Gefangener extends CardStateEffekt {

	public Gefangener(Card card) {
		super(card, State.boardState, Trigger.triggerhatAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		if (this.wasPlayedInTurn) {
			new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, true, false);
		}
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), 2, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}
