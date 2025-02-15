package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Sturmangriff extends CardStateSpell {

	public Sturmangriff(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        cardGame.getCardOfId(id).hasAttackOnTurn = false;
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return true;	
    }

    @Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
			if (card.hasAttackOnTurn && !card.isHide) {
				cardGame.optionsCardsToSelect.add(card);
			}
		}
    }
}