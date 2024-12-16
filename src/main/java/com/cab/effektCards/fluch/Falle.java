package com.cab.effektCards.fluch;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Falle extends EffektCardState {	
	public Falle(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}
	
	public void effekt(Integer id) {
		cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasArtOnBoard(Art.Tier);
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide && card.art == Art.Tier;
	}
}