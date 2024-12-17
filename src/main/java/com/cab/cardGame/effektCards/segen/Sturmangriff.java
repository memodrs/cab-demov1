package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Sturmangriff extends EffektCardState {

	public Sturmangriff(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        cardGame.getCardOfId(id).hasAttackOnTurn = false;
	}
	
    public boolean isEffektPossible(Player p) {
        return p.boardCards.stream().anyMatch(card -> card.hasAttackOnTurn);	
    }

    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && card.hasAttackOnTurn;
    }
}