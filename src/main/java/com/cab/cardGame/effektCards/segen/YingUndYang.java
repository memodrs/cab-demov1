package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class YingUndYang extends CardStateSpell {

	public YingUndYang(Card card, CardGame cardGame, int nextStateForPlayer, int selectState) {
		super(card, cardGame, nextStateForPlayer, selectState);
	}


	public void effekt(Integer id) {	
        cardGame.karteVomFriedhofAufBoard(cardGame.player, id, true);
	}
	
    public boolean isEffektPossible(Player p) {
		return  p.isBoardEmpty() && p.hasGraveCards() && cardGame.getOpOfP(p).boardCards.size() == 1;
    }
}