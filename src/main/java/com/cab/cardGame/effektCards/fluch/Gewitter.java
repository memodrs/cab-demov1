package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Gewitter extends EffektCardState {

	public Gewitter(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.setKarteStatus(id, true, Status.Blitz, true);
	}
	
    public boolean isEffektPossible(Player p) {
        return cardGame.getOpOfP(p).boardCards.stream().anyMatch(card -> !card.statusSet.contains(Status.Blitz) && !card.isHide);	
    }

    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Blitz);
    }
}