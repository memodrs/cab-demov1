package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;



public class Ratte extends CardStateEffekt {

	public Ratte(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonHandAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, false, true, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace() && cardGame.getOwnerOfCard(this).handCards.stream()
		.anyMatch(card -> Art.Nachtgestalt.equals(card.art));	
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		for (CardState card : cardGame.getOwnerOfCard(this).handCards) {
			if (card.art == Art.Nachtgestalt) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}