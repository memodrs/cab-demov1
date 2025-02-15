package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Hetze extends CardStateSpell {

	public Hetze(Card card) {
		super(card, State.boardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
        for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
            card.hasAttackOnTurn = false;
        }
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return cardGame.getOwnerOfCard(this).boardCards.stream().anyMatch(card -> card.hasAttackOnTurn);	
    }
}