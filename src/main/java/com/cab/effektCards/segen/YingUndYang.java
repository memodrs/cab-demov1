package com.cab.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class YingUndYang extends EffektCardState {

	public YingUndYang(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.isBoardEmpty() && p.hasGraveCards() && cardGame.getOpOfP(p).boardCards.size() == 1;
    }
}