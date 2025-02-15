package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteDrehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Fisch extends CardStateEffekt {

	public Fisch(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {
        new KarteDrehen().execute(cardGame, this.id, true, true);
    }

    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return !isEffectActivateInTurn;
    }
}
