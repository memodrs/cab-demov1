package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Loewe extends EffektCardState implements EffektCard {

	public Loewe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		cardGame.karteVomBoardInDieHandGeben(cardGame.getOponentForPlayer(p), id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}