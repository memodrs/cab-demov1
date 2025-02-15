package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteAngriffErhoehen;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;


public class SchildUndSchwert extends CardStateSpell {

	public SchildUndSchwert(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteAngriffErhoehen().execute(cardGame, id, 3, true);
		new SetKarteStatus().execute(cardGame, id, true, Status.Schild, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOwnerOfCard(this), false);
    }
}