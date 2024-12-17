package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;;

public class Jaeger extends EffektCardState {

	public Jaeger(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
	}
	
	public boolean isEffektPossible(Player p) {
		Player op = cardGame.getOpOfP(p);
		return op.hasArtOnBoard(Art.Tier);
	}
	
	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide && card.art == Art.Tier;
	}
}