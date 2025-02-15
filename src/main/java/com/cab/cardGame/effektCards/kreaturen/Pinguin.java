package com.cab.cardGame.effektCards.kreaturen;

import java.util.stream.Collectors;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;




public class Pinguin extends CardStateEffekt {

	public Pinguin(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonHandAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, true, true, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace() && cardGame.getOwnerOfCard(this).hasKreaturInHand();
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.getOwnerOfCard(this).handCards.stream()
				.filter(card -> !card.defaultCard.isSpell())
				.collect(Collectors.toList())
		);
	}
}
