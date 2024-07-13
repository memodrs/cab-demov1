package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Gespenst extends EffektCardState implements EffektCard {

	public Gespenst(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {		
		cardGame.karteHeilen(p, this.id, cardGame.getCardOfId(cardGame.getOponentForPlayer(p), id).atk, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !isHide;
	}
}