package com.cab.cardGame.effektCards.kreaturen;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteSchaden;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Witwe extends CardStateEffekt {

	public Witwe(Card card) {
		super(card, State.boardState, Trigger.triggerOnAddKreaturToGrave, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		Player op = cardGame.oponent;
		List<Integer> cardIds = new ArrayList<>();
		
		for (CardState card : op.boardCards) {
			cardIds.add(card.id);
		}
		for (Integer cardId : cardIds) {
			new KarteSchaden().execute(cardGame, op, cardId, 1, true, false);
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.boardCards.size() > 0 && p.boardCards.contains(this);
	}
}
