package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Cerberus extends EffektCardState implements EffektCard {

	public Cerberus(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
        cardGame.karteAngriffErhoehen(p, this.id, 1, true);
        cardGame.karteHeilen(p, this.id, 1, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.boardCards.contains(this);
	}
}
