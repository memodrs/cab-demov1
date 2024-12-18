package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Kappa extends CardStateEffekt {

	public Kappa(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		for (CardState card : cardGame.player.boardCards) {
            cardGame.setKarteStatus(card.id, false, Status.Feuer, true);
            cardGame.setKarteStatus(card.id, false, Status.Blitz, true);
			cardGame.karteHeilen(card.id, 1, true);
        }
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return !isEffectActivateInTurn;
	}
}