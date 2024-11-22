package com.cab.effektCards.fluch;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Feuer extends EffektCardState {

	public Feuer(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.setKarteStatus(id, true, Status.Feuer, true);
	}
	
    public boolean isEffektPossible(Player p) {
        return cardGame.getOpOfP(p).boardCards.stream().anyMatch(card -> !card.statusSet.contains(Status.Feuer) && !card.isHide);	
    }

    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Feuer);
    }
}