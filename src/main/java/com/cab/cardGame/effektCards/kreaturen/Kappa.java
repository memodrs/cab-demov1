package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.actions.SetKarteStatus;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Kappa extends CardStateEffekt {

	public Kappa(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
            new SetKarteStatus().execute(cardGame, card.id, false, Status.Feuer, true);
            new SetKarteStatus().execute(cardGame, card.id, false, Status.Blitz, true);
			new KarteHeilen().execute(cardGame, card.id, 1, true);
        }
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return !isEffectActivateInTurn;
	}
}