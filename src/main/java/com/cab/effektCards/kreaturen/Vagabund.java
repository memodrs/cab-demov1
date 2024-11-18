package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Vagabund extends EffektCardState {

	public Vagabund(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {		
		cardGame.kreaturVomBoardInDieHandGeben(p, this.id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
