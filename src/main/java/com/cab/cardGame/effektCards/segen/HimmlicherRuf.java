package com.cab.cardGame.effektCards.segen;

import java.util.List;
import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class HimmlicherRuf extends CardStateSpell {

	public HimmlicherRuf(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
		return  cardGame.getOwnerOfCard(this).hasBoardPlace() && cardGame.getOwnerOfCard(this).graveCards.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));
    }

	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).graveCards.stream()
			.filter(card -> card.art == Art.Fabelwesen)
			.collect(Collectors.toList());
	}
}