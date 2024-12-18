package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Feuer extends CardStateSpell {
	public Feuer(Card card, CardGame cardGame, int nextStateForPlayer, int selectState) {
		super(card, cardGame, nextStateForPlayer, selectState);
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