package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonStapelAufHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;


public class SegenDerFabel extends CardStateSpell {

	public SegenDerFabel(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        new KarteVonStapelAufHand().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
		cardGame.kartenMischen(cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).stapel, true);
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
		return  cardGame.getOwnerOfCard(this).stapel.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));
    }

	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.getOwnerOfCard(this).stapel.stream()
			.filter(card -> card.art == Art.Fabelwesen)
			.collect(Collectors.toList())
		);
    }
}