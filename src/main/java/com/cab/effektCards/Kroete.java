package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Kroete extends EffektCardState implements EffektCard {

	public Kroete(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		cardGame.kreaturVomBoardZerstoeren(cardGame.getOponentForPlayer(p), id, true, false);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}