package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Schild extends EffektCardState {

	public Schild(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		cardGame.setKarteStatus(id, true, Status.Schild, true);
	}
	
    public boolean isEffektPossible(Player p) {
        return p.boardCards.stream().anyMatch(card -> !card.statusSet.contains(Status.Schild) && !card.isHide);	
    }

    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && !card.statusSet.contains(Status.Schild);
    }
}