package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufFriedhof;
import com.cab.cardGame.actions.KarteVonStapelAufHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

public class Prinzessin extends CardStateEffekt {
	public Prinzessin(Card card) {
		super(card, State.handCardState, Trigger.triggerManualFromHand, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonHandAufFriedhof().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, true);
		new KarteVonStapelAufHand().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
		cardGame.kartenMischen(cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).stapel, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasSpecificCardInStapel(Ids.KOENIG) || cardGame.getOwnerOfCard(this).hasSpecificCardInStapel(Ids.HERRSCHERIN);
	}


	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		if (cardGame.getOwnerOfCard(this).hasSpecificCardInStapel(Ids.KOENIG)) {
			cardGame.optionsCardsToSelect.add(cardGame.getCardOfSpecificId(Ids.KOENIG));
		}

		if (cardGame.getOwnerOfCard(this).hasSpecificCardInStapel(Ids.HERRSCHERIN)) {
			cardGame.optionsCardsToSelect.add(cardGame.getCardOfSpecificId(Ids.HERRSCHERIN));
		}
    }
}
