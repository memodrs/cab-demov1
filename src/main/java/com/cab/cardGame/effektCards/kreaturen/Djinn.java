package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofInHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Djinn extends CardStateEffekt {

	public Djinn(Card card) {
		super(card, State.handCardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {        
        new KarteVomFriedhofInHand().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
    }

    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return true;
    }

    @Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectGraveCards(cardGame.getOwnerOfCard(this));;
    }
}