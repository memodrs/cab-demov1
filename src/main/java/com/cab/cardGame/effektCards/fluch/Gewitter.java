package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Gewitter extends CardStateSpell {
	public Gewitter(Card card) {
		super(card, State.handCardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new SetKarteStatus().execute(cardGame, id, true, Status.Blitz, true);
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return cardGame.getOpOfCard(this).boardCards.stream().anyMatch(card -> !card.statusSet.contains(Status.Blitz) && !card.isHide);	
    }

    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Blitz);
    }

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectOpenCardsHasStatusNotOnBoard(cardGame.getOpOfCard(this), Status.Blitz);;
    }
}