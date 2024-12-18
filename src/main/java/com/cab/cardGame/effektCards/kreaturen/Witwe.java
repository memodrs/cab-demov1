package com.cab.cardGame.effektCards.kreaturen;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Witwe extends CardStateEffekt {

	public Witwe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		Player op = cardGame.oponent;
		List<Integer> cardIds = new ArrayList<>();
		
		for (CardState card : op.boardCards) {
			cardIds.add(card.id);
		}
		for (Integer cardId : cardIds) {
			cardGame.karteSchaden(op, cardId, 1, true, false);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).boardCards.size() > 0;
	}
}
