package com.cab.cardGame.effektCards.segen;

import java.util.List;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Fluegel extends CardStateSpell {

	public Fluegel(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new SetKarteStatus().execute(cardGame, id, true, Status.Fluegel, true);
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return true;	
    }

	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.optionCardsToSelectOpenCardsHasStatusNotOnBoard(cardGame.getOwnerOfCard(this), Status.Fluegel);
    }
}