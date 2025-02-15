package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Kirin extends CardStateEffekt {

	public Kirin(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		new SetKarteStatus().execute(cardGame, id, true, Status.Blitz, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
	
	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectOpenCardsHasStatusNotOnBoard(cardGame.getOpOfCard(this), Status.Blitz);
	}
}
