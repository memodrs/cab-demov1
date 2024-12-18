package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Zwerg extends CardStateEffekt {
	final int SCHWERT_ID =  122;

	public Zwerg(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteVonHandAufBoard(cardGame.player, this.id, false, true, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return !cardGame.getOpOfP(p).isBoardEmpty() && p.hasBoardPlace();
	}
}
