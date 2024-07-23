package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class S_Lebensenergie extends EffektCardState implements EffektCard {

	public S_Lebensenergie(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		cardGame.karteHeilen(p, id, 4, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.boardCards.size() > 0;
	}
}