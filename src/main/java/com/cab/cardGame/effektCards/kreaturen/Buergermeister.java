package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Buergermeister extends CardStateEffekt {

	public Buergermeister(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
        cardGame.changeSavedIdOponentAttack(id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return p.boardCards.size() > 1;
	}
}