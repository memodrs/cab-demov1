package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffErhoehen;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.actions.KarteVonHandAufFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;



public class Schwein extends CardStateEffekt {

	public Schwein(Card card) {
		super(card, State.handCardState, Trigger.triggerManualFromHand, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		new KarteVonHandAufFriedhof().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, true);
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
			if (card.art == Art.Mensch && !card.isHide) {
				new KarteAngriffErhoehen().execute(cardGame, card.id, 2, true);
				new KarteHeilen().execute(cardGame, card.id, 2, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasArtOnBoard(Art.Mensch);
	}
}