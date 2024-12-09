package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Djinn extends EffektCardState {

	public Djinn(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

    public void effekt(Integer id) {        
        cardGame.kreaturVomFriedhofInDieHandNehmen(cardGame.player, id, true);
    }

    public boolean isEffektPossible(Player p) {
        return p.graveCards.size() > 0;
    }
}