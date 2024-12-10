package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Yeti extends EffektCardState {
	final int SCHWERT_ID =  122;

	public Yeti(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.kreaturAufrufen(cardGame.player, this.id, false, true, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasArtOnBoard(Art.Mensch) && p.hasBoardPlace();
	}
}
