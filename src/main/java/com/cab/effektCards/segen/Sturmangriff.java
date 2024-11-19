package com.cab.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Sturmangriff extends EffektCardState {

	public Sturmangriff(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
        cardGame.getCardOfId(id).hasAttackOnTurn = false;
	}
	
    @Override
    public boolean isEffektPossible(Player p) {
        for (CardState card : p.boardCards) {
            if (card.hasAttackOnTurn && !card.isHide) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCardValidForSelection(CardState card) {
        return !card.isHide && card.hasAttackOnTurn;
    }
}