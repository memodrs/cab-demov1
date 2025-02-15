package com.cab.cardGame.effektCards.segen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetArtOfCard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


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
	public void setUpOptionsToSelect(CardGame cardGame) {
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
			if (card.art != Art.Mensch && !card.isHide) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}