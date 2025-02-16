package com.cab.cardGame.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetArtOfCard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import java.util.List;
import java.util.stream.Collectors;


public class Wandel extends CardStateSpell {

	public Wandel(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new SetArtOfCard().execute(cardGame, id, Art.Mensch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}

	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).boardCards.stream()
			.filter(card -> !card.isHide)
			.collect(Collectors.toList());
	}
}