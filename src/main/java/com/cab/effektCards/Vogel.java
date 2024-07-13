package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Vogel extends EffektCardState implements EffektCard {


	
	public Vogel(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		cardGame.kreaturAufrufen(p, cardGame.getIdxOfCard(p.handCards, this), false, true, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.boardCards.size() < 4;
	};
}
