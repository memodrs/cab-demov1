package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Kappa extends EffektCardState {

	public Kappa(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		for (CardState card : p.boardCards) {
            cardGame.setKarteStatus(p, card.id, false, Status.Feuer, true);
        }
	}
	
	public boolean isEffektPossible(Player p) {
        boolean hasCardWithFeuer = false;
        for (CardState card : p.boardCards) {
            if (card.statusSet.contains(Status.Feuer)) {
                hasCardWithFeuer = true;
                break;
            }
        }
		return !isEffectActivateInTurn && hasCardWithFeuer;
	}
}