package com.cab.cardGame.effektCards.segen;

import java.util.List;
import java.util.stream.Collectors;

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
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).boardCards.stream().filter(card -> card.hasAttackOnTurn && !card.isHide).collect(Collectors.toList());
    }
}