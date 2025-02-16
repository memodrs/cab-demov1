package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;
import java.util.stream.Collectors;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufFriedhof;
import com.cab.cardGame.actions.KarteVonStapelAufHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
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
		return true;
	}


	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).stapel.stream().filter(card -> card.id == Ids.KOENIG || card.id == Ids.HERRSCHERIN).collect(Collectors.toList());
    }
}
