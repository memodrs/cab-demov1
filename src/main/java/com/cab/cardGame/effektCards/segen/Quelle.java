package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Quelle extends CardStateSpell {

	public Quelle(Card card) {
		super(card, State.boardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
			if (!card.isHide) {
				new KarteHeilen().execute(cardGame, card.id, 2, true);
				new SetKarteStatus().execute(cardGame, card.id, false, Status.Feuer, true);
				new SetKarteStatus().execute(cardGame, card.id, false, Status.Blitz, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasOpenCardsOnBoard();
	}
}