package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Arzt extends CardStateEffekt {

	public Arzt(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.effektSelectOwnBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteHeilen().execute(cardGame, id, 2, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return !isEffectActivateInTurn && p.hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}
}
