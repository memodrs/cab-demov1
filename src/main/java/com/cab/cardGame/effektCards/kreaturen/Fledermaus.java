package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Fledermaus extends CardStateEffekt {
	
	public Fledermaus(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		CardState searchCard = cardGame.getCardOfSpecificId(Ids.VAMPIR);
		new KarteVonHandAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), searchCard.id, false, true, true);
	}

	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace() && cardGame.getOwnerOfCard(this).hasSpecificCardInHand(Ids.VAMPIR);
	};
}
