package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Astrologe extends EffektCardState implements EffektCard {

	public Astrologe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		Player op = cardGame.getOponentForPlayer(p);
		for (int i = 0; i < op.boardCards.size(); i++) {
			cardGame.karteDrehen(op, op.boardCards.get(i).id, false, true);
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOponentForPlayer(p).boardCards.size() > 0;
	}
}