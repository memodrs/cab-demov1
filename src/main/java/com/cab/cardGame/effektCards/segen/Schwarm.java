package com.cab.cardGame.effektCards.segen;

import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonStapelAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;


public class Schwarm extends CardStateSpell {

	public Schwarm(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        new KarteVonStapelAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
        cardGame.kartenMischen(cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).stapel, true);
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
		return  cardGame.getOwnerOfCard(this).hasBoardPlace() &&
				cardGame.getOwnerOfCard(this).boardCards.stream().anyMatch(card -> Art.Tier.equals(card.art)) && 
                cardGame.getOwnerOfCard(this).stapel.stream().anyMatch(card -> Art.Tier.equals(card.art));
    }

	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionsCardsToSelect.addAll(
			cardGame.getOwnerOfCard(this).stapel.stream()
			.filter(card -> card.art == Art.Tier)
			.collect(Collectors.toList())
		);
    }
}