package com.cab.cardGame.effektCards.segen;

import java.util.List;
import java.util.stream.Collectors;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Regenbogen extends CardStateSpell {

	public Regenbogen(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        new KarteVonHandAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, false, true, true);
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
		return  cardGame.getOwnerOfCard(this).hasBoardPlace() && cardGame.getOwnerOfCard(this).handCards.stream().anyMatch(card -> Art.Fabelwesen.equals(card.art));
    }

	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).handCards.stream().filter(card -> card.art == Art.Fabelwesen).collect(Collectors.toList());
	}
}