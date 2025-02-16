package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.ChangeSavedIdOponentAttack;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Buergermeister extends CardStateEffekt {

	public Buergermeister(Card card) {                                     
		super(card, State.boardState, Trigger.triggerAngriffSetupVerteidiger, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new ChangeSavedIdOponentAttack().execute(cardGame, id, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).boardCards.size() > 1;
	}

	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOwnerOfCard(this), false);
    }
}