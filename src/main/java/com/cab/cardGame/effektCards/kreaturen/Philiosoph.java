package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonStapelAufHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;




public class Philiosoph extends CardStateEffekt {

	public Philiosoph(Card card) {
		super(card, State.handCardState, Trigger.triggerOnStartRunde, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonStapelAufHand().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
		cardGame.kartenMischen(cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).stapel.stream()
		.anyMatch(card -> Art.Segen.equals(card.art));	
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		for (CardState card : cardGame.getOwnerOfCard(this).stapel) {
			if (card.art == Art.Segen) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}
