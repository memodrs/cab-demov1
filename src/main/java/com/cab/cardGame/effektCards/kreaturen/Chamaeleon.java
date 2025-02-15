package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteDrehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Chamaeleon extends CardStateEffekt {

	public Chamaeleon(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer id) {
        for (CardState card : cardGame.getOwnerOfCard(this).boardCards) {
            new KarteDrehen().execute(cardGame, card.id, true, true);
        }
        cardGame.kartenMischen(cardGame.getOwnerOfCard(this), cardGame.getOwnerOfCard(this).boardCards, true);
    }

    @Override
	public boolean isEffektPossible(CardGame cardGame) {
        return !isEffectActivateInTurn;
    }
}

