package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Brand extends CardStateSpell {
	public Brand(Card card) {
		super(card, State.handCardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
			if (!card.isHide) {
				new SetKarteStatus().execute(cardGame, card.id, true, Status.Feuer, true);
			}
		}	

		for (CardState card : cardGame.getOpOfCard(this).boardCards) {
			if (!card.isHide) {
				new SetKarteStatus().execute(cardGame, card.id, true, Status.Feuer, true);
			}
		}	
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasOpenCardsOnBoard() || cardGame.getOpOfCard(this).hasOpenCardsOnBoard();
	}
}
