package com.cab.effektCards.kreaturen;

import java.util.ArrayList;
import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Witwe extends EffektCardState {

	public Witwe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		Player op = cardGame.getOpOfP(p);
		List<Integer> cardIds = new ArrayList<>();
		
		for (CardState card : op.boardCards) {
			cardIds.add(card.id);
		}
		for (Integer cardId : cardIds) {
			cardGame.karteSchaden(op, cardId, 1, true);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).boardCards.size() > 0;
	}
}
