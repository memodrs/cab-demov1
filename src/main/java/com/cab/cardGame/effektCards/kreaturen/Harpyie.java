package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Harpyie extends CardStateEffekt {


	
	public Harpyie(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromHand, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		new KarteVonHandAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, false, true, true);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), -2, PunkteArt.Fluch, true);
	}

	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace() && cardGame.getOwnerOfCard(this).fluchCounter > 1;
	};
}
