package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Gorilla extends EffektCardState implements EffektCard {

	public Gorilla(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		cardGame.karteAngriffErhoehen(p, this.id, 2, true);	
	}
	
	public boolean isEffektPossible(Player p) {
		return p.boardCards.contains(this);
	}
}