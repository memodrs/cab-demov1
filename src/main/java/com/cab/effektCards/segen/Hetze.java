package com.cab.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Hetze extends EffektCardState {

	public Hetze(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
        for (CardState card : cardGame.player.boardCards) {
            card.hasAttackOnTurn = false;
        }
	}
	
    public boolean isEffektPossible(Player p) {
        return p.boardCards.stream().anyMatch(card -> card.hasAttackOnTurn);	
    }
}