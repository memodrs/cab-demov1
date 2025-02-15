package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Wolf extends CardStateEffekt {
	public Wolf(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.specificKreaturAusStapelOderHandAufrufen(cardGame.getOwnerOfCard(this), getId());
	}

	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace() && (cardGame.getOwnerOfCard(this).hasSpecificCardInHand(getId()) || cardGame.getOwnerOfCard(this).hasSpecificCardInStapel(getId()));
	};

	private int getId() {
		if (this.defaultCard.getId() == Ids.WOLF_1) {
			return Ids.WOLF_2;
		} else if (this.defaultCard.getId() == Ids.WOLF_2) {
			return Ids.WOLF_1;
		} else {
			throw new Error("Wolf hat eine unerwartete ID: " + this.defaultCard.getId());
		}
	}
}
